package com.oboard.purer;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout;
import android.view.WindowManager;
import android.os.Handler;
import android.os.Message;
import android.graphics.PixelFormat;

public class SnackBar {

    public static final int LENGTH_SHORT = 2000;
    public static final int LENGTH_LONG = 3500;

   
    LinearLayout toast;
    Context mContext;
    TextView toastTextField;
    int mDuration = 2000;

    public SnackBar(Context context) {
        mContext = context;
        toast = new LinearLayout(context);
        toast.setBackgroundColor(0xFF212121);
        toast.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(10, 10, 10, 10);
        toastTextField = new TextView(context);
        toastTextField.setTextColor(0xFFFFFFFF);
        toastTextField.setTextSize(20);
        toastTextField.setLayoutParams(lp);
        toast.addView(toastTextField);
    }

    public void setDuration(int d) {
        mDuration = d;
    }

    public void setText(String t) {
        toastTextField.setText(t);
    }

    public static SnackBar makeText(Context context, String text, int duration) {
        SnackBar toastCustom = new SnackBar(context);
        toastCustom.setText(text);
        toastCustom.setDuration(duration);
        return toastCustom;
    }

    public void show() {
       
        // 取得系统窗体
        final WindowManager mWindowManager = (WindowManager) mContext.getSystemService("window");
        // 窗体的布局样式
        WindowManager.LayoutParams mLayoutParams = new WindowManager.LayoutParams();
        // 设置窗体显示类型――TYPE_SYSTEM_ALERT(系统提示)
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        // 设置窗体焦点及触摸：
        // FLAG_NOT_FOCUSABLE(不能获得按键输入焦点)
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // 设置显示的模式
        mLayoutParams.format = PixelFormat.RGBA_8888;
        // 设置对齐的方法
        mLayoutParams.gravity = Gravity.BOTTOM;
        // 设置窗体宽度和高度
        mLayoutParams.width = WindowManager.LayoutParams.FILL_PARENT;
        mLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowManager.addView(toast, mLayoutParams);
        
        new Handler(){  
            @Override  
            public void handleMessage(Message msg) {  
                super.handleMessage(msg);
                mWindowManager.removeView(toast);
            }  
        }.sendEmptyMessageDelayed(0, mDuration);  
    }

}
