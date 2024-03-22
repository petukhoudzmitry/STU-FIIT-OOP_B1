package com.petition.platform.services;

import com.petition.platform.models.User;
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

    public boolean addUser(User user) throws NullPointerException {
        final String role = user.getRole();
        return switch(role){
            case "ROLE_USER" -> addSimpleUser(user);
            case "ROLE_ADMIN" -> addAdminUser(user);
            case "ROLE_SUPER" -> addSuperUser(user);
            default -> false;
        };
    }

    public boolean addSimpleUser(User user) throws NullPointerException {

        if(userRepository.findByEmail((user.getEmail())).isEmpty()){
            setupDefaultUser(user);
            userRepository.save(user);

            return true;
        }

        return false;
    }

    public boolean addAdminUser(User user) throws NullPointerException {

        if(userRepository.findByEmail(user.getEmail()).isEmpty()){
            setupDefaultUser(user);
            user.setRole("admin");
            userRepository.save(user);

            return true;
        }

        return false;
    }

    public boolean addSuperUser(User user) throws NullPointerException {
        if(userRepository.findByEmail(user.getEmail()).isEmpty()) {
            setupDefaultUser(user);
            user.setRole("super");
            userRepository.save(user);

            return true;
        }

        return false;
    }

    private void setupDefaultUser(User user) throws NullPointerException {
        user.setRole("user");
        user.setEnabled(true);
        user.setSigned_petitions_id(new int[]{});
        user.setFollowing_companies_id(new int[]{});
    }
}
