package com.petition.platform.models;

import com.petition.platform.roles.Roles;
import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
public class User implements Serializable {

    public User() {}

    public User(User user){
        this.id = user.id;
        this.username = user.username;
        this.email = user.email;
        this.password = user.password;
        this.enabled = user.enabled;
        this.createdAt = user.createdAt;
        this.updatedAt = user.updatedAt;
        this.role = user.role;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    protected Long id;

    @Column(name = "name", length = 50, nullable = false)
    protected String username;

    @Column(name = "email", unique = true, length = 50, nullable = false)
    protected String email;

    @Column(name = "password", length = 500, nullable = false)
    protected String password;

    @Column(name = "enabled", length = 5, nullable = false)
    protected Boolean enabled;

    @Column(name = "createdAt", length = 25, nullable = false)
    protected LocalDateTime createdAt;

    @Column(name = "updateAt", length = 25, nullable = false)
    protected LocalDateTime updatedAt;

    @Column(name = "role", length = 15, nullable = false)
    protected Roles role;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }


    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    @PrePersist
    protected void onCreate(){
        updatedAt = createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString(){
        return id + " " + username + " " + email;
    }
}
