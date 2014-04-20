package com.mavedev.profileutils;

import com.facebook.model.GraphUser;

public class ExtendedGraphUser {

    GraphUser user;
    private static final String GENDER = "gender";
    public static final String EMAIL = "email";
    public static final String LOCATION = "location";

    public ExtendedGraphUser(GraphUser user) {
        this.user = user;
    }

    public String getCity() {
        if (user.getLocation() != null) {
            return (String) user.getLocation().getProperty("name");
        } else {
            return null;
        }
    }

    public String getGender() {
        return user.getProperty(GENDER).toString();
    }
}
