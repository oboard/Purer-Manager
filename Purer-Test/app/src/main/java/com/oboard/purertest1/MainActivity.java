package com.oboard.purertest1;

import android.app.Activity;
import android.os.Bundle;
import android.content.IntentFilter;
import android.view.View;
import android.view.KeyEvent;

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
				PurerService.notification(true);
				break;
			case "1":
				PurerService.key(KeyEvent.KEYCODE_APP_SWITCH);
				break;
		}
	}
}
