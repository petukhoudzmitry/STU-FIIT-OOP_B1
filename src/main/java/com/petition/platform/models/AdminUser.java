package com.petition.platform.models;

import jakarta.persistence.Entity;

/**
 * Represents an admin user in the system.
 * Inherits properties and methods from the User class.
 */
@Entity
public class AdminUser extends User {
    /**
     * Default constructor for AdminUser class.
     */
    public AdminUser(){}

    /**
     * Constructor for AdminUser class that initializes it with properties of another User object.
     *
     * @param user Another User object whose properties are used to initialize the AdminUser.
     */
    public AdminUser(User user){
        super(user);
    }
}