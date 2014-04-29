package com.mavedev.profileutils.contacts.export;

import android.os.Bundle;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maverick on 26/4/14.
 */
public class FriendListService {

    private static final String USER_FIELDS = "id, " +
            "name, " +
            "location, " +
            "birthday, " +
            "gender, " +
            "email, " +
            "hometown, " +
            "link, " +
            "relationship_status, " +
            "about, " +
            "bio";

    private static List<Friend> friends;
    public static void getFriendsList(final FriendsCallback callback){
        if(FriendListService.friends == null){
            Bundle params = new Bundle();
            params.putString("fields", USER_FIELDS);
            Session session = Session.getActiveSession();
            Request newMyFriendsRequest = Request.newMyFriendsRequest(session, new Request.GraphUserListCallback() {

                @Override
                public void onCompleted(List<GraphUser> users, Response response) {
                    FriendListService.setFriends(users);
                    callback.onCompleted(FriendListService.friends);
                }
            });
            newMyFriendsRequest.setParameters(params);
            newMyFriendsRequest.executeAsync();
        }else{
            callback.onCompleted(FriendListService.friends);
        }
    }

    public static void setFriends(List<GraphUser> users){
        List<Friend> friends = new ArrayList<Friend>();
        for (GraphUser user: users){
            friends.add(new Friend(user));
        }
        FriendListService.friends = friends;
    }
}
