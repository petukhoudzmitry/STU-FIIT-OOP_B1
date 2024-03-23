package com.petition.platform.services;

import com.petition.platform.models.SimpleUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsPrincipal implements UserDetails {

    private final SimpleUser simpleUser;

    public UserDetailsPrincipal(SimpleUser simpleUser){
        this.simpleUser = simpleUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(simpleUser.getRole()));
    }

    @Override
    public String getPassword() {
        return simpleUser.getPassword();
    }

    @Override
    public String getUsername() {
        return simpleUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return simpleUser.getEnabled();
    }
}
