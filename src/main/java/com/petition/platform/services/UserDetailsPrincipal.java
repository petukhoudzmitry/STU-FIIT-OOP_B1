package com.petition.platform.services;

import com.petition.platform.models.SuperUser;
import com.petition.platform.models.User;
import com.petition.platform.roles.Roles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsPrincipal implements UserDetails {

    private final User user;

    public UserDetailsPrincipal(User user){
        this.user = user;
    }

    public Roles getRole(){
        return user.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(user.getRole().getName()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
        return user.getEnabled();
    }

    public Long getId(){
        return user.getId();
    }

    public boolean getIsRoot() {
        return user instanceof SuperUser ? ((SuperUser) user).getRoot() : false;
    }
}
