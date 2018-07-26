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
public class Mensaje {

    private String type;
    private String content;
    private Integer order;

    public Mensaje(String type, String content, Integer order) {
        this.type = type;
        this.content = content;
        this.order = order;
    }

    public Mensaje() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "Mensaje{" + "type=" + type + ", content=" + content + ", order=" + order + '}';
    }

}
