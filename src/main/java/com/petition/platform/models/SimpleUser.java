package com.petition.platform.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class SimpleUser extends User {

    public SimpleUser(){}

    public SimpleUser(User user){
        super(user);
    }

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    protected List<SimplePetition> petitions;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "votes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "petition_id"))
    protected List<SimplePetition> signedPetitions;

    public List<SimplePetition> getPetitions() {
        return petitions;
    }

    public void setPetitions(List<SimplePetition> petitions) {
        this.petitions = petitions;
    }

    public List<SimplePetition> getSignedPetitions() {
        return signedPetitions;
    }

    public void setSignedPetitions(List<SimplePetition> signedPetitions) {
        this.signedPetitions = signedPetitions;
    }
}