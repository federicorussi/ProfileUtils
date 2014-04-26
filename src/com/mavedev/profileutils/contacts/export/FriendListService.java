package com.mavedev.profileutils.contacts.export;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;

import java.util.List;

/**
 * Created by maverick on 26/4/14.
 */
public class FriendListService {

    public static List<GraphUser> friends;
    public static List<GraphUser> getFriendsList(){
        return FriendListService.friends;
    }
}
