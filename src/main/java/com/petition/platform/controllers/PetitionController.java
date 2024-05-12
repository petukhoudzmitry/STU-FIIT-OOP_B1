package com.petition.platform.controllers;

import com.petition.platform.models.SimplePetition;
import com.petition.platform.models.SimpleUser;
import com.petition.platform.repositories.CompanyUserRepository;
import com.petition.platform.repositories.SimpleUserRepository;
import com.petition.platform.services.CustomPetitionDetailsService;
import com.petition.platform.services.UserDetailsPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/petition")
public class PetitionController {

    @Autowired
    private CompanyUserRepository companyUserRepository;
    @Autowired
    private CustomPetitionDetailsService customPetitionDetailsService;
    @Autowired
    private SimpleUserRepository simpleUserRepository;

    @GetMapping("/add")
    public String add(Model model, SimpleUser user) {
        SimplePetition petition = new SimplePetition();
        petition.setCreator(user);
        model.addAttribute(petition);
        model.addAttribute("companies", companyUserRepository.findAll());
        return "petition-add";
    }

    @PostMapping("/add")
    public String add(@RequestParam(name = "companyId") Long id, SimplePetition petition) {
        simpleUserRepository.findById(((UserDetailsPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()).ifPresent(petition::setCreator);

        return customPetitionDetailsService.addPetition(petition, id) ? "redirect:/petition/add?success" : "redirect:/petition/add?failure";
    }
}
