package com.petition.platform.services;

import com.petition.platform.models.*;
import com.petition.platform.ooprequirements.Actions;
import com.petition.platform.ooprequirements.CreateUserActionListener;
import com.petition.platform.ooprequirements.EventManager;
import com.petition.platform.repositories.AdminUserRepository;
import com.petition.platform.repositories.CompanyUserRepository;
import com.petition.platform.repositories.SimpleUserRepository;
import com.petition.platform.repositories.SuperUserRepository;
import com.petition.platform.roles.Roles;
import com.petition.platform.utils.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;

/**
 * Service class for managing custom user-related operations.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    /**
     * Default constructor for CustomUserDetailsService.
     */
    public CustomUserDetailsService() {}

    @Autowired
    private SimpleUserRepository simpleUserSimpleUserRepository;
    @Autowired
    private SuperUserRepository superUserRepository;
    @Autowired
    private AdminUserRepository adminUserUserRepository;
    @Autowired
    private CompanyUserRepository companyUserRepository;

    private static final EventManager eventManager;
    static{
        eventManager = EventManager.getInstance();
        eventManager.subscribe(Actions.CREATE_USER, new CreateUserActionListener());
    }

    /**
     * Loads a user by email address.
     *
     * @param email the email address of the user.
     * @return the UserDetailsPrincipal associated with the email address.
     * @throws UsernameNotFoundException if the user with the specified email address is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final UserDetailsPrincipal[] userDetailsPrincipal = new UserDetailsPrincipal[1];

        simpleUserSimpleUserRepository.findByEmail(email).ifPresentOrElse(
                user -> userDetailsPrincipal[0] =  new UserDetailsPrincipal(user),
                () -> companyUserRepository.findByEmail(email).ifPresentOrElse(
                        user -> userDetailsPrincipal[0] = new UserDetailsPrincipal(user),
                        () -> adminUserUserRepository.findByEmail(email).ifPresentOrElse(
                                user -> userDetailsPrincipal[0] = new UserDetailsPrincipal(user),
                                () -> superUserRepository.findByEmail(email).ifPresentOrElse(
                                        user -> userDetailsPrincipal[0] = new UserDetailsPrincipal(user),
                                        () -> {throw new UsernameNotFoundException("Couldn't find user with the email: " + email);}
                                )
                        )
                )
        );
        return userDetailsPrincipal[0];
    }

    /**
     * Initializes the root superUser if none exists.
     */
    @Bean
    public void rootSuperUserPersist(){
        if(superUserRepository.count() == 0L){
            SuperUser superUser = new SuperUser();
            superUser.setUsername("super user");
            superUser.setEmail("super@user.com");
            superUser.setRoot(true);
            superUser.setEnabled(true);
            String password = PasswordGenerator.generatePassword(16);
            superUser.setPassword(password);
            superUser.setRole(Roles.SUPER);
            superUser.setCreatedAt(LocalDateTime.now());
            superUser.setCreatedAt(superUser.getCreatedAt());
            superUserRepository.save(superUser);

            System.out.println("Super user credentials: " + superUser.getEmail() + " " + password);

            File file = new File("credentials.txt");
            try(BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file)); ObjectOutputStream obs = new ObjectOutputStream(out)){
                obs.writeObject(superUser);
            }catch(IOException ex){
                System.out.println(ex.getMessage());
            }
        }else{
            File file = new File("credentials.txt");
            if(file.exists()){
                try(ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))){
                    SuperUser superUser = (SuperUser) ois.readObject();
                    superUserRepository.findByEmail(superUser.getEmail()).ifPresent(user -> {
                        if(user.equals(superUser)){superUserRepository.delete(user);}
                    });
                }catch(IOException | ClassNotFoundException ex){
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    /**
     * Adds a new user.
     *
     * @param user the user to add.
     * @return true if the user is successfully added, false otherwise.
     * @throws NullPointerException if the user is null.
     */
    public boolean addUser(User user) throws NullPointerException {
        try{
            loadUserByUsername(user.getEmail());
        }catch(UsernameNotFoundException ex){
            return switch(user.getRole()){
                case Roles.USER -> addSimpleUser(user);
                case Roles.COMPANY -> addCompanyUser(user);
                case Roles.ADMIN -> addAdminUser(user);
                case Roles.SUPER -> addSuperUser(user);
            };
        }
        return false;
    }

    /**
     * Adds a company user.
     *
     * @param user the company user to add.
     * @return true if the company user is successfully added, false otherwise.
     * @throws NullPointerException if the user is null.
     */
    public boolean addCompanyUser(User user) throws NullPointerException {
        if(companyUserRepository.findByEmail((user.getEmail())).isEmpty()){
            setupDefaultUser(user);
            user.setRole(Roles.COMPANY);
            companyUserRepository.save(new CompanyUser(user));

            return true;
        }
        return false;
    }

    /**
     * Adds a simple user.
     *
     * @param user the simple user to add.
     * @return true if the simple user is successfully added, false otherwise.
     * @throws NullPointerException if the user is null.
     */
    public boolean addSimpleUser(User user) throws NullPointerException {
        if(simpleUserSimpleUserRepository.findByEmail((user.getEmail())).isEmpty()){
            setupDefaultUser(user);
            simpleUserSimpleUserRepository.save(new SimpleUser(user));
            return true;
        }
        return false;
    }

    /**
     * Adds an admin user.
     *
     * @param user the admin user to add.
     * @return true if the admin user is successfully added, false otherwise.
     * @throws NullPointerException if the user is null.
     */
    public boolean addAdminUser(User user) throws NullPointerException {
        if(adminUserUserRepository.findByEmail(user.getEmail()).isEmpty()){
            setupDefaultUser(user);
            user.setRole(Roles.ADMIN);
            adminUserUserRepository.save(new AdminUser(user));

            return true;
        }
        return false;
    }

    /**
     * Adds a super user.
     *
     * @param user the superuser to add.
     * @return true if the superuser is successfully added, false otherwise.
     * @throws NullPointerException if the user is null.
     */
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

    /**
     * Sets up default values for a new user.
     *
     * @param user the user to set up.
     */
    private void setupDefaultUser(User user){
        eventManager.notify(Actions.CREATE_USER,
                SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken ?
                        user.getEmail() :
                        ((UserDetailsPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail()
                , user.getEmail());

        user.setRole(Roles.USER);
        user.setEnabled(true);
    }
}