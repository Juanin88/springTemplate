/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitiva.la.services;

import com.cognitiva.la.model.sms.Constant;
import com.cognitiva.la.model.sms.StartCampaign;
import com.cognitiva.la.model.mongodb.Campaign;
import com.cognitiva.la.model.mongodb.CampaignCliente;
import com.cognitiva.la.model.mongodb.CampaignsInitialMessages;
import com.cognitiva.la.model.mongodb.Cliente;
import com.cognitiva.la.model.mongodb.ClienteCuenta;
import com.cognitiva.la.model.sms.Sms;
import com.cognitiva.la.model.sms.SmsMessage;
import com.cognitiva.la.repository.CampaignsInitialMessagesRepository;
import com.cognitiva.la.repository.ClienteRepository;
import com.cognitiva.la.repository.UtilsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

/**
 *
 * @author jgarfias
 */
public class SmsService {

    public void send(String jsonInString, URL url) throws MalformedURLException, ProtocolException, IOException {

        HttpURLConnection connection
                = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty(Constant.HEADER_ACCEPT, Constant.CONTENT_TYPE_JSON);
        connection.setRequestProperty(Constant.HEADER_CONTENT_TYPE, Constant.CONTENT_TYPE_JSON + "; " + Constant.CHARSET_UTF8);
        connection.setRequestProperty(Constant.HEADER_AUTHORIZATION, "Basic" + " " + "cmVzdFVzZXI6cmVzdEF1dGg=");
        connection.setRequestProperty(Constant.HEADER_CONTENT_LENGTH, jsonInString.getBytes().length + "");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        connection.setReadTimeout(10000);

        try (OutputStream outputStream = connection.getOutputStream()) {
            outputStream.write(jsonInString.getBytes("UTF-8"));
        } catch (IOException ioex) {
            System.out.println("Ocurrión un error al escribir el flujo del cuerpo de la petición en get getConnectionSendSms -> " + ioex.getMessage());
        }
        connection.connect();

        int status = connection.getResponseCode();
        StringBuilder sb = new StringBuilder();
        if (status == 200) {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            } catch (IOException ioex) {
                System.out.println("Ocurrió un error al procesar la respuesta de sendSmsSingle -> " + ioex.getMessage());
            }
        }

    }

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        int timeout = 5000;
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
                = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeout);
        return clientHttpRequestFactory;
    }

    public SmsMessage makeSmsAdvancedObj(Campaign campaign, StartCampaign st) throws NoSuchFieldException, JsonProcessingException, IOException {

        ClienteRepository cr = new ClienteRepository();

        SmsMessage smsMessage = new SmsMessage();

        List<CampaignCliente> campaignClientes = campaign.getCampaignClientes();
        CampaignsInitialMessagesRepository cimR = new CampaignsInitialMessagesRepository();
        CampaignsInitialMessages cim = cimR.findById(campaign.getIdCampaignsInitialMessages());

        ArrayList al = new ArrayList();

        for (CampaignCliente campaignCliente : campaignClientes) {
            // add elements to the array list
            al.add(campaignCliente.getClaveCliente());
        }

        List<Cliente> clientes = cr.findByClaveClienteArray(al);

        List<Sms> smss = new ArrayList<>();

        for (CampaignCliente campaignCliente : campaignClientes) {
            Sms message = new Sms();

            message.setMessageId(st.getClave() + "-" + campaignCliente.getTelSms().replace("+", "") + "-" + campaignCliente.getNoCuenta());
            message.setTo(campaignCliente.getTelSms());

            String noCuenta = campaignCliente.getNoCuenta();
            String claveCliente = campaignCliente.getClaveCliente();

            ClienteCuenta ccAux = new ClienteCuenta();
            Cliente clienteAux = new Cliente();
            for (Cliente cliente : clientes) {
                if (cliente.getClaveCliente().equals(claveCliente)) {
                    clienteAux = cliente;
                    for (ClienteCuenta cc : cliente.getClienteCuentas()) {
                        if (cc.getNoCuenta().equals(noCuenta)) {
                            ccAux = cc;
                        }
                    }
                }
            }

            String text = "";
            UtilsRepository util = new UtilsRepository();
            text = util.makeSentence(clienteAux, ccAux, cim);

            message.setFrom("cognitiva");
            message.setText(text);
            message.setFlagInit(Boolean.TRUE);
            message.setFlagContinue(Boolean.FALSE);

            smss.add(message);

        }

        smsMessage.setArraySms(smss);
        return smsMessage;
    }

}
