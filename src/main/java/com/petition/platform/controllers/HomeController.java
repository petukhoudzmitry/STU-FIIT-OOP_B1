package com.petition.platform.controllers;

import com.petition.platform.models.*;
import com.petition.platform.repositories.*;
import com.petition.platform.roles.Roles;
import com.petition.platform.services.UserDetailsPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Controller class for handling home-related endpoints.
 */
@Controller
@RequestMapping("/home")
public class HomeController {
    /**
     * Default constructor for the HomeController.
     */
    public HomeController() {}

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

    static final List<User> foundedUsers = new ArrayList<>();
    static List<AbstractPetition> foundedPetitions = new ArrayList<>();
    static final List<CompanyUser> foundedCompanies = new ArrayList<>();

    /**
     * Handles GET requests for the home page.
     *
     * @param model Model object to add attributes for the view.
     * @return View name for the home page.
     */
    @GetMapping("")
    public String home(Model model) {
        UserDetailsPrincipal userDetailsPrincipal = (UserDetailsPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Roles role = userDetailsPrincipal.getRole();

        // Add companies to model
        model.addAttribute("companies", foundedCompanies.isEmpty() ? companyUserRepository.findAll() : new ArrayList<>(foundedCompanies));

        // Add users to model
        if(foundedUsers.isEmpty()){
            List<User> users = new ArrayList<>(simpleUserRepository.findAll());
            users.addAll(companyUserRepository.findAll());
            users.addAll(adminUserRepository.findAll());
            users.addAll(superUserRepository.findAll());
            model.addAttribute("users", users);
        }else{
            model.addAttribute("users", new ArrayList<>(foundedUsers));
        }

        // Add petitions to model
        model.addAttribute("petitions", foundedPetitions.isEmpty() ? simplePetitionRepository.findAll() : new ArrayList<>(foundedPetitions));

        // Add specific attributes based on user role
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
        }
        return "home";
    }

    /**
     * Handles GET requests for searching companies.
     *
     * @param search Search query.
     * @return Redirect to the home page.
     */
    @GetMapping("/companies")
    public String companies(@RequestParam(name="search", defaultValue = "") String search) {
        foundedCompanies.clear();
        foundedCompanies.addAll(companyUserRepository.findByUsernameContaining(search));
        return "redirect:/home";
    }

    /**
     * Handles POST requests for retracting petitions.
     *
     * @param id Petition ID.
     * @return Redirect to the home page.
     */
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

    /**
     * Handles GET requests for searching petitions.
     *
     * @param search Search query.
     * @return Redirect to the home page.
     */
    @GetMapping("/petitions")
    public String petitions(@RequestParam(name = "search", defaultValue = "") String search) {
        foundedPetitions.clear();
        foundedPetitions.addAll(simplePetitionRepository.findByTitleContaining(search));
        foundedPetitions.addAll(simplePetitionRepository.findByTextContaining(search));
        foundedPetitions.addAll(simplePetitionRepository.findByCompanyUsernameContaining(search));
        foundedPetitions = new ArrayList<>(foundedPetitions.stream().sorted().distinct().sorted(Comparator.comparing(AbstractPetition::getValidUntil)).toList());
        return "redirect:/home";
    }

    /**
     * Handles POST requests for voting on petitions.
     *
     * @param id Petition ID.
     * @return Redirect to the home page.
     */
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

    /**
     * Handles POST requests for retracting votes on petitions.
     *
     * @param id Petition ID.
     * @return Redirect to the home page.
     */
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

    /**
     * Handles GET requests for searching users.
     *
     * @param search Search query.
     * @return Redirect to the home page.
     */
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