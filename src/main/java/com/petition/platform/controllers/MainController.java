package com.petition.platform.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller class for handling main endpoint requests.
 */
@Controller
public class MainController {
    /**
     * Default constructor for the MainController.
     */
    public MainController() {}

    /**
     * Handles GET requests for the main endpoint ("/").
     *
     * @return Redirects to the home page if the user is authenticated, otherwise returns the about page.
     */
    @GetMapping("/")
    public String index() {
        // Checking if the user is authenticated
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            return "redirect:/home"; // Redirect to home page if authenticated
        }
        return "about"; // Return about page if not authenticated
    }
}