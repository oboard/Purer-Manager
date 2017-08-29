package com.oboard.purer;

import android.app.Activity;
import android.os.Bundle;
import android.animation.TimeInterpolator;
import android.view.View.OnClickListener;
import android.view.View;
import android.animation.ValueAnimator;
import android.widget.Switch;
import android.animation.ArgbEvaluator;
import android.widget.ImageView;
import android.widget.CompoundButton;
import android.widget.TextView;

public class MainActivity extends Activity {

    ArgbEvaluator ae = new ArgbEvaluator();
    Switch main_launch;
    ImageView main_image;
    TextView main_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setElevation(0);
        setContentView(R.layout.main);
        
        main_title = (TextView)findViewById(R.id.main_title);
        main_image = (ImageView)findViewById(R.id.main_image);
        main_launch = (Switch)findViewById(R.id.main_launch);
        main_launch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton button, boolean b) {
                ValueAnimator mAni;
                if (b) {
                    mAni = ValueAnimator.ofFloat(0, 1);
                    main_title.setText("Purer 框架已启动");
                    main_image.setImageResource(R.drawable.ic_check_circle);
                } else {
                    mAni = ValueAnimator.ofFloat(1, 0);
                    main_title.setText("Purer 框架未启动");
                    main_image.setImageResource(R.drawable.ic_error);
                }
                mAni.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float av = animation.getAnimatedValue();
                            main_title.setTextColor(ae.evaluate(av, getColor(R.color.pred), getColor(R.color.pgreen)));
                            main_image.setBackgroundColor(main_title.getCurrentTextColor());
                        }
                    });
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
