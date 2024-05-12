package com.petition.platform.controllers;

import com.petition.platform.models.CompanyUser;
import com.petition.platform.models.SimpleUser;
import com.petition.platform.ooprequirements.CompanyUserFactory;
import com.petition.platform.ooprequirements.SimpleUserFactory;
import com.petition.platform.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private CustomUserDetailsService userDetailsService;


    @GetMapping("/register")
    public String register(Model model){
        if(isAuthenticated()){
            return "redirect:/home";
        }
        model.addAttribute(new SimpleUserFactory().createUser());
        return "register";
    }

    @GetMapping("/register/company")
    public String registerCompany(@RequestParam(defaultValue="") Model model){
        if(isAuthenticated()){
            return "redirect:/home";
        }
        model.addAttribute(new CompanyUserFactory().createUser());
        return "register-company";
    }

    @PostMapping("/register/company")
    public String register(CompanyUser companyUser){
        return isAuthenticated() ? "redirect:/home" :
                userDetailsService.addCompanyUser(companyUser) ? "redirect:/auth/login" : "redirect:/auth/register?error";
    }

    @PostMapping("/register")
    public String register(SimpleUser simpleUser){
        return isAuthenticated() ? "redirect:/home" :
                userDetailsService.addSimpleUser(simpleUser) ? "redirect:/auth/login" : "redirect:/auth/register?error";
    }

    @GetMapping("/login")
    public String login(){
        if(isAuthenticated()){
            return "redirect:/home";
        }
        return "login";
    }

    private boolean isAuthenticated(){
        return !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
    }
}