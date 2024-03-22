package com.petition.platform.controllers;

import com.petition.platform.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String index(Model model, @RequestParam(name = "name", defaultValue = "user") String name) {
        model.addAttribute("name", name);
        return "index";
    }
}
