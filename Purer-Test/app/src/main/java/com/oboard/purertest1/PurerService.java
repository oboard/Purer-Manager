package com.oboard.purertest1;

/*Purer API 2*/
import android.app.Notification;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Process;

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
	
	//展开／关闭通知栏
	public static void notificationpage(boolean show) {
		run("notificationpage", show);
	}//启动程序
	public static void open(String packagename) {
		run("open", packagename);
	}//Toast
	public static void toast(String message) {
		run("toast", message);
	}//SnackBar
	public static void snack(String message) {
		run("snack", message);
	}//自杀
	public static void died() {
		if (!mData.getBoolean(mContext.getPackageName() + ".p.died", true)) return;
		android.os.Process.killProcess(Process.myPid());
	}//通知
	public static void notification(String ticker, String title, String text, int number, Icon icon) {
        if (!mData.getBoolean(mContext.getPackageName() + ".p.notification", true)) return;
		NotificationManager notiManager = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
        Notification notify3 = new Notification.Builder(mContext)  
            .setSmallIcon(icon)
            .setTicker(title)
            .setContentTitle(title)  
            .setContentText(text)  
            .setNumber(number).build();
        notify3.flags |= Notification.FLAG_AUTO_CANCEL;
        notiManager.notify(number, notify3);
	}
	
	private static void run(String command, String value) {
		Intent i = new Intent();
		i.setComponent(new ComponentName("com.oboard.purer", "com.oboard.purer.PurerService"));  

 		i.putExtra("i", mContext.getPackageName());
		i.putExtra("c", command);
		i.putExtra("v", value);
		mContext.startService(i);
	} private static void run(String command, boolean value) {
		Intent i = new Intent();
		i.setComponent(new ComponentName("com.oboard.purer", "com.oboard.purer.PurerService"));  
		
		i.putExtra("i", mContext.getPackageName());
		i.putExtra("c", command);
		i.putExtra("v", value);
		mContext.startService(i);
	} private static void run(String command, int value) {
		Intent i = new Intent();
		i.setComponent(new ComponentName("com.oboard.purer", "com.oboard.purer.PurerService"));  
		
		i.putExtra("i", mContext.getPackageName());
		i.putExtra("c", command);
		i.putExtra("v", value);
		mContext.startService(i);
	}
}
