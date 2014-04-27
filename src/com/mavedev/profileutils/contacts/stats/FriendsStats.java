package com.mavedev.profileutils.contacts.stats;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.model.GraphUser;
import com.mavedev.profileutils.R;
import com.mavedev.profileutils.contacts.export.FriendListService;
import com.mavedev.profileutils.contacts.export.FriendsCallback;

import java.util.List;

/**
 * Created by maverick on 26/4/14.
 */
public class FriendsStats extends Activity {

    TextView noOFMaleFriendsText;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_stats);
        noOFMaleFriendsText = (TextView) findViewById(R.id.noOfMaleFriends);

        FriendListService.getFriendsList(new FriendsCallback() {
            @Override
            public void onCompleted(List<GraphUser> friends) {
                noOFMaleFriendsText.setText("No of Male Friends: "+new Friends(friends).getNumberOfFriends("MALE") + "/"+new Friends(friends).getTotalFriends());
                System.out.println(new Friends(friends).getNumberOfFriends("MALE"));
            }
        });

    }


}