package com.oboard.purer;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import android.view.KeyEvent;

public class PurerService extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        S.init(this, "com.oboard.purer");
        if (!S.get("s", false)) finish();
        switch (getIntent().getExtras().getString("command", "")) {
            case "shell":
                exec(getIntent().getExtras().getString("value", ""));
                break;
            case "notification":
                if (getIntent().getExtras().getBoolean("value", false))
                    expandNotification(this);
                else
                    collapsingNotification(this);
                break;
            case "key":
                try {
                    if (getIntent().getExtras().getInt("value", -1) == -1) {
                        String keyCommand = "input keyevent " + getIntent().getExtras().getInt("value", 0);
                        Process proc = Runtime.getRuntime().exec(keyCommand);
                    }
                } catch (IOException e) {
                    e.printStackTrace();  
                }
                
                break;
        }
        finish();
    }

    public static void collapsingNotification(Context context) {
        Object service = context.getSystemService("statusbar");

        if (null == service) return;

        try {
            Class<?> clazz = Class.forName("android.app.StatusBarManager");
            int sdkVersion = android.os.Build.VERSION.SDK_INT;
            Method collapse = null;

            if (sdkVersion <= 16) {
                collapse = clazz.getMethod("collapse");
            } else {
                collapse = clazz.getMethod("collapsePanels");
            }
            collapse.setAccessible(true);
            collapse.invoke(service);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void expandNotification(Context context) {
        Object service = context.getSystemService("statusbar");
        if (null == service) return;
        try {
            Class<?> clazz = Class.forName("android.app.StatusBarManager");
            int sdkVersion = android.os.Build.VERSION.SDK_INT;
            Method expand = null;

            if (sdkVersion <= 16) {
                expand = clazz.getMethod("expand");
            } else {
                /*

                 * Android SDK 16之后的版本展开通知栏有两个接口可以处理

                 * expandNotificationsPanel()

                 * expandSettingsPanel()

                 */

                //expand =clazz.getMethod("expandNotificationsPanel");
                expand = clazz.getMethod("expandSettingsPanel");
            }
            expand.setAccessible(true);
            expand.invoke(service);
        } catch (Exception e) {

            e.printStackTrace();

        }

    }


    private String exec(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));
            int read;
            char[] buffer = new char[4096];
            StringBuffer output = new StringBuffer();
            while ((read = reader.read(buffer)) > 0) {
                output.append(buffer, 0, read);
            }
            reader.close();
            process.waitFor();
            return output.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
