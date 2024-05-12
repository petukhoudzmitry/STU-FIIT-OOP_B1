package com.petition.platform.roles;

public enum Roles {
    USER ("ROLE_USER"),
    COMPANY ("ROLE_COMPANY"),
    ADMIN ("ROLE_ADMIN"),
    SUPER ("ROLE_SUPER");

    private final String name;

    Roles(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public static Roles getRole(String role){
        for(Roles roles : values()){
            if(roles.getName().equals(role)){
                return roles;
            }
        }

        return USER;
    }
}
