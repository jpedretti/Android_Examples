package com.example.jpedretti.chapter4recyclerview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class DetailActivity extends AppCompatActivity {

    private ImageView detailImage;
    private int selectedItem;
    private static final int MIN_DISTANCE = 120;
    private static final int OFF_PATH = 100;
    private static final int VELOCITY_THRESHOLD = 75;
    private GestureDetector detector;
    View.OnTouchListener listener;
    private int imageIndex;
    private static final String DEBUG_TAG = "tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        selectedItem = getIntent().getIntExtra(MainActivity.SELECTED_ITEM_ID, -1);

        detailImage = (ImageView) findViewById(R.id.detail_image);

        TextView detailName = (TextView) findViewById(R.id.detail_name);

        TextView detailDistance = (TextView) findViewById(R.id.detail_distance);

        TextView detailText = (TextView) findViewById(R.id.detail_text);
        detailText.setMovementMethod(new ScrollingMovementMethod());

        ImageView detailWebLink = (ImageView) findViewById(R.id.detail_web_link);

        Random n = new Random();
        int m = n.nextInt((600 - 20) + 1) + 20;

        setTitle(getString(R.string.app_name) + " - " + MainData.
                nameArray[selectedItem]);
        detailImage.setImageResource(MainData.detailImageArray[selectedItem]);
        detailName.setText(MainData.nameArray[selectedItem]);
        detailDistance.setText(String.valueOf(m) + " miles");
        detailText.setText(MainData.detailTextArray[selectedItem]);

        detailWebLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(
                        MainData.detailWebLink[selectedItem]));
                startActivity(intent);
            }
        });

        detector = new GestureDetector(this, new GalleryGestureDetector());
        listener = new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return detector.onTouchEvent(event);
            }
        };

        imageIndex = 0;
        detailImage.setOnTouchListener(listener);
    }

    private class GalleryGestureDetector extends GestureDetector.SimpleOnGestureListener {
        private int item  = selectedItem;

        @Override
        public boolean onDown(MotionEvent e) {
            Log.d(DEBUG_TAG, "onDown");
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            Log.d(DEBUG_TAG, "onShowPress");
            detailImage.setElevation(4);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (Math.abs(e1.getY() - e2.getY()) > OFF_PATH) {
                return false;
            }

            if (MainData.galleryArray[item].length != 0) {
                //Swipe Left
                if (e1.getX() - e2.getX() > MIN_DISTANCE &&
                        Math.abs(velocityX) > VELOCITY_THRESHOLD) {
                    imageIndex++;
                    if (imageIndex == MainData.galleryArray[item].length) {
                        imageIndex = 0;
                    }

                    detailImage.setImageResource(MainData.galleryArray[item][imageIndex]);
                    Log.d(DEBUG_TAG, "left");
                } else if (e2.getX() - e1.getX() > MIN_DISTANCE &&
                        Math.abs(velocityX) > VELOCITY_THRESHOLD) {
                    //Swipe right
                    imageIndex--;
                    if (imageIndex < 0) {
                        imageIndex = MainData.galleryArray[item].length - 1;
                    }
                    detailImage.setImageResource(MainData.galleryArray[item][imageIndex]);
                    Log.d(DEBUG_TAG, "right");
                }
            }
            detailImage.setElevation(0);
            return true;
        }
    }
}
