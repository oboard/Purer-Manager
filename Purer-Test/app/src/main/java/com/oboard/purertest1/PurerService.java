package com.oboard.purertest1;

/*Purer API 1*/
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.Intent;

public class PurerService {

	public static Context mContext;
	public static SharedPreferences mSharedPreferences = null;

	public static void init(Context c) {
		mContext = c;
		
		Context cc = null;
        try {
            cc = c.createPackageContext("com.oboard.purer", Context.CONTEXT_IGNORE_SECURITY);
		} catch (PackageManager.NameNotFoundException e) {
			return;
		}
		
		if (cc != null) mSharedPreferences = cc.getSharedPreferences("com.oboard.purer", Context.MODE_WORLD_READABLE);
	}

	//获得Purer状态
	public static boolean getState() {
		if (mSharedPreferences == null) return false;
		return mSharedPreferences.getBoolean("s", false);
	}
	
	//Android Shell
	public static void shell(String command) {
		run("shell", command);
	}
	//展开／关闭通知栏
	public static void notification(boolean show) {
		run("notification", show);
	}
	//root
	public static void root() {
		run("root", "");
	}
	//模拟按键
	public static void key(int keycode) {
		run("key", keycode);
	}
	//模拟点击
	public static void tap(int x, int y) {
		run("tap", x + " " + y);
	}
	//启动程序
	public static void open(String packagename) {
		run("open", packagename);
	}
	//关闭程序
	public static void kill(String packagename) {
		run("kill", packagename);
	}
	
	
	private static void run(String command, boolean value) {
		Intent i = new Intent("com.oboard.purer.api");
		i.addCategory(i.CATEGORY_DEFAULT);
		i.putExtra("id", mContext.getPackageName());
		i.putExtra("command", command);
		i.putExtra("value", value);
		mContext.startActivity(i);
	} private static void run(String command, String value) {
		Intent i = new Intent("com.oboard.purer.api");
		i.addCategory(i.CATEGORY_DEFAULT);
 		i.putExtra("id", mContext.getPackageName());
		i.putExtra("command", command);
		i.putExtra("value", value);
		mContext.startActivity(i);
	} private static void run(String command, int value) {
		Intent i = new Intent("com.oboard.purer.api");
		i.addCategory(i.CATEGORY_DEFAULT);
		i.putExtra("id", mContext.getPackageName());
		i.putExtra("command", command);
		i.putExtra("value", value);
		mContext.startActivity(i);
	}
}
