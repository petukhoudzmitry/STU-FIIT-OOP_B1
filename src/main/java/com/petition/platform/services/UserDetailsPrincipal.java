package com.petition.platform.services;

import com.petition.platform.models.SuperUser;
import com.petition.platform.models.User;
import com.petition.platform.roles.Roles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Implementation of UserDetails representing user details.
 */
public class UserDetailsPrincipal implements UserDetails {

    /**
     * The user associated with the user details.
     */
    private final User user;

    /**
     * Constructs a new UserDetailsPrincipal with the given user.
     *
     * @param user the user to represent.
     */
    public UserDetailsPrincipal(User user){
        this.user = user;
    }

    /**
     * Returns the role of the user.
     *
     * @return the role of the user.
     */
    public Roles getRole(){
        return user.getRole();
    }

    /**
     * Returns the email of the user.
     *
     * @return the email of the user.
     */
    public String getEmail(){
        return user == null ? null : user.getEmail();
    }

    /**
     * Returns the authorities granted to the user based on their role.
     *
     * @return a collection of authorities granted to the user.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(user.getRole().getName()));
    }

    /**
     * Returns the password of the user.
     *
     * @return the password of the user.
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Returns the username of the user.
     *
     * @return the username of the user.
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * Indicates whether the user's account has expired. Always returns true.
     *
     * @return always true.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user's account is locked. Always returns true.
     *
     * @return always true.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials have expired. Always returns true.
     *
     * @return always true.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled.
     *
     * @return true if the user is enabled, false otherwise.
     */
    @Override
    public boolean isEnabled() {
        return user.getEnabled();
    }

    /**
     * Returns the ID of the user.
     *
     * @return the ID of the user.
     */
    public Long getId(){
        return user.getId();
    }

    /**
     * Returns whether the user is a root user.
     *
     * @return true if the user is a root user, false otherwise.
     */
    public boolean getIsRoot() {
        return user instanceof SuperUser ? ((SuperUser) user).getRoot() : false;
    }
}