package com.mavedev.profileutils.contacts.export;

import android.content.Context;
import android.util.SparseBooleanArray;

public class ExportFactory {

	public static FriendsExporter getExporter(int exportType, FriendListViewAdapter adapter, SparseBooleanArray checkedItemPositions, Context context, boolean sendEmail){
		if(exportType == ExportType.TEXT){
			return new TextFriendsExporter(adapter, checkedItemPositions, context, sendEmail);
		}else if(exportType == ExportType.HTML){
			return new TextFriendsExporter(adapter, checkedItemPositions, context, sendEmail);
		}
		return null;
	}
}
