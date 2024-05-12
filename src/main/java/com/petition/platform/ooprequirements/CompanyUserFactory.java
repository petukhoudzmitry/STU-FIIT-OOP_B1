package com.petition.platform.ooprequirements;

import com.petition.platform.models.CompanyUser;

public class CompanyUserFactory implements UserFactory<CompanyUser>{
    @Override
    public CompanyUser createUser() {
        return new CompanyUser();
    }
}
