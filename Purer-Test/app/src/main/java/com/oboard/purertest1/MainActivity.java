package com.oboard.purertest1;

import android.app.Activity;
import android.os.Bundle;
import android.content.IntentFilter;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		PurerService.init(this);
		
		PurerService.setOnStateListener(new PurerService.OnStateListener() {
			public void onState(boolean o) {
				if (o)
					setTitle("Purer框架可用");
				else
					setTitle("Purer框架不可用");
			}
		});
		
    }
}
