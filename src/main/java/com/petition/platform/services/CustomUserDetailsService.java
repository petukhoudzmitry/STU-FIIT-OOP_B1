package com.petition.platform.services;

import com.petition.platform.models.*;
import com.petition.platform.repositories.AdminUserRepository;
import com.petition.platform.repositories.CompanyUserRepository;
import com.petition.platform.repositories.SimpleUserRepository;
import com.petition.platform.repositories.SuperUserRepository;
import com.petition.platform.roles.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private SimpleUserRepository simpleUserSimpleUserRepository;
    @Autowired
    private SuperUserRepository superUserRepository;
    @Autowired
    private AdminUserRepository adminUserUserRepository;
    @Autowired
    private CompanyUserRepository companyUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<SimpleUser> simpleUser = simpleUserSimpleUserRepository.findByEmail(email);

        if(simpleUser.isEmpty()){
            Optional<CompanyUser> companyUser = companyUserRepository.findByEmail(email);
            if(companyUser.isEmpty()) {
                Optional<AdminUser> adminUser = adminUserUserRepository.findByEmail(email);
                if (adminUser.isEmpty()) {
                    Optional<SuperUser> superUser = superUserRepository.findByEmail(email);
                    if (superUser.isEmpty()) {
                        throw new UsernameNotFoundException("Couldn't find user with the email: " + email);
                    } else {
                        return new UserDetailsPrincipal(superUser.get());
                    }
                } else {
                    return new UserDetailsPrincipal(adminUser.get());
                }
            }else{
                return new UserDetailsPrincipal(companyUser.get());
            }
        }

        return new UserDetailsPrincipal(simpleUser.get());
    }

    public boolean addUser(User user) throws NullPointerException {
        return switch(user.getRole()){
            case Roles.USER -> addSimpleUser(user);
            case Roles.COMPANY -> addCompanyUser(user);
            case Roles.ADMIN -> addAdminUser(user);
            case Roles.SUPER -> addSuperUser(user);
        };
    }

    public boolean addCompanyUser(User user) throws NullPointerException {
        if(companyUserRepository.findByEmail((user.getEmail())).isEmpty()){
            setupDefaultUser(user);
            user.setRole(Roles.COMPANY);
            companyUserRepository.save(new CompanyUser(user));

            return true;
        }

        return false;
    }

    public boolean addSimpleUser(User user) throws NullPointerException {

        if(simpleUserSimpleUserRepository.findByEmail((user.getEmail())).isEmpty()){
            setupDefaultUser(user);
            simpleUserSimpleUserRepository.save(new SimpleUser(user));
            return true;
        }

        return false;
    }

    public boolean addAdminUser(User user) throws NullPointerException {
        if(adminUserUserRepository.findByEmail(user.getEmail()).isEmpty()){
            setupDefaultUser(user);
            user.setRole(Roles.ADMIN);
            adminUserUserRepository.save(new AdminUser(user));

            return true;
        }

        return false;
    }

    public boolean addSuperUser(User user) throws NullPointerException {
        if(superUserRepository.findByEmail(user.getEmail()).isEmpty()) {
            SuperUser superUser = new SuperUser(user);
            if(superUserRepository.count() == 1L && superUserRepository.findById(1L).isPresent()){
                superUser.setRoot(true);
                SuperUser superUser1 = superUserRepository.findById(1L).get();
                superUser1.setRoot(false);
                superUserRepository.save(superUser1);
            }
            setupDefaultUser(superUser);
            superUser.setRole(Roles.SUPER);
            superUserRepository.save(superUser);
            return true;
        }

        return false;
    }

    private void setupDefaultUser(User user) throws NullPointerException {
        user.setRole(Roles.USER);
        user.setEnabled(true);
    }
}
