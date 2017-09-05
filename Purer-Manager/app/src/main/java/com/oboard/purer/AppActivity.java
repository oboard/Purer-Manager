package com.oboard.purer;
import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class AppActivity extends Activity {

    String app_package;
    Switch[] app_switch = new Switch[6];
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app);
        setTitle("");
        getActionBar().setDisplayHomeAsUpEnabled(true);

        ((TextView)findViewById(R.id.app_text)).setText(getIntent().getStringExtra("n"));
        ((ImageView)findViewById(R.id.app_icon)).setImageDrawable(getAppIcon(getIntent().getStringExtra("i")));
        
        app_switch[0] = (Switch)findViewById(R.id.app_0);
        app_switch[1] = (Switch)findViewById(R.id.app_1);
        app_switch[2] = (Switch)findViewById(R.id.app_2);
        app_switch[3] = (Switch)findViewById(R.id.app_3);
        app_switch[4] = (Switch)findViewById(R.id.app_4);
        app_switch[5] = (Switch)findViewById(R.id.app_5);
        app_package = getIntent().getStringExtra("i");
        app_package = app_package + ".p.";
        app_switch[0].setChecked(S.get(app_package + "notificationpage", true));
        app_switch[1].setChecked(S.get(app_package + "notification", true));
        app_switch[2].setChecked(S.get(app_package + "toast", true));
        app_switch[3].setChecked(S.get(app_package + "snack", true));
        app_switch[4].setChecked(S.get(app_package + "died", true));
        app_switch[5].setChecked(S.get(app_package + "open", true));

        app_switch[0].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton cb, boolean value) {
                    S.put(app_package + "notificationpage", value);
                    S.ok();
                }
            });
        app_switch[1].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton cb, boolean value) {
                    S.put(app_package + "notification", value);
                    S.ok();
                }
            });
        app_switch[2].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton cb, boolean value) {
                    S.put(app_package + "toast", value);
                    S.ok();
                }
            });
        app_switch[3].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton cb, boolean value) {
                    S.put(app_package + "snack", value);
                    S.ok();
                }
            });
        app_switch[4].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton cb, boolean value) {
                    S.put(app_package + "died", value);
                    S.ok();
                }
            });
        app_switch[5].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton cb, boolean value) {
                    S.put(app_package + "open", value);
                    S.ok();
                }
            });
    }

    public Drawable getAppIcon(String packname) {
        try {
            PackageManager pm = getPackageManager();
            ApplicationInfo info = pm.getApplicationInfo(packname, 0);
            return info.loadIcon(pm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    } @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) finish();
        return super.onKeyDown(keyCode, event);
    }
    
    
}
