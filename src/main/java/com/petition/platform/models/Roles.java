package com.petition.platform.models;

import java.util.Arrays;
import java.util.HashSet;

public enum Roles {
    USER ("USER"),
    ADMIN ("ADMIN"),
    SUPER ("SUPER");

    private final String name;

    Roles(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public static HashSet<String> getRoles(){
        return new HashSet<>(Arrays.stream(Roles.values()).map(Roles::getName).toList());
    }

    public static Roles getRole(String role){
        for(Roles roles : values()){
            if(roles.getName().equals(role)){
                return roles;
            }
        }

        return null;
    }
}
