package com.oboard.purer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MainReceiver extends BroadcastReceiver {

    private static final String TAG = "MainReceiver";
    public static final String ACTION = "com.oboard.purer.api";
    
    @Override
    public void onReceive(Context context, Intent intent) {
        String data = intent.getExtras().getString("command");
        Intent in = new Intent(intent.getExtras().getString("id"));
        in.putExtra("command", data);
        S.init(context, "com.oboad.purer");
        switch (data) {
            case "state":
                //返回状态
                
                in.putExtra("back", S.get("s", false));
                context.sendBroadcast(in);
                break;
        }
        //abortBroadcast();//接收到广播后中断广播
    }
}
