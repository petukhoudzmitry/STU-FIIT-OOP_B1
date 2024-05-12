package com.petition.platform.services;

import com.petition.platform.models.AbstractPetition;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Implementation of UserDetails representing petition details.
 */
public class PetitionDetailsPrincipal implements UserDetails {

    /**
     * The abstract petition associated with the user details.
     */
    private final AbstractPetition petition;

    /**
     * Constructs a new PetitionDetailsPrincipal with the given petition.
     *
     * @param petition the petition to represent.
     */
    public PetitionDetailsPrincipal(AbstractPetition petition) {
        this.petition = petition;
    }

    /**
     * Returns an empty list since this class doesn't have any authorities.
     *
     * @return an empty list.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    /**
     * Returns an empty string since this class doesn't have a password.
     *
     * @return an empty string.
     */
    @Override
    public String getPassword() {
        return "";
    }

    /**
     * Returns the title of the petition as the username.
     *
     * @return the title of the petition.
     */
    @Override
    public String getUsername() {
        return petition.getTitle();
    }

    /**
     * Indicates whether the account is non-expired. Always returns true.
     *
     * @return always true.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the account is non-locked. Always returns true.
     *
     * @return always true.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the credentials are non-expired. Always returns true.
     *
     * @return always true.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the account is enabled. Always returns true.
     *
     * @return always true.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}