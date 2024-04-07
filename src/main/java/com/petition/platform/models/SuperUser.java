package com.petition.platform.models;

import jakarta.persistence.Entity;

@Entity
public class SuperUser extends User {

    public SuperUser(){}

    public SuperUser(User user){
        super(user);
    }

}
