package com.oboard.purertest1;

/*Purer API 1*/
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.Intent;
import android.content.ComponentName;

public class PurerService {

	public static Context mContext;
	public static SharedPreferences mData = null;

	public static void init(Context c) {
		mContext = c;
		
		initData();
	}

	private static void initData() {
		Context cc = null;
        try {
            cc = mContext.createPackageContext("com.oboard.purer", Context.CONTEXT_IGNORE_SECURITY);
		} catch (PackageManager.NameNotFoundException e) {
			return;
		}

		if (cc != null) mData = cc.getSharedPreferences("com.oboard.purer", Context.MODE_WORLD_READABLE);
	}
	
	//获得Purer状态
	public static boolean getState() {
		if (mData == null) return false;
		return mData.getBoolean("s", false);
	}
	
	//Android Shell
	public static void shell(String command) {
		run("shell", command);
	}//展开／关闭通知栏
	public static void notification(boolean show) {
		run("notification", show);
	}//启动程序
	public static void open(String packagename) {
		run("open", packagename);
	}//Toast
	public static void toast(String message) {
		run("toast", message);
	}//SnackBar
	public static void snack(String message) {
		run("snack", message);
	}
	
	private static void run(String command, String value) {
		Intent i = new Intent();
		i.setComponent(new ComponentName("com.oboard.purer", "com.oboard.purer.PurerService"));  

 		i.putExtra("id", mContext.getPackageName());
		i.putExtra("c", command);
		i.putExtra("v", value);
		mContext.startService(i);
	} private static void run(String command, boolean value) {
		Intent i = new Intent();
		i.setComponent(new ComponentName("com.oboard.purer", "com.oboard.purer.PurerService"));  
		
		i.putExtra("id", mContext.getPackageName());
		i.putExtra("c", command);
		i.putExtra("v", value);
		mContext.startService(i);
	} private static void run(String command, int value) {
		Intent i = new Intent();
		i.setComponent(new ComponentName("com.oboard.purer", "com.oboard.purer.PurerService"));  
		
		i.putExtra("id", mContext.getPackageName());
		i.putExtra("c", command);
		i.putExtra("v", value);
		mContext.startService(i);
	}
}
