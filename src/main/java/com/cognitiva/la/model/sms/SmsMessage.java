package com.cognitiva.la.model.sms;

import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jgarfias
 */
public class SmsMessage {

    private List<Sms> arraySms;
    
    public SmsMessage() {
    }

    public SmsMessage(List<Sms> arraySms) {
        this.arraySms = arraySms;
    }

    public List<Sms> getArraySms() {
        return arraySms;
    }

    public void setArraySms(List<Sms> arraySms) {
        this.arraySms = arraySms;
    }

    @Override
    public String toString() {
        return "SmsMessage{" + "arraySms=" + arraySms + '}';
    }
    
}
