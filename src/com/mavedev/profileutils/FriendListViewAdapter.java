package com.mavedev.profileutils;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;

public class FriendListViewAdapter extends BaseAdapter {

	List<GraphUser> friends;
	private LayoutInflater inflater;
	CheckBox selectAllCheckBox;
	
	public FriendListViewAdapter(Activity activity, List<GraphUser> friends) {
		this.friends = friends;
		selectAllCheckBox = (CheckBox) activity.findViewById(R.id.selectAllCheckBox);
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return friends.size();
	}

	@Override
	public Object getItem(int position) {
		return friends.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.row_layout, null);

		final ListView list = (ListView) parent;
		
		

		GraphUser user = friends.get(position);

		TextView name = (TextView) vi.findViewById(R.id.name); // name
		TextView location = (TextView) vi.findViewById(R.id.location);
		ProfilePictureView profilePic = (ProfilePictureView) vi
				.findViewById(R.id.profile_pic); // thumb image
		ImageView checkMark = (ImageView) vi.findViewById(R.id.checkMark);
		Drawable drawable = vi.getResources().getDrawable(
				R.drawable.ic_action_done);
		drawable.setColorFilter(android.graphics.Color.rgb(0, 129, 196),
				Mode.MULTIPLY);
		checkMark.setImageDrawable(drawable);
		checkMark.setVisibility(View.INVISIBLE);

		// name
		name.setText(user.getName());

		// location
		if (user.getLocation() != null) {
			location.setText(user.getLocation().getCity());
		}

		// profile pic
		profilePic.setProfileId(user.getId());

		//handle select all
		if(list.isItemChecked(position)){
			checkMark.setVisibility(View.VISIBLE);
		}else{
			checkMark.setVisibility(View.INVISIBLE);
		}
		return vi;
	}

	public int getSelectItemsCount() {
		return friends.size();
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}
}
