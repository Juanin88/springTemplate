/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitiva.la.model.responses;

import com.cognitiva.la.model.mongodb.Campaign;

/**
 *
 * @author jgarfias
 */
public class AddCampaign {
    
    private int code;
    private String status;
    private String msg;
    private Campaign campaign;

    public AddCampaign() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public AddCampaign(int code, String status, String msg, Campaign campaign) {
        this.code = code;
        this.status = status;
        this.msg = msg;
        this.campaign = campaign;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }
    
    
}
