package com.oboard.purer;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;  

public class BootBroadcastReceiver extends BroadcastReceiver {  
    //重写onReceive方法  
    @Override  
    public void onReceive(Context context, Intent intent) {
        MainReceiver m = new MainReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MainReceiver.ACTION);
        context.registerReceiver(m, intentFilter);
    }  

}  
