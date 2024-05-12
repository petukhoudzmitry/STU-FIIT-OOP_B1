package com.petition.platform.models;

import com.petition.platform.roles.Roles;
import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents a user in the system.
 * This class is mapped to the database and serves as a base class for specific user types.
 */
@MappedSuperclass
public class User implements Serializable {

    /**
     * Default constructor for the User class.
     */
    public User() {}

    /**
     * Constructor for User class that initializes it with properties of another User object.
     *
     * @param user Another User object whose properties are used to initialize the new User.
     */
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

    /**
     * Unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    protected Long id;

    /**
     * User's username.
     */
    @Column(name = "name", length = 50, nullable = false)
    protected String username;

    /**
     * User's email address.
     */
    @Column(name = "email", unique = true, length = 50, nullable = false)
    protected String email;

    /**
     * User's password (encrypted).
     */
    @Column(name = "password", length = 500, nullable = false)
    protected String password;

    /**
     * Indicates whether the user account is enabled or disabled.
     */
    @Column(name = "enabled", length = 5, nullable = false)
    protected Boolean enabled;

    /**
     * Timestamp when the user account was created.
     */
    @Column(name = "createdAt", length = 25, nullable = false)
    protected LocalDateTime createdAt;

    /**
     * Timestamp when the user account was last updated.
     */
    @Column(name = "updateAt", length = 25, nullable = false)
    protected LocalDateTime updatedAt;

    /**
     * Role of the user in the system.
     */
    @Column(name = "role", length = 15, nullable = false)
    protected Roles role;

    /**
     * Retrieves the unique identifier of the user.
     *
     * @return The user's unique identifier.
     */
    public Long getId() {
        return id;
    }

    /**
     * Retrieves the username of the user.
     *
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Retrieves the email address of the user.
     *
     * @return The email address of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Retrieves the encrypted password of the user.
     *
     * @return The encrypted password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Retrieves the status of the user account (enabled or disabled).
     *
     * @return True if the user account is enabled, false otherwise.
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * Sets the unique identifier of the user.
     *
     * @param id The unique identifier to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Sets the username of the user.
     *
     * @param name The username to set.
     */
    public void setUsername(String name) {
        this.username = name;
    }

    /**
     * Sets the email address of the user.
     *
     * @param email The email address to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the password of the user. Encrypts the password using BCryptPasswordEncoder before setting.
     *
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    /**
     * Sets the status of the user account (enabled or disabled).
     *
     * @param enabled True to enable the user account, false to disable.
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Retrieves the timestamp when the user account was created.
     *
     * @return The timestamp when the user account was created.
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the timestamp when the user account was created.
     *
     * @param createdAt The timestamp when the user account was created.
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Retrieves the timestamp when the user account was last updated.
     *
     * @return The timestamp when the user account was last updated.
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the timestamp when the user account was last updated.
     *
     * @param updatedAt The timestamp when the user account was last updated.
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Retrieves the role of the user in the system.
     *
     * @return The role of the user.
     */
    public Roles getRole() {
        return role;
    }

    /**
     * Sets the role of the user in the system.
     *
     * @param role The role to set for the user.
     */
    public void setRole(Roles role) {
        this.role = role;
    }

    /**
     * Sets the createdAt and updatedAt fields to the current timestamp before persisting a new user entity.
     */
    @PrePersist
    protected void onCreate(){
        updatedAt = createdAt = LocalDateTime.now();
    }

    /**
     * Updates the updatedAt field to the current timestamp before updating an existing user entity.
     */
    @PreUpdate
    protected void onUpdate(){
        updatedAt = LocalDateTime.now();
    }
}