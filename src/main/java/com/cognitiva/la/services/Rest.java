/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitiva.la.services;

import com.cognitiva.la.d3reports.Content;
import com.cognitiva.la.d3reports.Contents;
import com.cognitiva.la.model.requests.GetDataClienteRq;
import com.cognitiva.la.model.sms.Ping;
import com.cognitiva.la.model.sms.StartCampaign;
import com.cognitiva.la.model.mongodb.Campaign;
import com.cognitiva.la.model.mongodb.CampaignCliente;
import com.cognitiva.la.model.mongodb.CampaignsInitialMessages;
import com.cognitiva.la.model.mongodb.Cliente;
import com.cognitiva.la.model.mongodb.Mensaje;
import com.cognitiva.la.model.requests.SearchClientRq;
import com.cognitiva.la.model.responses.AddCampaign;
import com.cognitiva.la.model.responses.GetDataClienteRs;
import com.cognitiva.la.model.sms.Sms;
import com.cognitiva.la.model.sms.SmsMessage;
import com.cognitiva.la.repository.CampaignRepository;
import com.cognitiva.la.repository.CampaignsInitialMessagesRepository;
import com.cognitiva.la.repository.ClienteRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.bson.Document;
import org.springframework.stereotype.Component;

/**
 *
 * @author jgarfias
 */
@Component
@Path("/action")
public class Rest {

    public Rest() {
    }

    @GET
    @Path("/test")
    public Response test() {

        String output = " Hola Jersey!!! ";
        System.out.println("Entra REST!!");
        return Response.status(200).entity(output).build();

    }

    @POST
    @Path("/add-campaign")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCampaign(Campaign campaign) throws JsonProcessingException {

        System.out.println("Camapaña : " + campaign.toString());
        CampaignRepository campRep = new CampaignRepository();

        // Campaign camp = campRep.addCampaign(campaign);
        System.out.println("TestCampaign");
        AddCampaign rs = new AddCampaign();

        rs.setCode(0);
        rs.setStatus("success");
        rs.setMsg("Se agregó correctamente una campaña.");
        // rs.setCampaign(camp);

        return Response.status(200).entity(rs).build();

    }

    @POST
    @Path("/start-campaign")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response startCampaign(StartCampaign st) throws JsonProcessingException, MalformedURLException, IOException, NoSuchFieldException {

        SmsService smsService = new SmsService();
        ObjectMapper mapper = new ObjectMapper();

        ClienteRepository clienteRepository = new ClienteRepository();
        Campaign campaign = clienteRepository.findByClaveCampaign(st.getClave());

        SmsMessage smsMessage = smsService.makeSmsAdvancedObj(campaign, st);
        //Object to JSON in String
        String jsonInString = mapper.writeValueAsString(smsMessage);

        for (CampaignCliente cc : campaign.getCampaignClientes()) {
            cc.setFechaActividad(new Date());
        }

        CampaignRepository cr = new CampaignRepository();

        cr.update(campaign);
        URL url = new URL("https://dev-sms-service.mybluemix.net/rest/sms-rest/sendSmsGC");
        smsService.send(jsonInString, url);

        return Response.status(200).entity(smsMessage).build();
    }

    @POST
    @Path("/start-client-campaign")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response startClientCampaign(StartCampaign st) throws JsonProcessingException, MalformedURLException, IOException, NoSuchFieldException {

        SmsService smsService = new SmsService();
        ObjectMapper mapper = new ObjectMapper();

        ClienteRepository cr = new ClienteRepository();
        Cliente cliente = cr.findClienteByClave(st.getClaveCliente());
        CampaignsInitialMessagesRepository cimR = new CampaignsInitialMessagesRepository();

        String text = cimR.makeInitialSentence(cliente, st.getIdCampaignsInitialMessages(), st.getNoCuenta());

        SmsMessage smsMessage = new SmsMessage();
        Sms sms = new Sms();
        List<Sms> lsms = new ArrayList<>();

        sms.setTo(st.getTel());
        sms.setFrom("Cognitiva");
        sms.setText(text);
        sms.setFlagInit(Boolean.TRUE);
        sms.setFlagContinue(Boolean.FALSE);
        sms.setMessageId(st.getClave() + "-" + st.getTel().replace("+", "") + "-" + st.getNoCuenta());

        lsms.add(sms);

        smsMessage.setArraySms(lsms);

        //Object to JSON in String
        String jsonInString = mapper.writeValueAsString(smsMessage);

        URL url = new URL("https://dev-sms-service.mybluemix.net/rest/sms-rest/sendSmsGC");
        smsService.send(jsonInString, url);

        try {
            CampaignRepository campR = new CampaignRepository();
            Campaign campaign = campR.findByClaveCampaign(st.getClave());

            for (CampaignCliente cc : campaign.getCampaignClientes()) {

                if (cc.getClaveCliente().equals(st.getClaveCliente())) {
                    cc.setFechaActividad(new Date());
                    break;
                }
            }

            campR.update(campaign);
        } finally {
            return Response.status(200).entity(smsMessage).build();
        }

    }

    @POST
    @Path("/ping")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response ping() throws JsonProcessingException, MalformedURLException, IOException, NoSuchFieldException {

        Ping ping = new Ping();

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date today = Calendar.getInstance().getTime();
        String reportDate = df.format(today);

        ping.setTxt("ping");
        ping.setTimestamp(reportDate);

        return Response.status(200).entity(ping).build();

    }

    @POST
    @Path("/get-data-cliente")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getDataCliente(GetDataClienteRq gdCliente) throws JsonProcessingException {

        CampaignRepository campaignRepository = new CampaignRepository();
        Campaign campaign = (Campaign) campaignRepository.findByClaveCampaign(gdCliente.getClave());
        ClienteRepository cli = new ClienteRepository();
        GetDataClienteRs gdc = new GetDataClienteRs();
        Cliente cliente = cli.findByTelAndNumCuenta(gdCliente.getTel(), gdCliente.getCuenta());

        if (cliente != null) {

            System.out.println("Cliente : " + cliente.toString());
            gdc.setClave(gdCliente.getClave());
            gdc.setFechaCreacion(campaign.getFechaCreacion());
            gdc.setFechaLimitePagoCampaign(campaign.getFechaLimitePagoCampaign());
            gdc.setCliente(cliente);
            System.err.println("Cliente " + gdc.toString());
        }

        return Response.status(200).entity(gdc).build();
    }

    @POST
    @Path("/add-client")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addClient(Cliente cliente) throws JsonProcessingException {

        ClienteRepository cr = new ClienteRepository();

        List<Document> documents = new ArrayList<>();

        List<Cliente> clienteValid = cr.findByClaveCliente(cliente.getClaveCliente());

        System.out.println("Cliente : " + clienteValid.toString());
        // Valida si el cliente no existe actualmente registrado.
        if (clienteValid.isEmpty()) {
            documents.add(cr.makeBsonDocument(cliente));
            cr.insertMany(documents);
        } else {
            cliente = null;
        }

        return Response.status(200).entity(cliente).build();

    }

    @POST
    @Path("/edit-client")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editClient(Cliente cliente) throws JsonProcessingException {

        ClienteRepository cr = new ClienteRepository();

        cr.editClient(cliente);

        return Response.status(200).entity(cliente).build();

    }

    @POST
    @Path("/search-client")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchClient(SearchClientRq searchClientRq) throws JsonProcessingException {

        ClienteRepository cr = new ClienteRepository();

        List<Cliente> clientes = cr.searchByKey(searchClientRq.getClave());

        System.out.println(searchClientRq.toString());

        return Response.status(200).entity(clientes).build();
    }

    @POST
    @Path("/edit-account")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editAccount(Cliente cliente) throws JsonProcessingException {

        System.out.println(cliente.toString());
        ClienteRepository cr = new ClienteRepository();
        cr.editAccountsClient(cliente);

        return Response.status(200).entity(cliente).build();

    }

    @POST
    @Path("/add-client-to-campaign")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addClientToCampaign(Cliente cliente) throws JsonProcessingException {

        ClienteRepository cr = new ClienteRepository();
        List<Cliente> clienteValid = cr.findByRfc(cliente.getRfc());

        // Valida si el cliente no existe actualmente registrado.
        if (!clienteValid.isEmpty()) {
            CampaignRepository camRep = new CampaignRepository();
            camRep.insertClienteToCampaign(cliente);
        }

        System.out.println("Cliente " + cliente.toString());
        return Response.status(200).entity(cliente).build();

    }

    @POST
    @Path("/add-init-message")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addInitMessage(CampaignsInitialMessages cim) throws JsonProcessingException {
        for (Mensaje item : cim.getMensajes()) {
            if (item.getContent().startsWith("${") && item.getContent().endsWith("}")) {
                item.setType("var");
            } else {
                item.setType("text");
            }
        }
        CampaignsInitialMessagesRepository campaignsInitialMessagesRepository = new CampaignsInitialMessagesRepository();
        boolean status = campaignsInitialMessagesRepository.insert(cim);

        if (status == true) {
            return Response.status(200).entity(cim).build();
        } else {
            return Response.status(200).entity(null).build();
        }
    }

    @POST
    @Path("/update-init-message")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateInitMessage(CampaignsInitialMessages cim) throws JsonProcessingException {
        for (Mensaje item : cim.getMensajes()) {
            if (item.getContent().startsWith("${") && item.getContent().endsWith("}")) {
                item.setType("var");
            } else {
                item.setType("text");
            }
        }
        CampaignsInitialMessagesRepository campaignsInitialMessagesRepository = new CampaignsInitialMessagesRepository();
        List<CampaignsInitialMessages> listMesajes = campaignsInitialMessagesRepository.update(cim);

        if (listMesajes == null) {
            return Response.status(200).entity(null).build();
        } else {
            return Response.status(200).entity(listMesajes).build();
        }
    }
    
    @POST
    @Path("/report-campaign-results")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response reportCampaignResults() throws JsonProcessingException {

        Content content = new Content();
        
        List<Contents> list = new ArrayList<>();
        
        Contents contents = new Contents();
        contents.setLabel("Promesa de pago");
        contents.setValue(40);
        contents.setColor("#A3D8FE");
        list.add(contents);
        
        Contents contents2 = new Contents();
        contents2.setLabel("No Pago");
        contents2.setValue(30);
        contents2.setColor("#FEB5A3");
        list.add(contents2);
        
        Contents contents3 = new Contents();
        contents3.setLabel("No Contesta");
        contents3.setValue(70);
        contents3.setColor("#CDCDCD");
        list.add(contents3);
        
        content.setContent(list);

        return Response.status(200).entity(content).build();

    }

    
}
