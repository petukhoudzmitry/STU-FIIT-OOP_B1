package com.petition.platform.services;

import com.petition.platform.models.CompanyUser;
import com.petition.platform.models.SimplePetition;
import com.petition.platform.ooprequirements.Actions;
import com.petition.platform.ooprequirements.CreatePetitionActionListener;
import com.petition.platform.ooprequirements.EventManager;
import com.petition.platform.repositories.CompanyUserRepository;
import com.petition.platform.repositories.SimplePetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class for managing custom petition-related operations.
 */
@Service
public class CustomPetitionDetailsService implements UserDetailsService {
    /**
     * default constructor for CustomPetitionDetailsService.
     */
    public CustomPetitionDetailsService() {}

    @Autowired
    private CompanyUserRepository companyUserRepository;
    @Autowired
    private SimplePetitionRepository simplePetitionRepository;

    private static final EventManager eventManager;
    static{
        eventManager = EventManager.getInstance();
        eventManager.subscribe(Actions.CREATE_PETITION, new CreatePetitionActionListener());
    }

    /**
     * Loads a user by username (in this case, by petition title).
     *
     * @param username the title of the petition.
     * @return the user details principal associated with the petition title.
     * @throws UsernameNotFoundException if the petition title is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new PetitionDetailsPrincipal(simplePetitionRepository.findByTitleContaining(username).getFirst());
    }

    /**
     * Adds a new petition.
     *
     * @param simplePetition the petition to add.
     * @param id             the ID of the company user adding the petition.
     * @return true if the petition is successfully added, false otherwise.
     */
    public boolean addPetition(SimplePetition simplePetition, Long id) {
        CompanyUser[] companyUser = new CompanyUser[1];
        companyUserRepository.findById(id).ifPresent(user -> companyUser[0] = user);

        List<SimplePetition> optional = simplePetitionRepository.findByCompanyAndCreatorOrderByValidUntilDesc(
                companyUser[0],
                simplePetition.getCreator()
        );

        if(!optional.isEmpty() && optional.getFirst().getValidUntil().isAfter(LocalDateTime.now())){
            return false;
        }

        setupDefaultSimplePetition(simplePetition, companyUser[0]);
        simplePetitionRepository.save(simplePetition);
        eventManager.notify(Actions.CREATE_PETITION,  simplePetition.getCreator().getEmail(), simplePetition.getId().toString());
        return true;
    }

    /**
     * Sets up default values for a new petition.
     *
     * @param simplePetition the petition to set up.
     * @param companyUser    the company user associated with the petition.
     */
    public void setupDefaultSimplePetition(SimplePetition simplePetition, CompanyUser companyUser) {
        simplePetition.setCompany(companyUser);
        simplePetition.setIsValid(true);
    }
}