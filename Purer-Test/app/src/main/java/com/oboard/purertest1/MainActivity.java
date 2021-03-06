package com.oboard.purertest1;

import android.app.Activity;
import android.os.Bundle;
import android.content.IntentFilter;
import android.view.View;
import android.view.KeyEvent;
import android.widget.Toast;
import android.graphics.drawable.Icon;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		PurerService.init(this);
		
		if (PurerService.getState()) {
			setTitle("Purer框架可用");
			//PurerService.shell("wm size 3200x1800");
			//PurerService.notification(true);
		} else
			setTitle("Purer框架不可用");

    }
	
	public void go(View v) {
		switch (v.getTag().toString()) {
			case "0":
				PurerService.notificationpage(true);
				break;
			case "1":
				PurerService.toast("hello");
				break;
			case "2":
				PurerService.snack("hello");
				break;
			case "3":
				PurerService.notification("hello0","hello1","hello2", 0, Icon.createWithResource(this, R.mipmap.i));
				break;
			case "4":
				PurerService.died();
				break;	
		}
	}
}
