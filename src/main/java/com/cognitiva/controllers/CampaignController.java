/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitiva.controllers;

import com.cognitiva.la.model.sms.FileBucket;
import com.cognitiva.la.model.mongodb.Campaign;
import com.cognitiva.la.model.mongodb.CampaignsInitialMessages;
import com.cognitiva.la.model.mongodb.CampaignsInitialMessagesVars;
import com.cognitiva.la.model.mongodb.Cliente;
import com.cognitiva.la.model.mongodb.ClienteCuenta;
import com.cognitiva.la.repository.CampaignRepository;
import com.cognitiva.la.repository.CampaignsInitialMessagesRepository;
import com.cognitiva.la.repository.ClienteRepository;
import com.cognitiva.la.services.EtlImpl;
import com.cognitiva.la.util.FileValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.owlike.genson.Genson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author jgarfias
 *
 */
@Controller
@RequestMapping("/campaign")
public class CampaignController {

    @Value("${path}")
    private static String UPLOAD_LOCATION;

    @Autowired
    FileValidator fileValidator;

    @InitBinder("fileBucket")
    protected void initBinderFileBucket(WebDataBinder binder) {
        binder.setValidator(fileValidator);
    }

    public CampaignController() {
    }

    @RequestMapping("index.htm")
    public ModelAndView index() {

        CampaignRepository cri = new CampaignRepository();

        List<Campaign> campaigns = cri.getCampaigns();

        Map<String, String> productoTipo = new LinkedHashMap<String, String>();
        productoTipo.put("", "Seleccione un producto...");
        productoTipo.put("TC-Bs", "Tarjeta Básica");
        productoTipo.put("TC-Or", "Tarjeta Oro");
        productoTipo.put("TC-Pt", "Tarjeta Platino");
        productoTipo.put("TC-Bk", "Tarjeta Black");

        ModelAndView mav = new ModelAndView();
        mav.addObject("id", "test");
        mav.addObject("campaigns", campaigns);
        mav.addObject("command", new Campaign());
        mav.addObject("productoTipo", productoTipo);

        return mav;

    }

    @RequestMapping(value = "/clientes.htm", method = RequestMethod.GET)
    public ModelAndView clientes(@RequestParam("clave") String clave) throws JsonProcessingException {

        ModelAndView mav = new ModelAndView();

        ClienteRepository clienteRepository = new ClienteRepository();
        Campaign campaign = clienteRepository.findByClaveCampaign(clave);

        CampaignsInitialMessagesRepository cimr = new CampaignsInitialMessagesRepository();
        CampaignsInitialMessages cim = cimr.findById(campaign.getIdCampaignsInitialMessages());

        mav.addObject("CampaignsInitialMessages", cim);

        mav.addObject("campaign", campaign);

        Cliente cliente = new Cliente();
        ClienteCuenta clienteCuenta = new ClienteCuenta();

        mav.addObject("clienteForm", cliente);
        mav.addObject("clienteCuentaForm", clienteCuenta);
        mav.addObject("actionFormCliente", "addNewClientToCampaign");

        FileBucket fileModel = new FileBucket();

        fileModel.setClave(clave);
        fileModel.setClaveCampaign(clave);

        mav.addObject("fileBucket", fileModel);

        return mav;

    }

    @RequestMapping(value = "/clientes.htm", method = RequestMethod.POST)
    public String clientes(@Valid FileBucket fileBucket, BindingResult result, ModelMap model) throws IOException, FileNotFoundException, ParseException {

        String clave = fileBucket.getClave();

        if (result.hasErrors()) {
            System.out.println("validation errors : " + result.getAllErrors().toString());
            return "/campaign/newCampaign";
        } else {
            System.out.println("Fetching file");
            MultipartFile multipartFile = fileBucket.getFile();

            InputStream stream = multipartFile.getInputStream();
            System.out.println(stream.toString());

            //Now do something with file...
            FileCopyUtils.copy(fileBucket.getFile().getBytes(), new File(UPLOAD_LOCATION + fileBucket.getFile().getOriginalFilename()));

            EtlImpl instance = new EtlImpl();
            String path = UPLOAD_LOCATION + fileBucket.getFile().getOriginalFilename();

            if (fileBucket.getFormAction().equals("desClientCSV")) {
                System.out.println("Desasigación!!!");
                instance.desClientFromCampaignFile(path, fileBucket);
            }

            if (fileBucket.getFormAction().equals("addClientCSV")) {
                instance.uploadClienteFileToCampaing(path, fileBucket, clave);
            }

            String fileName = multipartFile.getOriginalFilename();
            model.addAttribute("fileName", fileName);
            model.addAttribute("clave", fileBucket.getClave());

            return "redirect:/campaign/success.htm";

        }

    }

    @RequestMapping(value = "/newCampaign", method = RequestMethod.GET)
    public ModelAndView newCampaign(ModelMap model) throws NoSuchAlgorithmException, JsonProcessingException {

        ModelAndView mav = new ModelAndView();
        FileBucket fileModel = new FileBucket();

        String uniqueID = UUID.randomUUID().toString();

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(uniqueID.getBytes());
        BigInteger number = new BigInteger(1, messageDigest);
        String hashtext = number.toString(16);

        fileModel.setClave(hashtext);

        model.addAttribute("fileBucket", fileModel);

        Map<String, String> productoTipo = new LinkedHashMap<String, String>();
        productoTipo.put("", "Seleccione un producto...");
        productoTipo.put("TC-Bs", "Tarjeta Básica");
        productoTipo.put("TC-Or", "Tarjeta Oro");
        productoTipo.put("TC-Pt", "Tarjeta Platino");
        productoTipo.put("TC-Bk", "Tarjeta Black");

        mav.addObject("productoTipo", productoTipo);

        CampaignsInitialMessagesRepository campaignsInitialMessagesRepository = new CampaignsInitialMessagesRepository();
        List<CampaignsInitialMessages> listMesajes = campaignsInitialMessagesRepository.getMensajesIniciales();
        Map<String, String> listMesajesMap = new LinkedHashMap<String, String>();

        for (CampaignsInitialMessages msg : listMesajes) {
            listMesajesMap.put(msg.getId(), msg.getNombreMsg() + " - " + msg.getDescripcion());
        }

        mav.addObject("listMesajes", listMesajesMap);

        return mav;

    }

    @RequestMapping(value = "/newCampaign", method = RequestMethod.POST)
    public String newCampaign(@Valid FileBucket fileBucket, BindingResult result, ModelMap model) throws IOException, FileNotFoundException, ParseException {

        if (result.hasErrors()) {
            System.out.println("validation errors : " + result.getAllErrors().toString());
            return "/campaign/newCampaign";
        } else {
            System.out.println("Fetching file");
            MultipartFile multipartFile = fileBucket.getFile();

            InputStream stream = multipartFile.getInputStream();
            System.out.println(stream.toString());

            //Now do something with file...
            FileCopyUtils.copy(fileBucket.getFile().getBytes(), new File(UPLOAD_LOCATION + fileBucket.getFile().getOriginalFilename()));

            EtlImpl instance = new EtlImpl();
            String path = UPLOAD_LOCATION + fileBucket.getFile().getOriginalFilename();

            instance.uploadClienteFile(path, fileBucket);

            String fileName = multipartFile.getOriginalFilename();
            model.addAttribute("fileName", fileName);
            return "redirect:/campaign/success.htm";

        }
    }

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public ModelAndView success() {

        ModelAndView mav = new ModelAndView();

        return mav;

    }

    @RequestMapping(value = "/campaignsInitialMessages", method = RequestMethod.GET)
    public ModelAndView campaignsInitialMessages() throws JsonProcessingException {

        ModelAndView mav = new ModelAndView();

        CampaignsInitialMessagesRepository cimr = new CampaignsInitialMessagesRepository();

        List<CampaignsInitialMessagesVars> listCampaignsInitialMessagesVars = cimr.getVars();

        System.out.println("variables :" + listCampaignsInitialMessagesVars.toString());

        mav.addObject("messagesVars", listCampaignsInitialMessagesVars);

        CampaignsInitialMessagesRepository campaignsInitialMessagesRepository = new CampaignsInitialMessagesRepository();

        List<CampaignsInitialMessages> listMesajes = campaignsInitialMessagesRepository.getMensajesIniciales();

        Genson genson = new Genson();

        String json = genson.serialize(listMesajes);

        mav.addObject("listMesajes", listMesajes);
        mav.addObject("listMesajesJson", json);

        return mav;

    }

    @ResponseBody
    @RequestMapping(value = "/download-layout")
    public String txtResponse(@RequestParam("clave") String clave, HttpServletResponse response) throws JsonProcessingException {
        String fileName = "a.txt";
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

      

        EtlImpl etlImpl = new EtlImpl();
        String content = etlImpl.generateLayout(clave);
                
        
        
        return content;
    }

    
    


}
