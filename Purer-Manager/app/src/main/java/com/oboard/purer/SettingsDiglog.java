package com.oboard.purer;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import java.util.List;

public class SettingsDiglog extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
    }

    public void c(View v) {
        finish();
    }

    public void onChangeIcon(View v) {
        changeLuncher(v.getTag().toString());
        finish();
    }

    private void changeLuncher(String name) {
        PackageManager pm = getPackageManager();
        pm.setComponentEnabledSetting(new ComponentName(SettingsDiglog.this,"com.oboard.purer.MainActivity"), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        pm.setComponentEnabledSetting(new ComponentName(SettingsDiglog.this,"com.oboard.purer.ic2"), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        
        pm.setComponentEnabledSetting(new ComponentName(SettingsDiglog.this, name), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }

}
