package com.mavedev.profileutils.contacts.stats;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.TextView;

import com.mavedev.profileutils.R;
import com.mavedev.profileutils.contacts.export.Friend;
import com.mavedev.profileutils.contacts.export.FriendListService;
import com.mavedev.profileutils.contacts.export.FriendsCallback;

import java.util.List;

/**
 * Created by maverick on 26/4/14.
 */
public class FriendsStats extends Activity {

    private TextView noOFMaleFriendsText;
    private TextView noOFFemaleFriendsText;
    private TextView maleToFemaleRatioText;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_stats);

        getResources().getDrawable(R.drawable.ic_action_next_item).setColorFilter(android.graphics.Color.rgb(0, 129, 196),
                PorterDuff.Mode.MULTIPLY);
        
        noOFMaleFriendsText = (TextView) findViewById(R.id.noOfMaleFriends);
        noOFFemaleFriendsText = (TextView) findViewById(R.id.noOfFemaleFriends);
        maleToFemaleRatioText = (TextView) findViewById(R.id.maleToFemaleRatio);

        FriendListService.getFriendsList(new FriendsCallback() {
            @Override
            public void onCompleted(List<Friend> friends) {
                Friends friendsList = new Friends(friends);
                int maleFriends = friendsList.getNumberOfFriends("MALE");
                int totalFriends = friendsList.getTotalFriends();
                int femaleFriends = friendsList.getNumberOfFriends("FEMALE");

                noOFMaleFriendsText.setText(String.format("Male Friends: %s/%s (%s%%)",
                        maleFriends,
                        totalFriends,
                        maleFriends/(float)totalFriends*100));

                noOFFemaleFriendsText.setText(String.format("Female Friends: %s/%s (%s%%)",
                        femaleFriends,
                        totalFriends,
                        femaleFriends/(float)totalFriends*100));

                maleToFemaleRatioText.setText(String.format("Male to Female Ratio: %s",
                        maleFriends/(float)femaleFriends));
            }
        });

    }


}