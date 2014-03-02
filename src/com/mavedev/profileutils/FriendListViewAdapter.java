package com.mavedev.profileutils;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;

public class FriendListViewAdapter extends BaseAdapter {

		List<GraphUser> friends;
		private LayoutInflater inflater;
	
		public FriendListViewAdapter(Activity activity, List<GraphUser> friends) {
			this.friends = friends;
			inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

		View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.row_layout, null);
 
        final ListView list = (ListView) parent;
        
        GraphUser user = friends.get(position);
		
		TextView name = (TextView)vi.findViewById(R.id.name); // name
        TextView location = (TextView)vi.findViewById(R.id.location);
        ProfilePictureView profilePic=(ProfilePictureView)vi.findViewById(R.id.profile_pic); // thumb image
        
        CheckBox checkBox = (CheckBox) vi.findViewById(R.id.selected);
        checkBox.setTag(position);
        checkBox.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CheckBox checkBox = (CheckBox) v;
				if(checkBox.isChecked()){
					list.setItemChecked((Integer) checkBox.getTag(), true);
				}else{
					list.setItemChecked((Integer) checkBox.getTag(), false);
				}
			}
		});
        
        //name
        name.setText(user.getName());
        
        //location
        if(user.getLocation()!=null){
        	location.setText(user.getLocation().getCity());
        }

        //profile pic
        profilePic.setProfileId(user.getId());
        
        
        
        return vi;
	}

	public int getSelectItemsCount() {
		return friends.size();
	}


}
