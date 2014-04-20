package com.mavedev.profileutils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Request.GraphUserListCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;

import java.util.List;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SelectFriendsActivity extends Activity {

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
	
	private ListView friendsListView;
	private TextView noOfSelectedUsers;
	private CheckBox selectAllCheckBox;
	private FriendListViewAdapter adapter;
	int exportType;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_friends);
		friendsListView = (ListView) findViewById(R.id.friendsList);
		noOfSelectedUsers = (TextView)findViewById(R.id.noOfSelectedUsers);
		selectAllCheckBox = (CheckBox) findViewById(R.id.selectAllCheckBox);
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
		Bundle params = new Bundle();
		params.putString("fields", USER_FIELDS);
		Session session = Session.getActiveSession();
		Request newMyFriendsRequest = Request.newMyFriendsRequest(session, new GraphUserListCallback() {
			
			@Override
			public void onCompleted(List<GraphUser> users, Response response) {
				populateFriendsList(users);
			}
		});
		newMyFriendsRequest.setParameters(params);
		newMyFriendsRequest.executeAsync();
	}

	protected void populateFriendsList(final List<GraphUser> friends) {
			adapter = new FriendListViewAdapter(this, friends);
			friendsListView.setAdapter(adapter);
			friendsListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
			
			onFriendListItemClick();
			
			onClickSelectAll(adapter);
	}

	private void onFriendListItemClick() {
		friendsListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				ImageView checkMark = (ImageView) view.findViewById(R.id.checkMark);
				int visibility = checkMark.getVisibility()==View.VISIBLE?View.INVISIBLE:View.VISIBLE;
				checkMark.setVisibility(visibility);
				updateTotalCount();
				
			}

			
		});
	}

	private void onClickSelectAll(final FriendListViewAdapter adapter) {
		selectAllCheckBox.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CheckBox selectAll = (CheckBox) v;
					for(int position=0;position<adapter.getCount();position++){
						friendsListView.setItemChecked(position, selectAll.isChecked());
					}
					adapter.notifyDataSetChanged();
					updateTotalCount();
						
			}
		});
	}


	private void updateTotalCount() {
		noOfSelectedUsers.setText("("+getTotalSelectedFriendsCount()+")");
	}

	private int getTotalSelectedFriendsCount() {
		return friendsListView.getCheckedItemCount();
	}
	
	private void onClickExport(final boolean sendEmail) {

				CharSequence[] exportOptionsList ={"Text", "HTML"} ;
				AlertDialog.Builder selectExportOption = new AlertDialog.Builder(SelectFriendsActivity.this);
				selectExportOption.setTitle("Export friends: "+getTotalSelectedFriendsCount())
				.setSingleChoiceItems(exportOptionsList, 0, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						exportType = which;
					}
				})
				.setPositiveButton("Export", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
                        FriendsExporter exporter = ExportFactory.getExporter(exportType, adapter, getCheckedItemPositions(), SelectFriendsActivity.this, sendEmail);
						exporter.executeTask();
					}

				});
				selectExportOption.show();
	}

	private SparseBooleanArray getCheckedItemPositions() {
		return friendsListView.getCheckedItemPositions();
	}


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
          switch (item.getItemId()){
            case R.id.action_export:
                onClickExport(false);
                return true;
            case R.id.action_export_and_mail:
                onClickExport(true);
                return true;
            default:
                return true;
        }
    }

}
