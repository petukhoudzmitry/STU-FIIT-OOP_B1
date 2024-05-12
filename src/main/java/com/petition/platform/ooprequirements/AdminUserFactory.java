package com.petition.platform.ooprequirements;

import com.petition.platform.models.AdminUser;

public class AdminUserFactory implements UserFactory<AdminUser>{
    @Override
    public AdminUser createUser() {
        return new AdminUser();
    }
}
