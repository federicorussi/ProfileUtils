package com.mavedev.profileutils.contacts.export;

import com.facebook.model.GraphUser;

import java.util.List;

/**
 * Created by maverick on 27/4/14.
 */
public interface FriendsCallback {

    public void onCompleted(List<GraphUser> friends);
}
