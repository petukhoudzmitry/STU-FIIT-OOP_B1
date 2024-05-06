package com.petition.platform.controllers;

import com.petition.platform.models.*;
import com.petition.platform.repositories.*;
import com.petition.platform.roles.Roles;
import com.petition.platform.services.CustomUserDetailsService;
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

    static List<User> foundedUsers = new ArrayList<>();
    static List<AbstractPetition> foundedPetitions = new ArrayList<>();
    static List<CompanyUser> foundedCompanies = new ArrayList<>();

    @GetMapping("")
    public String home(Model model) {
        UserDetailsPrincipal userDetailsPrincipal = (UserDetailsPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Roles role = userDetailsPrincipal.getRole();

        model.addAttribute("companies", foundedCompanies.isEmpty() ? companyUserRepository.findAll() : new ArrayList<>(foundedCompanies));

        if(foundedUsers.isEmpty()){
            List<User> users = new ArrayList<>(simpleUserRepository.findAll());
            users.addAll(companyUserRepository.findAll());
            users.addAll(adminUserRepository.findAll());
            users.addAll(superUserRepository.findAll());
            model.addAttribute("users", users);
        }else{
            model.addAttribute("users", new ArrayList<>(foundedUsers));
        }

        model.addAttribute("petitions", foundedPetitions.isEmpty() ? simplePetitionRepository.findAll() : new ArrayList<>(foundedPetitions));

        switch(role){
            case Roles.USER -> simpleUserRepository.findById(userDetailsPrincipal.getId()).ifPresent(
                    user -> {
                        model.addAttribute("votedUpPetitions", user.getSignedPetitions());
                        model.addAttribute("createdPetitions", user.getPetitions());
                        model.addAttribute("signedPetitions", user.getSignedPetitions());
                    }
            );
            case Roles.COMPANY -> companyUserRepository.findById(userDetailsPrincipal.getId()).ifPresent(
                    user -> model.addAttribute("companyPetitions", user.getPetitions())
            );
            case Roles.ADMIN -> {
//                AdminUser user = adminUserRepository.findById(userDetailsPrincipal.getId()).get();
            }
            case Roles.SUPER -> {
//                SuperUser user = superUserRepository.findById(userDetailsPrincipal.getId()).get();
            }
        }
        return "home";
    }

    @GetMapping("/companies")
    public String companies(@RequestParam(name="search", defaultValue = "") String search) {
        foundedCompanies.clear();
        foundedCompanies.addAll(companyUserRepository.findByUsernameContaining(search));
        return "redirect:/home";
    }

    @PostMapping("/retract")
    public String home(@RequestParam(name = "id")UUID id){
        UserDetailsPrincipal userDetailsPrincipal = (UserDetailsPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (userDetailsPrincipal.getRole() == Roles.USER) {
            simpleUserRepository.findById(userDetailsPrincipal.getId()).ifPresent(
                    user -> {
                        simplePetitionRepository.findById(id).ifPresent(user.getSignedPetitions()::remove);
                        simpleUserRepository.save(user);
                    }
            );
        }
        return "redirect:/home";
    }

    @GetMapping("/petitions")
    public String petitions(@RequestParam(name = "search", defaultValue = "") String search) {
        foundedPetitions.clear();
        foundedPetitions.addAll(simplePetitionRepository.findByTitleContaining(search));
        foundedPetitions.addAll(simplePetitionRepository.findByTextContaining(search));
        foundedPetitions.addAll(simplePetitionRepository.findByCompanyUsernameContaining(search));
        foundedPetitions = foundedPetitions.stream().sorted().distinct().sorted(Comparator.comparing(AbstractPetition::getValidUntil)).toList();
        return "redirect:/home";
    }

    @PostMapping("/petitions/vote")
    public String vote(@RequestParam(name = "id") UUID id){
        simpleUserRepository.findById(((UserDetailsPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()).ifPresent(
                user -> simplePetitionRepository.findById(id).ifPresent(
                        petition -> {
                            user.getSignedPetitions().add(petition);
                            simpleUserRepository.save(user);
                        }
                )
        );
        return "redirect:/home";
    }

    @PostMapping("/petitions/retract")
    public String retract(@RequestParam(name = "id") UUID id){
        simpleUserRepository.findById(((UserDetailsPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()).ifPresent(
                user -> simplePetitionRepository.findById(id).ifPresent(
                        petition -> {
                            user.getSignedPetitions().remove(petition);
                            simpleUserRepository.save(user);
                        }
                )
        );
        return "redirect:/home";
    }

    @GetMapping("/users")
    public String searchUsers(@RequestParam(name="search", defaultValue = "") String search){
        foundedUsers.clear();
        foundedUsers.addAll(simpleUserRepository.findByUsernameContaining(search));
        foundedUsers.addAll(adminUserRepository.findByUsernameContaining(search));
        foundedUsers.addAll(superUserRepository.findByUsernameContaining(search));
        foundedUsers.sort(Comparator.comparing(a -> a.getUsername().toUpperCase()));
        return "redirect:/home";
    }
}