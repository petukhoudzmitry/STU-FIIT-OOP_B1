package com.petition.platform.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class CompanyUser extends User {

    public CompanyUser(){}

    public CompanyUser(User user){
        super(user);
    }

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    protected List<SimplePetition> petitions;

    public List<SimplePetition> getPetitions() {
        return petitions;
    }

    public void setPetitions(List<SimplePetition> petitions) {
        this.petitions = petitions;
    }
}
