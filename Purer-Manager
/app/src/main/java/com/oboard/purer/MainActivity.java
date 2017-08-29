package com.oboard.purer;

import android.app.Activity;
import android.os.Bundle;
import android.animation.TimeInterpolator;
import android.view.View.OnClickListener;
import android.view.View;
import android.animation.ValueAnimator;
import android.widget.Switch;
import android.animation.ArgbEvaluator;

public class MainActivity extends Activity {

    ArgbEvaluator ae = new ArgbEvaluator();
    Switch main_launch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        main_launch = (Switch)findViewById(R.id.main_launch);
        main_launch.setOnClickListener(new OnClickListener() {
           public void onClick(View v) {
               ValueAnimator mAni = ValueAnimator.ofFloat(0, 1);
               mAni.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                       @Override
                       public void onAnimationUpdate(ValueAnimator animation) {
                           float av = animation.getAnimatedValue();
                           
                       }
                   });
               mAni.setInterpolator(new SpringInterpolator());
               mAni.setDuration(300).start();
           } 
        });
    }
}

class SpringInterpolator implements TimeInterpolator {
    private static final float DEFAULT_FACTOR = 0.4f;

    private float mFactor;

    public SpringInterpolator() {
        this(DEFAULT_FACTOR);
    }

    public SpringInterpolator(float factor) {
        mFactor = factor;
    }

    @Override
    public float getInterpolation(float input) {
        // pow(2, -10 * input) * sin((input - factor / 4) * (2 * PI) / factor) + 1
        return (float) (Math.pow(2, -10 * input) * Math.sin((input - mFactor / 4.0d) * (2.0d * Math.PI) / mFactor) + 1);

    }
}
