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

/**
 * Controller class for handling authentication-related endpoints.
 */
@Controller
@RequestMapping("/auth")
public class AuthController {
    /**
     * Default constructor for the AuthController.
     */
    public AuthController() {}

    /**
     * Autowired CustomUserDetailsService for user-related operations.
     */
    @Autowired
    private CustomUserDetailsService userDetailsService;

    /**
     * Handles GET requests for user registration page.
     *
     * @param model Model object to add attributes for the view.
     * @return View name for user registration.
     */
    @GetMapping("/register")
    public String register(Model model){
        // Redirect to home page if user is already authenticated
        if(isAuthenticated()){
            return "redirect:/home";
        }
        // Add a new SimpleUser object to the model for registration
        model.addAttribute(new SimpleUserFactory().createUser());
        return "register";
    }

    /**
     * Handles GET requests for company user registration page.
     *
     * @param model Model object to add attributes for the view.
     * @return View name for company user registration.
     */
    @GetMapping("/register/company")
    public String registerCompany(@RequestParam(defaultValue="") Model model){
        // Redirect to home page if user is already authenticated
        if(isAuthenticated()){
            return "redirect:/home";
        }
        // Add a new CompanyUser object to the model for registration
        model.addAttribute(new CompanyUserFactory().createUser());
        return "register-company";
    }

    /**
     * Handles POST requests for company user registration.
     *
     * @param companyUser CompanyUser object containing registration data.
     * @return Redirect to home page if successful, otherwise redirect to registration page with an error message.
     */
    @PostMapping("/register/company")
    public String register(CompanyUser companyUser){
        return isAuthenticated() ? "redirect:/home" :
                userDetailsService.addCompanyUser(companyUser) ? "redirect:/auth/login" : "redirect:/auth/register?error";
    }

    /**
     * Handles POST requests for simple user registration.
     *
     * @param simpleUser SimpleUser object containing registration data.
     * @return Redirect to home page if successful, otherwise redirect to registration page with an error message.
     */
    @PostMapping("/register")
    public String register(SimpleUser simpleUser){
        return isAuthenticated() ? "redirect:/home" :
                userDetailsService.addSimpleUser(simpleUser) ? "redirect:/auth/login" : "redirect:/auth/register?error";
    }

    /**
     * Handles GET requests for login page.
     *
     * @return View name for the login page.
     */
    @GetMapping("/login")
    public String login(){
        // Redirect to home page if user is already authenticated
        if(isAuthenticated()){
            return "redirect:/home";
        }
        return "login";
    }

    /**
     * Checks if the current user is authenticated.
     *
     * @return True if the user is authenticated, otherwise false.
     */
    private boolean isAuthenticated(){
        return !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
    }
}