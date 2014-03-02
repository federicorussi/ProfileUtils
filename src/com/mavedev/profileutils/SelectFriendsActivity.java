package com.mavedev.profileutils;

import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Request.GraphUserListCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SelectFriendsActivity extends Activity {

	ListView friendsListView;
	TextView noOfSelectedUsers;
	Button exportButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_friends);
		friendsListView = (ListView) findViewById(R.id.friendsList);
		noOfSelectedUsers = (TextView)findViewById(R.id.noOfSelectedUsers);
		exportButton = (Button) findViewById(R.id.exportButton);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.select_friends, menu);
		return true;
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		//get friends
		Session session = Session.getActiveSession();
		Request.newMyFriendsRequest(session, new GraphUserListCallback() {
			
			@Override
			public void onCompleted(List<GraphUser> users, Response response) {
				populateFriendsList(users);
			}
		}).executeAsync();
	
		
		
	}

	protected void populateFriendsList(List<GraphUser> friends) {
		final FriendListViewAdapter adapter = new FriendListViewAdapter(this, friends);
			friendsListView.setAdapter(adapter);
			friendsListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
			
			friendsListView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
					
					ListView friendsList = (ListView) parent;

					CheckBox checkBox = (CheckBox) view.findViewById(R.id.selected);
					checkBox.setChecked(!checkBox.isChecked());
					noOfSelectedUsers.setText("("+friendsList.getCheckedItemCount()+")");
					
				}
			});
			
			exportButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					System.out.println(friendsListView.getCheckedItemPositions());
					
				}
			});
	}	
}
