package com.petition.platform.roles;

/**
 * Enumeration representing different roles in the application.
 */
public enum Roles {
    /**
     * User role.
     */
    USER ("ROLE_USER"),

    /**
     * Company role.
     */
    COMPANY ("ROLE_COMPANY"),

    /**
     * Admin role.
     */
    ADMIN ("ROLE_ADMIN"),

    /**
     * Super user role.
     */
    SUPER ("ROLE_SUPER");

    /**
     * The name of the role.
     */
    private final String name;

    /**
     * Constructor to initialize a role with its name.
     *
     * @param name the name of the role.
     */
    Roles(String name){
        this.name = name;
    }

    /**
     * Gets the name of the role.
     *
     * @return the name of the role.
     */
    public String getName(){
        return name;
    }

    /**
     * Gets the role enum corresponding to a given role name.
     *
     * @param role the role name.
     * @return the corresponding role enum if found, otherwise USER.
     */
    public static Roles getRole(String role){
        for(Roles roles : values()){
            if(roles.getName().equals(role)){
                return roles;
            }
        }

        // Return USER role if the specified role is not found
        return USER;
    }
}