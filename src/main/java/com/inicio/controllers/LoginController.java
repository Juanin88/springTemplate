package com.inicio.controllers;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    
    @RequestMapping("/**")
    public String handleRequest(HttpServletRequest request, Model model) {
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        model.addAttribute("uri", request.getRequestURI())
                .addAttribute("user", auth.getName())
                .addAttribute("roles", auth.getAuthorities());
        return "my-page";
    }

}
