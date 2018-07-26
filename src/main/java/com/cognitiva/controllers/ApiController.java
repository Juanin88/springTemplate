/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitiva.controllers;

import com.cognitiva.la.d3reports.Content;
import com.cognitiva.la.d3reports.Contents;
import com.cognitiva.la.model.mongodb.Campaign;
import com.cognitiva.la.model.mongodb.CampaignCliente;
import com.cognitiva.la.model.mongodb.CampaignsInitialMessages;
import com.cognitiva.la.model.mongodb.Cliente;
import com.cognitiva.la.model.mongodb.Mensaje;
import com.cognitiva.la.model.requests.GetDataClienteRq;
import com.cognitiva.la.model.requests.SearchClientRq;
import com.cognitiva.la.model.responses.AddCampaign;
import com.cognitiva.la.model.responses.GetDataClienteRs;
import com.cognitiva.la.model.sms.Ping;
import com.cognitiva.la.model.sms.Sms;
import com.cognitiva.la.model.sms.SmsMessage;
import com.cognitiva.la.model.sms.StartCampaign;
import com.cognitiva.la.repository.CampaignRepository;
import com.cognitiva.la.repository.CampaignsInitialMessagesRepository;
import com.cognitiva.la.repository.ClienteRepository;
import com.cognitiva.la.services.SmsService;
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
import javax.ws.rs.core.Response;
import org.bson.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jgarfias2
 */
@RestController
public class ApiController {

    @GetMapping("/rest-api/action/test")
    public Contents test() {

        Contents contents = new Contents();
        contents.setColor("red");
        contents.setLabel("color");
        contents.setValue(11);

        return contents;
    }

    @PostMapping("/rest-api/action/add-campaign")
    public ResponseEntity addCampaign(@RequestBody Campaign campaign) throws JsonProcessingException {

        System.out.println("Camapaña : " + campaign.toString());
        CampaignRepository campRep = new CampaignRepository();

        // Campaign camp = campRep.addCampaign(campaign);
        System.out.println("TestCampaign");
        AddCampaign rs = new AddCampaign();

        rs.setCode(0);
        rs.setStatus("success");
        rs.setMsg("Se agregó correctamente una campaña.");
        // rs.setCampaign(camp);

        return new ResponseEntity(rs, HttpStatus.OK);

    }

    @PostMapping("/rest-api/action/start-campaign")
    public ResponseEntity startCampaign(@RequestBody StartCampaign st) throws JsonProcessingException, NoSuchFieldException, IOException {

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
        //smsService.send(jsonInString, url);

        return new ResponseEntity(smsMessage, HttpStatus.OK);

    }

    @PostMapping("/rest-api/action/start-client-campaign")
    public ResponseEntity startClientCampaign(@RequestBody StartCampaign st) throws JsonProcessingException, MalformedURLException, IOException, NoSuchFieldException {

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
        //smsService.send(jsonInString, url);

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
            return new ResponseEntity(smsMessage, HttpStatus.OK);

        }

    }

    @PostMapping("/rest-api/action/ping")
    public ResponseEntity ping() throws JsonProcessingException, MalformedURLException, IOException, NoSuchFieldException {

        Ping ping = new Ping();

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date today = Calendar.getInstance().getTime();
        String reportDate = df.format(today);

        ping.setTxt("ping");
        ping.setTimestamp(reportDate);

        return new ResponseEntity(ping, HttpStatus.OK);

    }

    @PostMapping("/rest-api/action/get-data-cliente")
    public ResponseEntity getDataCliente(@RequestBody GetDataClienteRq gdCliente) throws JsonProcessingException {

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

        return new ResponseEntity(gdc, HttpStatus.OK);

    }

    @PostMapping("/rest-api/action/add-client")
    public ResponseEntity addClient(@RequestBody Cliente cliente) throws JsonProcessingException {

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

        return new ResponseEntity(cliente, HttpStatus.OK);

    }

    @PostMapping("/rest-api/action/edit-client")
    public ResponseEntity editClient(@RequestBody Cliente cliente) throws JsonProcessingException {

        ClienteRepository cr = new ClienteRepository();

        cr.editClient(cliente);

        return new ResponseEntity(cliente, HttpStatus.OK);

    }

    @PostMapping("/rest-api/action/search-client")
    public ResponseEntity searchClient(@RequestBody SearchClientRq searchClientRq) throws JsonProcessingException {

        ClienteRepository cr = new ClienteRepository();

        List<Cliente> clientes = cr.searchByKey(searchClientRq.getClave());

        System.out.println(searchClientRq.toString());

        return new ResponseEntity(clientes, HttpStatus.OK);

    }

    @PostMapping("/rest-api/action/edit-account")
    public ResponseEntity editAccount(@RequestBody Cliente cliente) throws JsonProcessingException {

        System.out.println(cliente.toString());
        ClienteRepository cr = new ClienteRepository();
        cr.editAccountsClient(cliente);

        return new ResponseEntity(cliente, HttpStatus.OK);

    }

    @PostMapping("/rest-api/action/add-client-to-campaign")
    public ResponseEntity addClientToCampaign(@RequestBody Cliente cliente) throws JsonProcessingException {

        ClienteRepository cr = new ClienteRepository();
        List<Cliente> clienteValid = cr.findByRfc(cliente.getRfc());

        // Valida si el cliente no existe actualmente registrado.
        if (!clienteValid.isEmpty()) {
            CampaignRepository camRep = new CampaignRepository();
            camRep.insertClienteToCampaign(cliente);
        }

        System.out.println("Cliente " + cliente.toString());
        return new ResponseEntity(cliente, HttpStatus.OK);

    }

    @PostMapping("/rest-api/action/add-init-message")
    public ResponseEntity addInitMessage(@RequestBody CampaignsInitialMessages cim) throws JsonProcessingException {
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
            return new ResponseEntity(cim, HttpStatus.OK);
        } else {
            return new ResponseEntity(null, HttpStatus.OK);

        }
    }

    @PostMapping("/rest-api/action/update-init-message")
    public ResponseEntity updateInitMessage(@RequestBody CampaignsInitialMessages cim) throws JsonProcessingException {
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
            return new ResponseEntity(null, HttpStatus.OK);

        } else {
            return new ResponseEntity(listMesajes, HttpStatus.OK);

        }
    }

    @PostMapping("/rest-api/action/report-campaign-results")
    public ResponseEntity reportCampaignResults() throws JsonProcessingException {

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

        return new ResponseEntity(content, HttpStatus.OK);

    }

}
