/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitiva.la.services;

import com.cognitiva.la.model.sms.FileBucket;
import com.cognitiva.la.model.mongodb.Campaign;
import com.cognitiva.la.model.mongodb.CampaignCliente;
import com.cognitiva.la.model.mongodb.Cliente;
import com.cognitiva.la.model.mongodb.ClienteCuenta;
import com.cognitiva.la.repository.CampaignRepository;
import com.cognitiva.la.repository.ClienteRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.bson.Document;

/**
 *
 * @author jgarfias
 */
public class EtlImpl {

    private String RFC;
    private String buc;
    private String NO_CUENTA;
    private String claveCampaign;

    public List<CampaignCliente> uploadClienteFile(String path, FileBucket fileBucket) throws FileNotFoundException, IOException, ParseException {

        List<CampaignCliente> campaignClientes = new ArrayList<>();
        Reader in = new FileReader(path);
        ClienteRepository cr = new ClienteRepository();
        CampaignRepository campRep = new CampaignRepository();
        Iterable<CSVRecord> records = CSVFormat.TDF.parse(in);
        boolean headerRow = false;

        List<Document> documents = new ArrayList<>();

        for (CSVRecord record : records) {
            if (headerRow) {

                Cliente cliente = makeClienteFromRecordCSV(record);

                CampaignCliente campaignCliente = new CampaignCliente();

                campaignCliente.setNoCuenta(NO_CUENTA);
                campaignCliente.setClaveCliente(buc);
                campaignCliente.setStatus(true);

                campaignClientes.add(campaignCliente);

                List<Cliente> clienteValid = cr.findByClaveCliente(buc);

                // Valida si el cliente no existe actualmente registrado.
                if (clienteValid.isEmpty()) {
                    // Agrega el cliente en la base de datos.
                    documents.add(cr.makeBsonDocument(cliente));
                } else {
                    // Si existe el cliente, ahora valida la existencia de su cuenta.
                    List<Cliente> clienteValid2 = cr.findByClaveClienteAndCuenta(buc, NO_CUENTA);
                    if (clienteValid2.isEmpty()) {
                        cr.insertCuenta(cliente.getClienteCuentas().get(0), buc, NO_CUENTA);
                    }
                }

                // Inserta cliente en campaña validando si existe en la campaña.
                if (claveCampaign != null) {
                    Campaign campAux = campRep.searchClienteOnCampaignByClaveAndCuenta(buc, claveCampaign, NO_CUENTA);
                    if (campAux.getClave() == null) {
                        cliente.setClaveCampaign(claveCampaign);
                        campRep.insertClienteToCampaign(cliente);
                    }
                }
            }
            headerRow = true;
        }

        cr.insertMany(documents);

        if (claveCampaign == null) {

            Campaign campaign = new Campaign();
            campaign.setClave(fileBucket.getClave());
            campaign.setCampaignClientes(campaignClientes);
            campaign.setNombre(fileBucket.getNombre());
            campaign.setDescripcion(fileBucket.getDescripcion());
            campaign.setProducto(fileBucket.getProducto());
            campaign.setIdCampaignsInitialMessages(fileBucket.getId());

            //campaign.setFechaLimitePagoCampaign(dateFormat2(fileBucket.getFechaLimitePagoCampaign()));

            campRep.addCampaign(campaign, campaignClientes);
        }

        return campaignClientes;
    }

    public List<CampaignCliente> uploadClienteFileToCampaing(String path, FileBucket fileBucket, String claveCampaignAux) throws FileNotFoundException, IOException, ParseException {

        claveCampaign = claveCampaignAux;

        return uploadClienteFile(path, fileBucket);

    }

    public static Date dateFormat(String dateInString) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        Date date = formatter.parse(dateInString);

        return date;

    }

    public static Date dateFormat2(String dateInString) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date date = formatter.parse(dateInString);

        return date;

    }

    public Cliente makeClienteFromRecordCSV(CSVRecord record) throws ParseException {

        System.out.println("Record : " + record.get(0));
        System.out.println("Record : " + record.get(1));
        Cliente cliente = new Cliente();
        List<ClienteCuenta> clienteCuentas = new ArrayList<>();

        RFC = record.get(33);
        buc = record.get(5);
        NO_CUENTA = record.get(1);

        cliente.setClaveCliente(buc);
        cliente.setRfc(RFC);
        cliente.setNombreCliente(record.get(2));
        cliente.setCalleNoCliente(record.get(15));
        cliente.setCiudadCliente(record.get(17));
        cliente.setCodPostal(record.get(19));
        cliente.setColoniaCliente(record.get(16));
        cliente.setCorreo(record.get(48));
        cliente.setEstado(record.get(18));

        ClienteCuenta clienteCuenta = new ClienteCuenta();

        clienteCuenta.setConsecutivo(record.get(0));
        clienteCuenta.setNoCuenta(NO_CUENTA);
        clienteCuenta.setAgenciaActual(record.get(3));
        clienteCuenta.setTotalDeudor(Double.parseDouble(record.get(7)));
        clienteCuenta.setPagoMinimo(Double.parseDouble(record.get(8)));
        clienteCuenta.setMontoMoroso(Double.parseDouble(record.get(9)));
        clienteCuenta.setCodigoBloqueo(record.get(10));
        clienteCuenta.setDiaCorte(Integer.parseInt(record.get(12)));
        clienteCuenta.setDescProducto(record.get(14));
        clienteCuenta.setAgenciaPrevia(record.get(26));
        clienteCuenta.setMontoUltimaCompra(Double.parseDouble(record.get(30)));
        clienteCuenta.setMontoUltimaDisposicion(Double.parseDouble(record.get(31)));
        clienteCuenta.setFechaAperturaCuenta(dateFormat(record.get(32)));
        //clienteCuenta.setMontoAsignadoAgencia(Double.parseDouble(record.get(34)));
        //clienteCuenta.setMontoAsignadoAgenciaPrev(Double.parseDouble(record.get(35)));
        clienteCuenta.setFechaLimitePago(dateFormat(record.get(36)));

        clienteCuentas.add(clienteCuenta);

        cliente.setClienteCuentas(clienteCuentas);

        return cliente;

    }

    public String uploadClienteTelsFile(String path, FileBucket fileBucket) throws FileNotFoundException, IOException, ParseException {

        Reader in = new FileReader(path);
        ClienteRepository cr = new ClienteRepository();

        Iterable<CSVRecord> records = CSVFormat.TDF.parse(in);
        boolean headerRow = false;

        List<Document> documents = new ArrayList<>();

        for (CSVRecord record : records) {
            if (headerRow) {
                if (record.get(0) == "") {
                    continue;
                }
                System.out.println(record.get(6));
                if ("M".equals(record.get(6))) {
                    System.out.println(record.get(0));
                    System.out.println(record.get(1));
                    System.out.println(record.get(2));
                    System.out.println(record.get(3));
                    System.out.println(record.get(4));

                    cr.addClientTel(record.get(1), "521" + record.get(3) + record.get(4));

                } else {
                }

            }
            headerRow = true;
        }

        return "";
    }

    public String updateClientesFile(String path, FileBucket fileBucket) throws FileNotFoundException, IOException, ParseException {

        Reader in = new FileReader(path);
        ClienteRepository cr = new ClienteRepository();
        Iterable<CSVRecord> records = CSVFormat.TDF.parse(in);
        boolean headerRow = false;

        List<Document> documents = new ArrayList<>();

        for (CSVRecord record : records) {
            if (headerRow) {

                Cliente cliente = makeClienteFromRecordCSVforUpdate(record);

                List<Cliente> clienteValid = cr.findByClaveCliente(buc);

                // Valida si el cliente no existe actualmente registrado.
                if (clienteValid.isEmpty()) {
                    // Agrega el cliente en la base de datos.
                    //documents.add(cr.makeBsonDocument(cliente));
                    continue;
                } else {
                    // Si existe el cliente, ahora valida la existencia de su cuenta.
                    List<Cliente> clienteValid2 = cr.findByClaveClienteAndCuenta(buc, NO_CUENTA);
                    if (clienteValid2.size() >= 1) {
                        //cr.insertCuenta(cliente.getClienteCuentas().get(0), buc, NO_CUENTA);
                        cr.updateClientCuenta(cliente, clienteValid.get(0));
                    }
                }

            }
            headerRow = true;
        }

        return "success";

    }

    public Cliente makeClienteFromRecordCSVforUpdate(CSVRecord record) throws ParseException {

        Cliente cliente = new Cliente();
        List<ClienteCuenta> clienteCuentas = new ArrayList<>();

        RFC = record.get(4);
        buc = record.get(17);
        NO_CUENTA = record.get(1);

        cliente.setClaveCliente(buc);
        cliente.setRfc(RFC);

        ClienteCuenta clienteCuenta = new ClienteCuenta();

        clienteCuenta.setNoCuenta(NO_CUENTA);

        try {
            clienteCuenta.setTotalDeudor(Double.parseDouble(record.get(11)));
        } catch (Exception e) {
            clienteCuenta.setTotalDeudor(0.0);
        }
        clienteCuenta.setPagoMinimo(Double.parseDouble(record.get(13)));
        clienteCuenta.setMontoMoroso(Double.parseDouble(record.get(12)));

        clienteCuenta.setDiaCorte(Integer.parseInt(record.get(8)));

        clienteCuenta.setFechaAperturaCuenta(dateFormat(record.get(54)));
        clienteCuenta.setFechaLimitePago(dateFormat(record.get(55)));
        clienteCuenta.setMontoUltimoPago(Double.parseDouble(record.get(21)));
        clienteCuenta.setFechaUltimoPago(dateFormat(record.get(22)));

        clienteCuentas.add(clienteCuenta);

        cliente.setClienteCuentas(clienteCuentas);

        return cliente;
    }

    public List<CampaignCliente> desClientFromCampaignFile(String path, FileBucket fileBucket) throws FileNotFoundException, IOException, ParseException {

        List<CampaignCliente> campaignClientes = new ArrayList<>();
        Reader in = new FileReader(path);
        CampaignRepository campRep = new CampaignRepository();
        Iterable<CSVRecord> records = CSVFormat.TDF.parse(in);
        boolean headerRow = false;

        Campaign campaign = new Campaign();

        campaign.setClave(fileBucket.getClaveCampaign());

        for (CSVRecord record : records) {
            CampaignCliente campaignCliente = new CampaignCliente();
            if (headerRow) {
                buc = record.get(17);
                NO_CUENTA = record.get(1);

                System.out.println("Buc " + buc + " No_cuenta : " + NO_CUENTA + " clave " + fileBucket.getClaveCampaign());
                campaignCliente.setClaveCliente(buc);
                campaignCliente.setNoCuenta(NO_CUENTA);

                campaignClientes.add(campaignCliente);
            }
            headerRow = true;
            campaign.setCampaignClientes(campaignClientes);
        }

        campRep.deleteClienteFromCampaign(campaign);

        return campaignClientes;
    }

    public String generateLayout(String claveCampaign) throws JsonProcessingException {

        CampaignRepository campaignRepository = new CampaignRepository();

        Campaign campaign = campaignRepository.findByClaveCampaignWithClient(claveCampaign);

        // 1. Nombre de Archivo
        String nombreDelArchivo = "Layout.txt";
        // 2. Numero de registros tota del archivo.
        String numeroDeRegistrosTotalDelArchivo = Integer.toString(campaign.getCampaignClientes().size());

        // 3. Suma del monto total de las promesas de pago.
        int sumaDelMontoTotalDeLasPromesasDePago = 23240;

        String layout = padRight(nombreDelArchivo, 30)
                + padLeftWithZeros(numeroDeRegistrosTotalDelArchivo, 7)
                + padRightWithZeros(sumaDelMontoTotalDeLasPromesasDePago, 15) + "\n";

        for (CampaignCliente cli : campaign.getCampaignClientes()) {

            SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy");
            String date = sdf.format(cli.getFechaActividad());
            //System.out.println(date); //15/10/2013

            SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
            String time = sdf2.format(cli.getFechaActividad());

            
            layout = layout
                    + "WT" // 4. Codigo actn Watson
                    + "6"// 5. Grupo de Cuenta Código de Constancia
                    + padRight(cli.getNoCuenta(), 25) // 6. Numero de Cuenta
                    + date // 7. Fecha de Actividad
                    + " " // 8. Espacio en Blanco
                    + time // 9. Hora de Actividad (hh:mm:ss)
                    + "\n";
        }

        String codigoDeActnConstante = "200";
        String grupoDeCuenta;
        String numeroDeCuenta;
        String fechaDeActividad;
        String espacioEnBlanco;
        String horaDeActividad;
        String numDeSecuencia;
        String codigodeAccion;
        String codigoDeResultado;
        String codigoDeCarta;
        String codigoDeAgencia;
        String comentario;
        String codigoDeDevolucion;
        String fechaDeDevolucion;
        String fechaEnQueSeRealizoLaPromesa;
        String fechaEnQueSeVenceLaPromesa;
        String montoDeLaPromesaDePago;
        String banderaDePromesaDePago;

        String content = "This is txt content";

        return layout;
    }

    public static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }

    public static String padLeft(String s, int n) {
        return String.format("%1$" + n + "s", s);
    }

    public static String padRightWithZeros(int s, int n) {
        String format = "%0" + Integer.toString(n) + "d";
        return String.format(format, s);
    }

    public static String padRightWithZeros(String s, int n) {
        return String.format("%1$-" + n + "s", s).replace(' ', '0');
    }

    public static String padLeftWithZeros(String s, int n) {
        return String.format("%1$" + n + "s", s).replace(' ', '0');
    }

}
