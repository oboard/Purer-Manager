package com.oboard.purertest1;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class PurerService extends BroadcastReceiver {
	
	public static Context context;
	public static String SACTION = "com.oboard.purer.api";
	public static String ACTION = "";
	
	public static void init(Context c) {
		context = c;
		ACTION = c.getPackageName() + ".api";
		
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(PurerService.ACTION);
		c.registerReceiver(new PurerService(), intentFilter);
				
		Intent in = new Intent(SACTION);
		in.putExtra("id", ACTION);
        in.putExtra("command", "state");
        c.sendBroadcast(in);
	}

	@Override
    public void onReceive(Context context, Intent intent) {
        String data = intent.getExtras().getString("command");

		switch (data) {
            case "state":
                //返回状态
				mOnStateListener.onState(intent.getExtras().getBoolean("back"));
                break;
        }
        //abortBroadcast();//接收到广播后中断广播
    }

	public static OnStateListener mOnStateListener;

	interface OnStateListener {
        public void onState(boolean on);
    }

	public static void setOnStateListener(OnStateListener onStateListener) {
        mOnStateListener = onStateListener;
    }
}
