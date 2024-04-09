package com.petition.platform.services;

import com.petition.platform.models.CompanyUser;
import com.petition.platform.models.SimplePetition;
import com.petition.platform.repositories.CompanyUserRepository;
import com.petition.platform.repositories.SimplePetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CustomPetitionDetailsService implements UserDetailsService {

    @Autowired
    private CompanyUserRepository companyUserRepository;
    @Autowired
    private SimplePetitionRepository simplePetitionRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new PetitionDetailsPrincipal(simplePetitionRepository.findByTitleContaining(username).getFirst());
    }

    public boolean addPetition(SimplePetition simplePetition, Long id) {
        CompanyUser companyUser = companyUserRepository.findById(id).get();

        List<SimplePetition> optional = simplePetitionRepository.findByCompanyAndCreatorOrderByValidUntilDesc(
                companyUser,
                simplePetition.getCreator()
        );

        if(!optional.isEmpty() && optional.getFirst().getValidUntil().isAfter(LocalDateTime.now())){
            return false;
        }

        setupDefaultSimplePetition(simplePetition, companyUser);
        simplePetitionRepository.save(simplePetition);
        return true;
    }

    public void setupDefaultSimplePetition(SimplePetition simplePetition, CompanyUser companyUser) {
        simplePetition.setCompany(companyUser);
        simplePetition.setIsValid(true);
    }
}
