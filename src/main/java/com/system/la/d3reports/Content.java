/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.la.d3reports;

import java.util.List;

/**
 *
 * @author jgarfias2
 */
public class Content {

    private List<Contents> content;

    public Content() {
    }

    public Content(List<Contents> content) {
        this.content = content;
    }

    public List<Contents> getContent() {
        return content;
    }

    public void setContent(List<Contents> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Content{" + "content=" + content + '}';
    }

}
