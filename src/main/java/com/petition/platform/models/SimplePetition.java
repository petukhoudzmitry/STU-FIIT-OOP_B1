package com.petition.platform.models;

import jakarta.persistence.Entity;

@Entity
public class SimplePetition extends AbstractPetition{


    @Override
    public boolean equals(Object obj) {
        if(obj instanceof SimplePetition){
            return ((SimplePetition) obj).id.equals(this.id);
        }

        return false;
    }
}
