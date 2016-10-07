package com.example.jpedretti.chapter4recyclerview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;

public class SplashActivity extends Activity {

    private GestureDetector m_detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView imageView = (ImageView) findViewById(R.id.splash_image);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        m_detector = new GestureDetector(this, new SplashListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.m_detector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }


    private class SplashListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }
}
