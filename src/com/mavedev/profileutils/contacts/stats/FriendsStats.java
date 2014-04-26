package com.mavedev.profileutils.contacts.stats;

import android.app.Activity;
import android.os.Bundle;

import com.facebook.model.GraphUser;
import com.mavedev.profileutils.contacts.export.FriendListService;

import java.util.List;

/**
 * Created by maverick on 26/4/14.
 */
public class FriendsStats extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<GraphUser> friendsList = FriendListService.getFriendsList();
        System.out.println(new Friends(friendsList).getNumberOfFriends("MALE"));
    }


}