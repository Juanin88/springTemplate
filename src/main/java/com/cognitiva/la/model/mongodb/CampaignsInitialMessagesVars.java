/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitiva.la.model.mongodb;

/**
 *
 * @author jgarfias2
 */
public class CampaignsInitialMessagesVars {
    private String claveVar;
    private String nameVar;

    public CampaignsInitialMessagesVars() {
    }

    public CampaignsInitialMessagesVars(String claveVar, String nameVar) {
        this.claveVar = claveVar;
        this.nameVar = nameVar;
    }

    public String getClaveVar() {
        return claveVar;
    }

    public void setClaveVar(String claveVar) {
        this.claveVar = claveVar;
    }

    public String getNameVar() {
        return nameVar;
    }

    public void setNameVar(String nameVar) {
        this.nameVar = nameVar;
    }

    @Override
    public String toString() {
        return "CampaignsInitialMessagesVars{" + "claveVar=" + claveVar + ", nameVar=" + nameVar + '}';
    }

}
