/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitiva.controllers;

import com.cognitiva.la.d3reports.Content;
import com.cognitiva.la.d3reports.Contents;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ws.rs.core.Response;
import org.bson.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jgarfias2
 */
@RestController
public class ApiController {

    @GetMapping("/rest-api/action/test")
    public Contents test() {

        Contents contents = new Contents();
        contents.setColor("red");
        contents.setLabel("color");
        contents.setValue(11);

        return contents;
    }

    @PostMapping("/rest-api/action/add-campaign")
    public ResponseEntity addCampaign(@RequestBody Contents contents) throws JsonProcessingException {

        contents.setColor("red");
        contents.setLabel("color");
        contents.setValue(11);



        return new ResponseEntity(contents, HttpStatus.OK);

    }

}
