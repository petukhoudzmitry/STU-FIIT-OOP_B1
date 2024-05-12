package com.petition.platform.models;

import jakarta.persistence.*;

import java.util.List;

/**
 * Represents a simple user in the system.
 * Inherits properties and methods from the User class.
 */
@Entity
public class SimpleUser extends User {

    /**
     * Default constructor for SimpleUser class.
     */
    public SimpleUser(){}

    /**
     * Constructor for SimpleUser class that initializes it with properties of another User object.
     *
     * @param user Another User object whose properties are used to initialize the SimpleUser.
     */
    public SimpleUser(User user){
        super(user);
    }

    /**
     * List of petitions created by the simple user.
     */
    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    protected List<SimplePetition> petitions;

    /**
     * List of petitions signed by the simple user.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "votes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "petition_id"))
    protected List<SimplePetition> signedPetitions;

    /**
     * Retrieves the list of petitions created by the simple user.
     *
     * @return List of petitions created by the simple user.
     */
    public List<SimplePetition> getPetitions() {
        return petitions;
    }

    /**
     * Sets the list of petitions created by the simple user.
     *
     * @param petitions List of petitions created by the simple user.
     */
    public void setPetitions(List<SimplePetition> petitions) {
        this.petitions = petitions;
    }

    /**
     * Retrieves the list of petitions signed by the simple user.
     *
     * @return List of petitions signed by the simple user.
     */
    public List<SimplePetition> getSignedPetitions() {
        return signedPetitions;
    }

    /**
     * Sets the list of petitions signed by the simple user.
     *
     * @param signedPetitions List of petitions signed by the simple user.
     */
    public void setSignedPetitions(List<SimplePetition> signedPetitions) {
        this.signedPetitions = signedPetitions;
    }
}