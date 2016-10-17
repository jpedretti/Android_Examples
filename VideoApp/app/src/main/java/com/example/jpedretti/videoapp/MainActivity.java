package com.example.jpedretti.videoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener{

    public static final int RECORD_AND_PLAY = 0;
    public static final int PLAY_IN_APP_VIDEO = 1;
    public static final int PLAY_FROM_WEB = 2;
    public static final String ACTION_VALUE = "action_value";

    private Button mRecAndPlayButton;
    private Button mPlayInAppVideoButton;
    private Button mPlayWebVideoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecAndPlayButton = (Button) findViewById(R.id.rec_and_play_button);
        mRecAndPlayButton.setOnClickListener(this);

        mPlayInAppVideoButton = (Button) findViewById(R.id.play_in_app_button);
        mPlayInAppVideoButton.setOnClickListener(this);

        mPlayWebVideoButton = (Button) findViewById(R.id.play_from_web_button);
        mPlayWebVideoButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), VideoActivity.class);
        if(v == mRecAndPlayButton){
            intent.putExtra(ACTION_VALUE, RECORD_AND_PLAY);
        } else if (v == mPlayInAppVideoButton){
            intent.putExtra(ACTION_VALUE, PLAY_IN_APP_VIDEO);
        } else if (v == mPlayWebVideoButton){
            intent.putExtra(ACTION_VALUE, PLAY_FROM_WEB);
        }
        startActivity(intent);
    }
}
