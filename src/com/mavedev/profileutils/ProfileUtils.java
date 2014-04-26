package com.mavedev.profileutils;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.Builder;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;

public class ProfileUtils extends Activity {

    private static final List<String> PERMISSIONS = Arrays.asList(
            "email",
            "friends_notes",
            "friends_relationship_details",
            "friends_about_me",
            "user_checkins");

	private StatusCallback statusCallback= new StatusCallBack(); 
	private static final int SELECT_FRIENDS_ACTIVITY = 1;
	ProfilePictureView profilePictureView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_utils);
		profilePictureView = (ProfilePictureView) findViewById(R.id.user_profile_pic);
		Button button = (Button) findViewById(R.id.export_contacts_view);
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onClickGetContactsList();
			}
		});
		
		if(ensureOpenSession()){
			getUserName(Session.getActiveSession());
		}

	}

	
	protected void onClickGetContactsList() {
		if (ensureOpenSession()) {
            Intent intent = new Intent(this, SelectFriendsActivity.class);
            startActivityForResult(intent, SELECT_FRIENDS_ACTIVITY);
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
