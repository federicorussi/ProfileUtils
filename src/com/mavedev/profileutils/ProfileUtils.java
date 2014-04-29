package com.mavedev.profileutils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.Builder;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;
import com.mavedev.profileutils.contacts.export.ExportFriendsActivity;
import com.mavedev.profileutils.contacts.export.Friend;
import com.mavedev.profileutils.contacts.export.FriendListService;
import com.mavedev.profileutils.contacts.export.FriendsCallback;
import com.mavedev.profileutils.contacts.stats.FriendsStats;

public class ProfileUtils extends Activity {

    private static final List<String> PERMISSIONS = Arrays.asList(
            "email",
            "friends_notes",
            "friends_relationship_details",
            "friends_about_me",
            "user_checkins");

	private StatusCallback statusCallback= new StatusCallBack();
    ProfilePictureView profilePictureView;
    TextView exportContactsMenuIcon;
    TextView friendsStatsMenuIcon;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_utils);
		profilePictureView = (ProfilePictureView) findViewById(R.id.user_profile_pic);

        setMenuIconColor();

        exportContactsMenuIcon = (TextView) findViewById(R.id.export_contacts_icon);
        exportContactsMenuIcon.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onClickGetContactsList();
			}
		});

        friendsStatsMenuIcon = (TextView) findViewById(R.id.friends_stats_icon);
        friendsStatsMenuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickShowFriendsStatsView();
            }
        });


		if(ensureOpenSession()){
			getUserName(Session.getActiveSession());
		}

	}

    private void onClickShowFriendsStatsView() {
        if (ensureOpenSession()) {
            Intent intent = new Intent(this, FriendsStats.class);
            startActivityForResult(intent, 1);
        }
    }

    private void setMenuIconColor() {
        getResources().getDrawable(R.drawable.ic_action_user).setColorFilter(android.graphics.Color.rgb(0, 129, 196),
                PorterDuff.Mode.MULTIPLY);

        getResources().getDrawable(R.drawable.ic_action_data_usage).setColorFilter(android.graphics.Color.rgb(0, 129, 196),
                PorterDuff.Mode.MULTIPLY);
    }


    protected void onClickGetContactsList() {
		if (ensureOpenSession()) {
            final Intent intent = new Intent(this, ExportFriendsActivity.class);
            FriendListService.getFriendsList(new FriendsCallback() {
                @Override
                public void onCompleted(List<Friend> friends) {
                    intent.putExtra("friends", (Serializable) friends);
                    startActivityForResult(intent, 1);
                }
            });

        }
	}

	private boolean ensureOpenSession() {
		Session session = Session.getActiveSession();
		if (session == null
				|| session.isClosed()) {
			session = new Builder(this).build();
		}
		
		if(!session.isClosed() && !session.isOpened()){
	            Session.setActiveSession(session);
	            session.openForRead(new Session.OpenRequest(this)
				.setPermissions(PERMISSIONS)
				.setCallback(statusCallback));
	        return false;
		}
	
		/*Session.NewPermissionsRequest newPermissionsRequest = new Session
			      .NewPermissionsRequest(this, Arrays.asList("user_checkins"));
			    session.requestNewReadPermissions(newPermissionsRequest);*/
			    
		if(session.isOpened()){
			System.out.println(Session.getActiveSession().getPermissions());
			return true;
		}
		return false;

	}

	protected void onSessionStateChanged(Session session, SessionState state,
			Exception exception) {
		if (session.isOpened()) {
			getUserName(session);
		}
	}

	private void getUserName(Session session) {

		// make request to the /me API
		Request.newMeRequest(session, new Request.GraphUserCallback() {

			// callback after Graph API response with user object
			@Override
			public void onCompleted(GraphUser user, Response response) {
				if (user != null) {
					TextView welcome = (TextView) findViewById(R.id.username);
					welcome.setText("Welcome " + user.getName() + "!");
					profilePictureView.setProfileId(user.getId());
				}
			}
		}).executeAsync();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile_utils, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode,
                resultCode, data);
	}

	private final class StatusCallBack implements Session.StatusCallback {
		// callback when session changes state
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			onSessionStateChanged(session, state, exception);
		}
	}

}
