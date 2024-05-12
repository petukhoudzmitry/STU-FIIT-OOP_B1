package com.petition.platform.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.util.List;

/**
 * Represents a company user in the system.
 * Inherits properties and methods from the User class.
 */
@Entity
public class CompanyUser extends User {

    /**
     * Default constructor for CompanyUser class.
     */
    public CompanyUser(){}

    /**
     * Constructor for CompanyUser class that initializes it with properties of another User object.
     *
     * @param user Another User object whose properties are used to initialize the CompanyUser.
     */
    public CompanyUser(User user){
        super(user);
    }

    /**
     * List of petitions created by the company user.
     */
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    protected List<SimplePetition> petitions;

    /**
     * Retrieves the list of petitions created by the company user.
     *
     * @return List of petitions created by the company user.
     */
    public List<SimplePetition> getPetitions() {
        return petitions;
    }

    /**
     * Sets the list of petitions created by the company user.
     *
     * @param petitions List of petitions created by the company user.
     */
    public void setPetitions(List<SimplePetition> petitions) {
        this.petitions = petitions;
    }
}