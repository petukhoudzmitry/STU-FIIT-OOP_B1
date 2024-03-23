package com.petition.platform.controllers;

import com.petition.platform.models.SimpleUser;
import com.petition.platform.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute(new SimpleUser());
        return "register";
    }

    @PostMapping("/register")
    public String register(SimpleUser simpleUser){
        return userDetailsService.addSimpleUser(simpleUser) ? "login" : "redirect:/auth/register?error";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
