package com.mavedev.profileutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Environment;
import android.util.SparseBooleanArray;

import com.facebook.model.GraphUser;

@TargetApi(19)
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
			ExtendedGraphUser extGraphUser = new ExtendedGraphUser(user);
			//name
			stringBuffer.append(user.getName());

            //location
            appendParameter(stringBuffer, "Location", extGraphUser.getCity());

			//Birthday
            appendParameter(stringBuffer, "BirthDate", user.getBirthday());

            //Gender
            appendParameter(stringBuffer, "Gender", extGraphUser.getGender());
			
			stringBuffer.append("\n");
		}
		
		FileOutputStream fos = null;
		try {
			File exportFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), FILENAME);
			fos = new FileOutputStream(exportFile);
			fos.write(new String(stringBuffer).getBytes());
			//readFile();

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

	private void appendParameter(StringBuffer stringBuffer, String key, String value) {
        if(value!=null){
            stringBuffer.append(String.format(",%s: %s", key, value));
        }else{
            stringBuffer.append(String.format(",%s: %s", key, "NA"));
        }
	}

	private void readFile() throws FileNotFoundException, IOException {
		FileInputStream fin = context.openFileInput(FILENAME);
		byte[] reader = new byte[fin.available()];
		                    while (fin.read(reader) != -1) {}
		                   System.out.println(new String(reader));
	}

}
