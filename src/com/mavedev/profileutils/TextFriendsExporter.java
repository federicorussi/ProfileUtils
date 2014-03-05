package com.mavedev.profileutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.util.SparseBooleanArray;

import com.facebook.model.GraphUser;

public class TextFriendsExporter implements FriendsExporter {


	private static final String FILENAME = "exported_contacts.txt";
	private FriendListViewAdapter adapter;
	private SparseBooleanArray checkedItemPositions;
	private Context context;

	public TextFriendsExporter(FriendListViewAdapter adapter, SparseBooleanArray checkedItemPositions, Context context) {
		this.adapter = adapter;
		this.checkedItemPositions = checkedItemPositions;
		this.context = context;
	}

	@Override
	public void export() {
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < checkedItemPositions.size(); i++) {
			GraphUser user = (GraphUser)adapter.getItem(i);
			stringBuffer.append("Name: "+user.getName());
			if(user.getLocation()!=null){
				stringBuffer.append("\tLocation: "+user.getLocation().getCity());
			}
			stringBuffer.append("\tBirthDate: "+user.getBirthday());
			stringBuffer.append("\n");
		}
		
		FileOutputStream fos = null;
		try {
			fos = context.openFileOutput(FILENAME, Context.MODE_WORLD_READABLE);
			fos.write(new String(stringBuffer).getBytes());
			System.out.println(new File(FILENAME).getAbsolutePath());
			System.out.println(context.getFilesDir().getAbsolutePath());
			FileInputStream fin = context.openFileInput(FILENAME);
			byte[] reader = new byte[fin.available()];
			                    while (fin.read(reader) != -1) {}
			                   System.out.println(new String(reader));

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				fos.flush();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
