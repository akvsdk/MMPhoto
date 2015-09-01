package com.ep.jyq.mmphoto.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ep.jyq.mmphoto.R;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.Transition;

/**
 * Created by Joy on 2015/9/1.
 */
public class LaunchActivity extends AppCompatActivity {

    private KenBurnsView iv;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        iv = (KenBurnsView) findViewById(R.id.iv_welcome);
        iv.setTransitionListener(new KenBurnsView.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                startActivity(new Intent(LaunchActivity.this, MainActivity.class));
                finish();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        iv.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        iv.pause();
    }
}
