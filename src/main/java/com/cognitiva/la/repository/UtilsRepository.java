/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitiva.la.repository;

import com.cognitiva.la.model.mongodb.CampaignsInitialMessages;
import com.cognitiva.la.model.mongodb.Cliente;
import com.cognitiva.la.model.mongodb.ClienteCuenta;
import com.cognitiva.la.model.mongodb.Mensaje;

/**
 *
 * @author jgarfias2
 */
public class UtilsRepository {

    public String makeSentence(Cliente cliente, ClienteCuenta clienteCuenta, CampaignsInitialMessages cim) {

        String text = "";
        for (Mensaje msj : cim.getMensajes()) {
            if ("var".equals(msj.getType())) {

                if ("${nombreCliente}".equals(msj.getContent())) {
                    text = text + cliente.getNombreCliente();
                }
                if ("${fechaLimitePago}".equals(msj.getContent())) {
                    text = text + clienteCuenta.getFechaLimitePago();
                }
                if ("${telSms}".equals(msj.getContent())) {
                    text = text + cliente.getTelSms();
                }
                if ("${totalDeudor}".equals(msj.getContent())) {
                    text = text + clienteCuenta.getTotalDeudor();
                }
                if ("${noCuenta}".equals(msj.getContent())) {
                    text = text + clienteCuenta.getNoCuenta();
                }
                if ("${pagoMinimo}".equals(msj.getContent())) {
                    text = text + clienteCuenta.getPagoMinimo();
                }
                if ("${descProducto}".equals(msj.getContent())) {
                    text = text + clienteCuenta.getDescProducto();
                }
            } else {
                text = text + msj.getContent();
            }
        }

        return text;
    }
}
