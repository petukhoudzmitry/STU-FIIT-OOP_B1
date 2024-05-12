package com.petition.platform.models;

import jakarta.persistence.Entity;

/**
 * Represents a simple petition in the system.
 * Inherits properties and methods from the AbstractPetition class.
 */
@Entity
public class SimplePetition extends AbstractPetition{
    /**
     * Default constructor for the SimplePetition class.
     */
    public SimplePetition() {}

    /**
     * Overrides the equals method to compare SimplePetition objects based on their IDs.
     *
     * @param obj Object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof SimplePetition){
            return ((SimplePetition) obj).id.equals(this.id);
        }
        return false;
    }
}