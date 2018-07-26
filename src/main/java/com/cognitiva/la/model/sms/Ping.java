/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitiva.la.model.sms;

/**
 *
 * @author jgarfias
 */
public class Ping {
    private String txt;
    private String timestamp;

    public Ping(String txt, String timestamp) {
        this.txt = txt;
        this.timestamp = timestamp;
    }

    public Ping() {
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Ping{" + "txt=" + txt + ", timestamp=" + timestamp + '}';
    }
    
    
}
