package com.petition.platform.controllers;

import com.petition.platform.models.AbstractPetition;
import com.petition.platform.models.SimplePetition;
import com.petition.platform.models.SimpleUser;
import com.petition.platform.models.User;
import com.petition.platform.repositories.*;
import com.petition.platform.roles.Roles;
import com.petition.platform.services.UserDetailsPrincipal;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

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
    @Autowired
    private SimplePetitionRepository simplePetitionRepository;

    @GetMapping("")
    public String home() {
        return "home";
    }

    @GetMapping("/companies")
    public String companies(@RequestParam(name="search", defaultValue = "") String search, Model model) {
        model.addAttribute("companies", companyUserRepository.findByUsernameContaining(search));
        return "home-companies";
    }

    @GetMapping("/petitions")
    public String petitions(@RequestParam(name = "search", defaultValue = "") String search, Model model) {
        List<SimplePetition> simplePetitions = new ArrayList<>(simplePetitionRepository.findByTitleContaining(search));
        simplePetitions.addAll(simplePetitionRepository.findByTextContaining(search));
        simplePetitions.addAll(simplePetitionRepository.findByCompanyUsernameContaining(search));
        simplePetitions = simplePetitions.stream().sorted().distinct().sorted(Comparator.comparing(AbstractPetition::getValidUntil)).toList();
        model.addAttribute("petitions", simplePetitions);
        model.addAttribute("signedPetitions", simpleUserRepository.findById(((UserDetailsPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()).get().getSignedPetitions());
        return "home-petitions";
    }

    @PostMapping("/petitions/vote")
    public String vote(@RequestParam(name = "id") UUID id){
        SimpleUser simpleUser = simpleUserRepository.findById(((UserDetailsPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()).get();
        simpleUser.getSignedPetitions().add(simplePetitionRepository.findById(id).get());
        simpleUserRepository.save(simpleUser);
        return "redirect:/home/petitions";
    }

    @PostMapping("/petitions/retract")
    public String retract(@RequestParam(name = "id") UUID id){
        SimpleUser simpleUser = simpleUserRepository.findById(((UserDetailsPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()).get();
        simpleUser.getSignedPetitions().removeIf(e -> e.equals(simplePetitionRepository.findById(id).get()));
        simpleUserRepository.save(simpleUser);
        return "redirect:/home/petitions";
    }

    @GetMapping("/users")
    public String searchUsers(@RequestParam(name="search", defaultValue = "") String search, Model model){
        List<User> list = new ArrayList<>(simpleUserRepository.findByUsernameContaining(search));
        list.addAll(adminUserRepository.findByUsernameContaining(search));
        list.addAll(superUserRepository.findByUsernameContaining(search));
        list.sort(Comparator.comparing(a -> a.getUsername().toUpperCase()));
        model.addAttribute("users", list);
        return "home-users";
    }
}
