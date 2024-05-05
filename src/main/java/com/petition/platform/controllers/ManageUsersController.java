package com.petition.platform.controllers;

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

    @GetMapping("/block/users")
    public String blockUser(@RequestParam(name = "search", defaultValue = "")String search, Model model){
        UserDetailsPrincipal userDetailsPrincipal = (UserDetailsPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Roles role = userDetailsPrincipal.getRole();

        switch(role){
            case ADMIN -> model.addAttribute("users", simpleUserRepository.findAll());
            case SUPER -> {
                List<User> users = new ArrayList<>(simpleUserRepository.findByUsernameContaining(search));
                users.addAll(adminUserRepository.findByUsernameContaining(search));
                users.addAll(companyUserRepository.findByUsernameContaining(search));
                users.addAll(superUserRepository.findByUsernameContaining(search));

                model.addAttribute("users", users);
            }
            default -> model.addAttribute("users", null);
        }

        return "block-users";
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
        }, () -> superUserRepository.findByEmail(email).ifPresentOrElse(user -> {
            user.setEnabled(false);
            superUserRepository.save(user);
        }, () -> {}))));

        return "redirect:/admin/block/users";
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

        return "redirect:/admin/block/users";
    }
}
