package com.cognitiva.la.model.sms;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author emurillo
 */
public class Sms{

    
    private String from;
    private String text;
    private String to;
    private Boolean flagInit;
    private Boolean flagContinue;
    private String productoPeriodo;
    private String messageId;

    public Sms() {
    }

    public Sms(String from, String text, String to, Boolean flagInit, Boolean flagContinue, String productoPeriodo, String messageId) {
        this.from = from;
        this.text = text;
        this.to = to;
        this.flagInit = flagInit;
        this.flagContinue = flagContinue;
        this.productoPeriodo = productoPeriodo;
        this.messageId = messageId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Boolean getFlagInit() {
        return flagInit;
    }

    public void setFlagInit(Boolean flagInit) {
        this.flagInit = flagInit;
    }

    public Boolean getFlagContinue() {
        return flagContinue;
    }

    public void setFlagContinue(Boolean flagContinue) {
        this.flagContinue = flagContinue;
    }

    public String getProductoPeriodo() {
        return productoPeriodo;
    }

    public void setProductoPeriodo(String productoPeriodo) {
        this.productoPeriodo = productoPeriodo;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Override
    public String toString() {
        return "Sms{" + "from=" + from + ", text=" + text + ", to=" + to + ", flagInit=" + flagInit + ", flagContinue=" + flagContinue + ", productoPeriodo=" + productoPeriodo + ", messageId=" + messageId + '}';
    }
    
}