/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitiva.controllers;

import com.cognitiva.la.model.mongodb.Cliente;
import com.cognitiva.la.model.mongodb.ClienteCuenta;
import com.cognitiva.la.model.sms.FileBucket;
import com.cognitiva.la.repository.ClienteRepository;
import com.cognitiva.la.services.EtlImpl;
import com.cognitiva.la.util.FileValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author jgarfias
 */
@Controller
@RequestMapping("/clients")
public class ClientsController {
    
    @Value("${path}")
    private static String UPLOAD_LOCATION;

    @Autowired
    FileValidator fileValidator;

    @InitBinder("fileBucket")
    protected void initBinderFileBucket(WebDataBinder binder) {
        binder.setValidator(fileValidator);
    }
    
    @RequestMapping("index.htm")
    public ModelAndView index() throws JsonProcessingException {
        ModelAndView mav = new ModelAndView();
        Cliente cliente = new Cliente();

        ClienteCuenta clienteCuenta = new ClienteCuenta();

        ClienteRepository clienteRepository = new ClienteRepository();

        List<Cliente> clientes = clienteRepository.getClientes();

        mav.addObject("clientes", clientes);
        mav.addObject("clienteForm", cliente);
        mav.addObject("clienteCuentaForm", clienteCuenta);
        mav.addObject("actionFormCliente", "addNewClient");
        mav.addObject("titleModal", "Agregar");
        FileBucket fileModel = new FileBucket();
        mav.addObject("fileBucket", fileModel);

        return mav;
    }

    @RequestMapping(value = "/index.htm", method = RequestMethod.POST)
    public String index(@Valid FileBucket fileBucket, BindingResult result, ModelMap model) throws IOException, FileNotFoundException, ParseException {

        if (result.hasErrors()) {
            System.out.println("validation errors : " + result.getAllErrors().toString());
            return "/clients/index";
        } else {
            MultipartFile multipartFile = fileBucket.getFile();

            InputStream stream = multipartFile.getInputStream();
            System.out.println(stream.toString());

            //Now do something with file...
            FileCopyUtils.copy(fileBucket.getFile().getBytes(), new File(UPLOAD_LOCATION + fileBucket.getFile().getOriginalFilename()));

            EtlImpl instance = new EtlImpl();
            String path = UPLOAD_LOCATION + fileBucket.getFile().getOriginalFilename();            
            
            if ("addClientTelCSV".equals(fileBucket.getFormAction() ) ) {
                instance.uploadClienteTelsFile(path, fileBucket);
            }
            
            if ("updateClientsCSV".equals(fileBucket.getFormAction() ) ) {
                instance.updateClientesFile(path, fileBucket);
            } 
            
            String fileName = multipartFile.getOriginalFilename();
            model.addAttribute("fileName", fileName);
            return "redirect:/clients/index.htm";

        }
    }
    
}
