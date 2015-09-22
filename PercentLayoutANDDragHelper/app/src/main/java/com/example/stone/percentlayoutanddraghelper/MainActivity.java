package com.example.stone.percentlayoutanddraghelper;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {
    Button topRight,topLeft,midRight,midLeft1,midLeft2,bot;
    PercentRelativeLayout PRL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    void listener(){
        topRight = (Button)findViewById(R.id.topRight);
        topLeft = (Button)findViewById(R.id.topLeft);
        midRight = (Button)findViewById(R.id.midRight);
        midLeft1 = (Button)findViewById(R.id.midLeft1);
        midLeft2 = (Button)findViewById(R.id.midLeft2);
        PRL = (PercentRelativeLayout)findViewById(R.id.PRL);
        bot = (Button)findViewById(R.id.bot);
        topLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animator(topLeft,topRight);
            }
        });
        topRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,DragActivity.class);
                startActivity(intent);
            }
        });
    }
    void animator(final View topLeft, final View topRight){
        ValueAnimator anim1 = ValueAnimator.ofFloat(0.3f,0.5f).setDuration(500);
        ValueAnimator anim2 = ValueAnimator.ofFloat(0.7f,0.5f).setDuration(500);
        final PercentRelativeLayout.LayoutParams params = new PercentRelativeLayout.LayoutParams(topLeft.getLayoutParams());
        final PercentRelativeLayout.LayoutParams params2 = new PercentRelativeLayout.LayoutParams(topRight.getLayoutParams());
        anim1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float)animation.getAnimatedValue();
                params.getPercentLayoutInfo().widthPercent = value;
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                params.addRule(RelativeLayout.ALIGN_PARENT_START);
                topLeft.setLayoutParams(params);
            }
        });
        anim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                params2.getPercentLayoutInfo().widthPercent = value;
                params2.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                params2.addRule(RelativeLayout.ALIGN_PARENT_END);
                topRight.setLayoutParams(params2);
            }
        });
        AnimatorSet set = new AnimatorSet();
        set.playTogether(anim1,anim2);
        set.start();
    }
}
