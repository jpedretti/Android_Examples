package com.example.jpedretti.audiorecorder;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MediaRecorder mMediaRecorder;
    private String mFileName;
    private ImageView mRecImageView;
    private ImageView mStopImageView;
    private ImageView mPlayImageView;
    private TextView mTextView;
    private MediaPlayer mMediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";

        setUpMediaPlayer();

        setUpMediaRecorder();

        mTextView = (TextView) findViewById(R.id.text_view);

        mRecImageView = (ImageView) findViewById(R.id.record_button);
        mRecImageView.setOnClickListener(this);

        mStopImageView = (ImageView) findViewById(R.id.stop_button);
        mStopImageView.setOnClickListener(this);

        mPlayImageView = (ImageView) findViewById(R.id.play_button);
        mPlayImageView.setOnClickListener(this);

    }

    private void setUpMediaRecorder() {
        mMediaRecorder = new MediaRecorder();
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mMediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mMediaRecorder.setOutputFile(mFileName);
    }

    private void setUpMediaPlayer() {
        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource(mFileName);
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.stop();
                    mTextView.setText(R.string.stoped_palying);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mRecImageView) {
            startRecording();
        } else if (v == mStopImageView) {
            StopRecordingOrPlaying();
        } else if (!mMediaPlayer.isPlaying() && v == mPlayImageView) {
            startPlaying();
        }
    }

    private void startPlaying() {
        try {
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mTextView.setText(R.string.playing);
    }

    private void StopRecordingOrPlaying() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mTextView.setText(R.string.stoped_palying);
        } else {
            mMediaRecorder.release();
            //mMediaRecorder.stop(); //calling this method causes the app to crash
            mTextView.setText(R.string.recording_complete);
        }
    }

    private void startRecording() {
        try {
            mMediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaRecorder.start();
        mTextView.setText(R.string.recording);
    }
}
