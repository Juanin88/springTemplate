/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitiva.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author jgarfias2
 */
@Controller
@RequestMapping("/test")
public class TestController {
   
    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String test() {
        return "test";
    }
}
