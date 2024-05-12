package com.petition.platform.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a superuser in the system with extended privileges.
 * Inherits properties and methods from the User class.
 */
@Entity
public class SuperUser extends User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor for SuperUser class.
     */
    public SuperUser(){}

    /**
     * Constructor for SuperUser class that initializes it with properties of another User object.
     *
     * @param user Another User object whose properties are used to initialize the SuperUser.
     */
    public SuperUser(User user){
        super(user);
    }

    /**
     * Indicates whether the superuser has root privileges.
     */
    @Column(name = "isRoot", nullable = false)
    private Boolean isRoot = false;

    /**
     * Retrieves whether the superuser has root privileges.
     *
     * @return True if the superuser has root privileges, false otherwise.
     */
    public Boolean getRoot() {
        return isRoot;
    }

    /**
     * Sets whether the superuser has root privileges.
     *
     * @param root True to grant root privileges to the superuser, false otherwise.
     */
    public void setRoot(Boolean root) {
        isRoot = root;
    }

    /**
     * Overrides the equals method to compare SuperUser objects based on their properties.
     *
     * @param o Object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if(o instanceof SuperUser superUser){
            return getId().equals(superUser.getId()) && getEmail().equals(superUser.getEmail()) && getPassword().equals(superUser.getPassword()) && getRole().equals(superUser.getRole());
        }
        return false;
    }
}