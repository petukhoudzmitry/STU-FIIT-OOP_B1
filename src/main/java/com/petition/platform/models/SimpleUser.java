package com.petition.platform.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class SimpleUser extends User {
    @OneToMany(mappedBy = "creator")
    protected List<SimplePetition> signed_simple_petitions;

    public List<SimplePetition> getSigned_simple_petitions() {
        return signed_simple_petitions;
    }

    public void setSigned_simple_petitions(List<SimplePetition> signed_simple_petitions) {
        this.signed_simple_petitions = signed_simple_petitions;
    }
}