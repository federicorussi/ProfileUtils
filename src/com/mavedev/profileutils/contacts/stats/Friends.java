package com.mavedev.profileutils.contacts.stats;

import com.facebook.model.GraphUser;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.mavedev.profileutils.contacts.export.ExtendedGraphUser;

import java.util.List;

/**
 * Created by maverick on 26/4/14.
 */
public class Friends {

    private List<GraphUser> friends;

    public Friends(List<GraphUser> friends) {
        this.friends = friends;
    }

    public int getNumberOfFriends(String gender) {

        final String filtergender;
        if (gender.equals("MALE")) {
            filtergender = "male";
        } else {
            filtergender = "female";
        }


        return Collections2.filter(friends, new Predicate<GraphUser>() {
            @Override
            public boolean apply(GraphUser graphUser) {
                ExtendedGraphUser extUser = new ExtendedGraphUser(graphUser);
                return extUser.getGender().equalsIgnoreCase(filtergender);
            }
        }).size();
    }
}
