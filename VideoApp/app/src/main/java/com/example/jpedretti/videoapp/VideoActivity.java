package com.example.jpedretti.videoapp;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity {

    private static final int VIDEO_REQUEST_CODE = 1;
    private VideoView mVideoView;
    private int mPosition = 0;
    private MediaController mMediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        mVideoView = (VideoView) findViewById(R.id.video_view);

        if(mMediaController == null){
            mMediaController = new MediaController(this);
        }

        mVideoView.setMediaController(mMediaController);
        int action = getIntent().getIntExtra(MainActivity.ACTION_VALUE, 0);
        if(action == MainActivity.RECORD_AND_PLAY){
            recordAndPlayVideo();
        } else if (action == MainActivity.PLAY_IN_APP_VIDEO){
            playInAppVideo();
        } else if (action == MainActivity.PLAY_FROM_WEB){
            playFromWebVideo();
        }
    }

    private void playFromWebVideo() {
        mVideoView.setVideoPath(
                "http://www.your_site.com/movies/movie.mp4");
        mVideoView.start();
    }

    private void playInAppVideo() {
        mVideoView.setVideoURI(Uri.parse("android.resource://" +
                getPackageName() + "/" + R.raw.movie));
        mVideoView.start();
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

    private void recordAndPlayVideo() {
        Intent takeVideoIntent =  new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        if(takeVideoIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(takeVideoIntent, VIDEO_REQUEST_CODE);
        }
    }
}
