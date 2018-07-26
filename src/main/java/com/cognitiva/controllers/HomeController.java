/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitiva.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author jgarfias
 */
@Controller
@RequestMapping("/home")
public class HomeController {


    @RequestMapping("/index.htm")
    public ModelAndView home() {

        ModelAndView mav = new ModelAndView();
        //mav.setViewName("index");
        return mav;
    }

  

}
