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

    public List<SimplePetition> getPetitions() {
        return petitions;
    }

    public void setPetitions(List<SimplePetition> petitions) {
        this.petitions = petitions;
    }
}