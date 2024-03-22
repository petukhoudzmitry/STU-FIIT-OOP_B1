package com.petition.platform.models;

import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "password", nullable = false, length = 500)
    private String password;

    @Column(name = "signed_petitions_id", nullable = false)
    private int[] signed_petitions_id;

    @Column(name = "following_companies_id", nullable = false)
    private int[] following_companies_id;

    @Column(name = "role", nullable = false, length = 15)
    private String role;

    @Column(name = "enabled")
    private Boolean enabled;

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

    public int[] getSigned_petitions_id() {
        return signed_petitions_id;
    }

    public int[] getFollowing_companies_id() {
        return following_companies_id;
    }

    public String getRole() {
        return role;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    public void setSigned_petitions_id(int[] signedPetitionsId) {
        this.signed_petitions_id = signedPetitionsId;
    }

    public void setFollowing_companies_id(int[] followingCompaniesId) {
        this.following_companies_id = followingCompaniesId;
    }

    public void setRole(String role) {
        this.role = "ROLE_" + role.toUpperCase();
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

}
