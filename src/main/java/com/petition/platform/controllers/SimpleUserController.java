package com.petition.platform.controllers;

import com.petition.platform.models.User;
import com.petition.platform.repositories.AdminUserRepository;
import com.petition.platform.repositories.CompanyUserRepository;
import com.petition.platform.repositories.SimpleUserRepository;
import com.petition.platform.repositories.SuperUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/home")
public class SimpleUserController {

    @Autowired
    private SimpleUserRepository simpleUserRepository;
    @Autowired
    private CompanyUserRepository companyUserRepository;
    @Autowired
    private AdminUserRepository adminUserRepository;
    @Autowired
    private SuperUserRepository superUserRepository;

    @GetMapping("")
    public String home() {
        return "home";
    }

    @GetMapping("/companies")
    public String companies(){
        return "home-companies";
    }

    @GetMapping("/petitions")
    public String petitions(){
        return "home-petitions";
    }

    @GetMapping("/users")
    public String users(Model model) {
        List<User> list = new ArrayList<>(simpleUserRepository.findAll());
        list.addAll(adminUserRepository.findAll());
        list.addAll(superUserRepository.findAll());
        list.sort(Comparator.comparing(a -> a.getUsername().toUpperCase()));
        model.addAttribute("users", list);
        return "home-users";
    }
}
