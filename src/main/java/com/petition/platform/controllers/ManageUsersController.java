package com.petition.platform.controllers;

import com.petition.platform.models.User;
import com.petition.platform.repositories.*;
import com.petition.platform.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class ManageUsersController {

    @Autowired
    CustomUserDetailsService customUserDetailsService;
    @Autowired
    SimpleUserRepository simpleUserRepository;
    @Autowired
    CompanyUserRepository companyUserRepository;
    @Autowired
    AdminUserRepository adminUserRepository;
    @Autowired
    SuperUserRepository superUserRepository;
    @Autowired
    SimplePetitionRepository simplePetitionRepository;

    @GetMapping("")
    public String adminHome(Model model){
        model.addAttribute("simpleUsers", simpleUserRepository.count());
        model.addAttribute("companyUsers", companyUserRepository.count());
        model.addAttribute("adminUsers", adminUserRepository.count());
        model.addAttribute("superUsers", superUserRepository.count());
        model.addAttribute("petitions", simplePetitionRepository.count());
        return "admin-home";
    }

    @GetMapping("/add")
    public String addUser(Model model){
        model.addAttribute(new User());
        return "super-add";
    }

    @PostMapping("/add")
    public String addUser(User user){
        return customUserDetailsService.addUser(user) ? "redirect:/admin/add?success" : "redirect:/admin/add?failure";
    }

}
