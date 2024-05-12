package com.petition.platform.ooprequirements;

import com.petition.platform.models.CompanyUser;

/**
 * Factory class for creating instances of CompanyUser objects.
 */
public class CompanyUserFactory implements UserFactory<CompanyUser>{
    /**
     * Default constructor for CompanyUserFactory.
     */
    public CompanyUserFactory() {}

    /**
     * Creates a new instance of CompanyUser.
     *
     * @return A new instance of CompanyUser.
     */
    @Override
    public CompanyUser createUser() {
        return new CompanyUser();
    }
}