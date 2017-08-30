package com.oboard.purertest1;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.Intent;

public class PurerService {

	public static Context mContext;
	public static boolean mState = false;

	public static SharedPreferences mSharedPreferences;

	public static void init(Context c) {
		mContext = c;
		
		Context cc = null;
        try {  
            cc = c.createPackageContext("com.oboard.purer", Context.CONTEXT_IGNORE_SECURITY);
			
		} catch (PackageManager.NameNotFoundException e) {
			
		}
		if (cc != null) {
			mSharedPreferences = cc.getSharedPreferences("com.oboard.purer", Context.MODE_WORLD_READABLE);  
			mState = mSharedPreferences.getBoolean("s", false);
		} 
	}

	public static boolean getState() {
		return mState;
	}
	
	public static void shell(String command) {
		run("shell", command);
	} public static void notification(boolean show) {
		run("notification", show);
	} public static void key(int keycode) {
		run("key", keycode);
	}
	
	private static void run(String command, boolean value) {
		Intent i = new Intent("com.oboard.purer.api");
		i.addCategory(i.CATEGORY_DEFAULT);
		i.putExtra("command", command);
		i.putExtra("value", value);
		mContext.startActivity(i);
	} private static void run(String command, String value) {
		Intent i = new Intent("com.oboard.purer.api");
		i.addCategory(i.CATEGORY_DEFAULT);
		i.putExtra("command", command);
		i.putExtra("value", value);
		mContext.startActivity(i);
	} private static void run(String command, int value) {
		Intent i = new Intent("com.oboard.purer.api");
		i.addCategory(i.CATEGORY_DEFAULT);
		i.putExtra("command", command);
		i.putExtra("value", value);
		mContext.startActivity(i);
	}
}
