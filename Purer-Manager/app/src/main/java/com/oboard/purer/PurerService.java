package com.oboard.purer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;
import java.lang.reflect.Method;
import java.util.List;
import android.graphics.drawable.Icon;
import android.graphics.Bitmap;
import android.content.pm.ApplicationInfo;
import java.net.IDN;

public class PurerService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        S.init(this, "com.oboard.purer");

        //ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);

        if (!S.get("s", false)) {
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }
        Bundle e = intent.getExtras();
        String a = e.getString("i") + ".p.";
        switch (e.getString("c", "")) {
            case "open":
                if (!S.get(a + "open", true)) break;
                openApp(this, e.getString("v"));
                break;
            case "notificationpage":
                if (!S.get(a + "notificationpage", true)) break;
                if (e.getBoolean("v", false))
                    expandNotification(this);
                else
                    collapsingNotification(this);
                break;
            case "toast":
                if (!S.get(a + "toast", true)) break;
                new ToastMessageTask().execute(e.getString("v"));
                break;
            case "snack":
                if (!S.get(a + "snack", true)) break;
                new SnackMessageTask().execute(e.getString("v"));
                break;
        }

        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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

    private class ToastMessageTask extends AsyncTask<String, String, String> {
        String toastMessage;

        @Override
        protected String doInBackground(String... params) {
            toastMessage = params[0];
            return toastMessage;
        }

        protected void OnProgressUpdate(String... values) { 
            super.onProgressUpdate(values);
        }
        // This is executed in the context of the main GUI thread
        protected void onPostExecute(String result){
            Toast toast = Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    
    private class SnackMessageTask extends AsyncTask<String, String, String> {
        String toastMessage;

        @Override
        protected String doInBackground(String... params) {
            toastMessage = params[0];
            return toastMessage;
        }

        protected void OnProgressUpdate(String... values) { 
            super.onProgressUpdate(values);
        }
        // This is executed in the context of the main GUI thread
        protected void onPostExecute(String result){
            SnackBar toast = SnackBar.makeText(getApplicationContext(), result, SnackBar.LENGTH_SHORT);
            toast.show();
        }
    }
     
}
   
