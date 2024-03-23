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
@RequestMapping("/super")
public class SuperController {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @GetMapping("/add")
    public String addUser(Model model){
        model.addAttribute(new SimpleUser());
        return "super-add";
    }

    @PostMapping("/add")
    public String addUser(SimpleUser simpleUser){
        return customUserDetailsService.addUser(simpleUser) ? "redirect:/super/add?success" : "redirect:/super/add?failure";
    }

}
