package com.oboard.purer;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.List;

public class PurerService extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        S.init(this, "com.oboard.purer");

        if (!S.get("s", false)) {
            finish();
            return;
        }

        Bundle e = getIntent().getExtras();
        switch (e.getString("command", "")) {
            case "shell":
                exec(e.getString("value", ""));
                break;
            case "open":
                openApp(this, e.getString("value"));
                break;
            case "kill":
                ActivityManager activityMgr = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
                activityMgr.killBackgroundProcesses(e.getString("value"));
                break;
            case "notification":
                if (e.getBoolean("value", false))
                    expandNotification(this);
                else
                    collapsingNotification(this);
                break;
            case "su":
                exec("su");
                break;
            case "tap":
                exec("input tap " + e.getInt("value", 0));
                break;
            case "key":
                exec("input keyevent " + e.getInt("value", 0));
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

    public static void openApp(Context c, String packageName) {
        try {
            PackageManager pm = c.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);

            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            resolveIntent.setPackage(pi.packageName);

            List<ResolveInfo> apps = pm.queryIntentActivities(resolveIntent, 0);

            ResolveInfo ri = apps.iterator().next();
            if (ri != null) {
                //String packageName = ri.activityInfo.packageName;
                String className = ri.activityInfo.name;

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);            

                ComponentName cn = new ComponentName(packageName, className);

                intent.setComponent(cn);
                c.startActivity(intent);
            }
        } catch (Exception e) {

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

    private void exec(String cmd) {  
        try {  
            // 申请获取root权限，这一步很重要，不然会没有作用  
            Process process = Runtime.getRuntime().exec("su");  
            // 获取输出流  
            OutputStream outputStream = process.getOutputStream();  
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);  
            dataOutputStream.writeBytes(cmd);  
            dataOutputStream.flush();  
            dataOutputStream.close();  
            outputStream.close();  
        } catch (Throwable t) {  
            t.printStackTrace();  
        }  
    }  

    /*
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
     }*/

}
