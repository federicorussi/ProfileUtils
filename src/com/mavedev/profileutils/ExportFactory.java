package com.mavedev.profileutils;

import android.content.Context;
import android.util.SparseBooleanArray;

public class ExportFactory {

	public static FriendsExporter getExporter(int exportType, FriendListViewAdapter adapter, SparseBooleanArray checkedItemPositions, Context context){
		if(exportType == ExportType.TEXT){
			return new TextFriendsExporter(adapter, checkedItemPositions, context);
		}else if(exportType == ExportType.HTML){
			return new TextFriendsExporter(adapter, checkedItemPositions, context);	
		}
		return null;
	}
}
