package com.petition.platform.services;

import com.petition.platform.models.*;
import com.petition.platform.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new UserDetailsPrincipal(userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email)));
    }

    public boolean addUser(SimpleUser simpleUser) throws NullPointerException {
        final String role = simpleUser.getRole();
        System.out.println(role.equals(Roles.USER.getName()));
        Roles roles = Roles.getRole(role.toUpperCase());
        assert roles != null;
        return switch(roles){
            case Roles.USER -> addSimpleUser(simpleUser);
            case Roles.ADMIN -> addAdminUser(simpleUser);
            case Roles.SUPER -> addSuperUser(simpleUser);
        };
    }

    public boolean addSimpleUser(SimpleUser simpleUser) throws NullPointerException {

        if(userRepository.findByEmail((simpleUser.getEmail())).isEmpty()){
            setupDefaultUser(simpleUser);
            userRepository.save(simpleUser);

            return true;
        }

        return false;
    }

    public boolean addAdminUser(SimpleUser simpleUser) throws NullPointerException {

        if(userRepository.findByEmail(simpleUser.getEmail()).isEmpty()){
            setupDefaultUser(simpleUser);
            simpleUser.setRole("admin");
            userRepository.save(simpleUser);

            return true;
        }

        return false;
    }

    public boolean addSuperUser(SimpleUser simpleUser) throws NullPointerException {
        if(userRepository.findByEmail(simpleUser.getEmail()).isEmpty()) {
            setupDefaultUser(simpleUser);
            simpleUser.setRole("super");
            userRepository.save(simpleUser);

            return true;
        }

        return false;
    }

    private void setupDefaultUser(SimpleUser simpleUser) throws NullPointerException {
        simpleUser.setRole("user");
        simpleUser.setEnabled(true);
    }
}
