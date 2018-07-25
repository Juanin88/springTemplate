/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitiva.la.d3reports;

/**
 *
 * @author jgarfias2
 */
public class Contents {
    private String label;
    private int value;
    private String color;

    public Contents() {
    }

    public Contents(String label, int value, String color) {
        this.label = label;
        this.value = value;
        this.color = color;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Contents{" + "label=" + label + ", value=" + value + ", color=" + color + '}';
    }
    
    
}
