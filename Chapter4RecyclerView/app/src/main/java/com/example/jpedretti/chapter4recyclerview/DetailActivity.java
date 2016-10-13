package com.example.jpedretti.chapter4recyclerview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static com.example.jpedretti.chapter4recyclerview.MainData.detailWebLink;

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
    private static final int PREVIEW_REQUEST_CODE = 1;
    private static final int SAVE_REQUEST_CODE = 2;
    private String photoPath;
    private File photoFile;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PREVIEW_REQUEST_CODE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            detailImage.setImageBitmap(imageBitmap);
        } else if ( requestCode == SAVE_REQUEST_CODE && resultCode == RESULT_OK){
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(photoFile);
            intent.setData(contentUri);
            this.sendBroadcast(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        selectedItem = getIntent().getIntExtra(MainActivity.SELECTED_ITEM_ID, -1);

        setCameraLink();

        ImageView cameraSave = (ImageView) findViewById(R.id.camera_save);
        cameraSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });

        setTitle(getString(R.string.app_name) + " - " + MainData.
                nameArray[selectedItem]);

        detailImage = (ImageView) findViewById(R.id.detail_image);
        detailImage.setImageResource(MainData.detailImageArray[selectedItem]);

        setDetailInfo();

        setDetailWebLink();

        setTouchListener();

        imageIndex = 0;
    }

    private void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager()) != null){
            photoFile = null;
            try {
                photoFile = fileName();
            }catch (IOException ex){
                Toast.makeText(this, "No SD Card.", Toast.LENGTH_SHORT).show();
            }

            if(photoFile != null){
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, SAVE_REQUEST_CODE);
            }
        }
    }

    private File fileName() throws IOException{
        String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String file = MainData.nameArray[selectedItem] + "_" + time + "_";
        File directory = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(file, ".jpg", directory);
        photoPath = "file:" + image.getAbsolutePath();

        return image;
    }

    private void setCameraLink() {
        ImageView cameraLink = (ImageView) findViewById(R.id.camera);
        cameraLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(intent,PREVIEW_REQUEST_CODE);
                }
            }
        });
    }

    private void setDetailInfo() {
        TextView detailText = (TextView) findViewById(R.id.detail_text);
        detailText.setMovementMethod(new ScrollingMovementMethod());
        detailText.setText(MainData.detailTextArray[selectedItem]);

        TextView detailName = (TextView) findViewById(R.id.detail_name);
        detailName.setText(MainData.nameArray[selectedItem]);

        Random n = new Random();
        int m = n.nextInt((600 - 20) + 1) + 20;
        TextView detailDistance = (TextView) findViewById(R.id.detail_distance);
        detailDistance.setText(String.valueOf(m) + " miles");
    }

    private void setTouchListener() {
        detector = new GestureDetector(this, new GalleryGestureDetector());
        listener = new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return detector.onTouchEvent(event);
            }
        };
        detailImage.setOnTouchListener(listener);
    }

    private void setDetailWebLink() {
        ImageView detailWebLinkImage = (ImageView) findViewById(R.id.detail_web_link);
        detailWebLinkImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(
                        detailWebLink[selectedItem]));
                startActivity(intent);
            }
        });
    }

    private class GalleryGestureDetector extends GestureDetector.SimpleOnGestureListener {
        private int item = selectedItem;

        @Override
        public boolean onDown(MotionEvent e) {
            Log.d(DEBUG_TAG, "onDown");
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            Log.d(DEBUG_TAG, "onShowPress");
            detailImage.setElevation(4);
            detailImage.setBackgroundColor(Color.GRAY);
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
            detailImage.setBackgroundColor(Color.TRANSPARENT);
            return true;
        }
    }
}
