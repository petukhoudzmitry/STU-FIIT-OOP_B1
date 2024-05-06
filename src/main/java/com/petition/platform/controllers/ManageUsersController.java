package com.petition.platform.controllers;

import com.petition.platform.models.AbstractPetition;
import com.petition.platform.models.User;
import com.petition.platform.repositories.*;
import com.petition.platform.roles.Roles;
import com.petition.platform.services.CustomUserDetailsService;
import com.petition.platform.services.UserDetailsPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    static List<User> usersToBlock = new ArrayList<>();
    static List<AbstractPetition> petitionsToBlock = new ArrayList<>();

    @GetMapping("")
    public String adminHome(Model model){

        UserDetailsPrincipal userDetailsPrincipal = (UserDetailsPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Roles role = userDetailsPrincipal.getRole();

        model.addAttribute("simpleUsersCount", simpleUserRepository.count());
        model.addAttribute("companyUsersCount", companyUserRepository.count());
        model.addAttribute("adminUsersCount", adminUserRepository.count());
        model.addAttribute("superUsersCount", superUserRepository.count());
        model.addAttribute("petitionsCount", simplePetitionRepository.count());

        if(usersToBlock.isEmpty()){
            switch(role){
                case ADMIN -> model.addAttribute("users", simpleUserRepository.findAll());
                case SUPER -> {
                    List<User> users = new ArrayList<>(simpleUserRepository.findAll());
                    users.addAll(adminUserRepository.findAll());
                    users.addAll(companyUserRepository.findAll());
                    if(userDetailsPrincipal.getIsRoot()){
                        users.addAll(superUserRepository.findAllByIsRootIsFalse());
                    }
                    model.addAttribute("users", users);
                }
                default -> model.addAttribute("users", new ArrayList<User>());
            }
        }else{
            model.addAttribute("users", new ArrayList<>(usersToBlock));
            usersToBlock.clear();
        }

        if(petitionsToBlock.isEmpty()){
            model.addAttribute("petitions", simplePetitionRepository.findAll());
        }else{
            model.addAttribute("petitions", new ArrayList<>(petitionsToBlock));
            petitionsToBlock.clear();
        }

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

    @GetMapping("/block/users")
    public String blockUser(@RequestParam(name = "search", defaultValue = "")String search, Model model){
        UserDetailsPrincipal userDetailsPrincipal = (UserDetailsPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Roles role = userDetailsPrincipal.getRole();

        usersToBlock.clear();

        switch(role){
            case ADMIN -> usersToBlock.addAll(simpleUserRepository.findByUsernameContaining(search));
            case SUPER -> {
                usersToBlock.addAll(simpleUserRepository.findByUsernameContaining(search));
                usersToBlock.addAll(adminUserRepository.findByUsernameContaining(search));
                usersToBlock.addAll(companyUserRepository.findByUsernameContaining(search));
                if(userDetailsPrincipal.getIsRoot()){
                    usersToBlock.addAll(superUserRepository.findAllByUsernameContainingAndIsRootIsFalse(search));
                }
            }
        }

        return "redirect:/admin";
    }

    @PostMapping("/block/users")
    public String blockUsers(@RequestParam(name = "email") String email){
        simpleUserRepository.findByEmail(email).ifPresentOrElse(user -> {
            user.setEnabled(false);
            simpleUserRepository.save(user);
        }, () -> adminUserRepository.findByEmail(email).ifPresentOrElse(user -> {
            user.setEnabled(false);
            adminUserRepository.save(user);
        }, () -> companyUserRepository.findByEmail(email).ifPresentOrElse(user -> {
            user.setEnabled(false);
            companyUserRepository.save(user);
        }, () -> {
                    if (((UserDetailsPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getIsRoot()) {
                        superUserRepository.findByEmail(email).ifPresentOrElse(user -> {
                            user.setEnabled(false);
                            superUserRepository.save(user);
                        }, () -> {
                        });
                    }
                }
        )));

        return "redirect:/admin";
    }

    @PostMapping("/unblock/users")
    public String unblockUsers(@RequestParam(name = "email") String email){
        simpleUserRepository.findByEmail(email).ifPresentOrElse(user -> {
            user.setEnabled(true);
            simpleUserRepository.save(user);
        }, () -> adminUserRepository.findByEmail(email).ifPresentOrElse(user -> {
            user.setEnabled(true);
            adminUserRepository.save(user);
        }, () -> companyUserRepository.findByEmail(email).ifPresentOrElse(user -> {
            user.setEnabled(true);
            companyUserRepository.save(user);
        }, () -> superUserRepository.findByEmail(email).ifPresentOrElse(user -> {
            user.setEnabled(true);
            superUserRepository.save(user);
        }, () -> {}))));

        return "redirect:/admin";
    }

    @GetMapping("/block/petitions")
    public String blockPetitions(@RequestParam(name = "search", defaultValue = "") String search, Model model){
        UserDetailsPrincipal userDetailsPrincipal = (UserDetailsPrincipal)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Roles role = userDetailsPrincipal.getRole();

        petitionsToBlock.clear();

        switch(role){
            case ADMIN, SUPER -> petitionsToBlock.addAll(simplePetitionRepository.findByTitleContaining(search));
        }

        System.out.println(petitionsToBlock.size());

        return "redirect:/admin";
    }

    @PostMapping("/block/petitions")
    public String blockPetitions(@RequestParam(name = "id") UUID id){

        simplePetitionRepository.findById(id).ifPresent(petition -> {
            petition.setValid(false);
            simplePetitionRepository.save(petition);
        });

        return "redirect:/admin";
    }

    @PostMapping("/unblock/petitions")
    public String unblockPetitions(@RequestParam(name = "id") UUID id){

        simplePetitionRepository.findById(id).ifPresent(petition -> {
            petition.setValid(true);
            simplePetitionRepository.save(petition);
        });

        return "redirect:/admin";
    }
}