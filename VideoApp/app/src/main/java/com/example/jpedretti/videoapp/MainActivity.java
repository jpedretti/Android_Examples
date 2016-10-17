package com.example.jpedretti.videoapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends Activity {

    private static final int VIDEO_REQUEST_CODE = 1;
    private VideoView mVideoView;
    private int mPosition = 0;
    private MediaController mMediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVideoView = (VideoView) findViewById(R.id.video_view);

        if(mMediaController == null){
            mMediaController = new MediaController(this);
        }

        mVideoView.setMediaController(mMediaController);

        takeVideo();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Position", mVideoView.getCurrentPosition());
        mVideoView.pause();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mPosition = savedInstanceState.getInt("Position");
        mVideoView.seekTo(mPosition);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == VIDEO_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri videoUri = data.getData();
            mVideoView.setVideoURI(videoUri);
        }
    }

    private void takeVideo() {
        Intent takeVideoIntent =  new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        if(takeVideoIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(takeVideoIntent, VIDEO_REQUEST_CODE);
        }
    }
}
