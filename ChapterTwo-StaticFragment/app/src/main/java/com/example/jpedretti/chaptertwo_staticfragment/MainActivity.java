package com.example.jpedretti.chaptertwo_staticfragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Fragment fragment;

        int id = item.getItemId();

        switch (id){
            case R.id.menu_date:
                startActivity(new Intent(Settings.ACTION_DATE_SETTINGS));
                break;
            case R.id.menu_location:
                startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
                break;
            case R.id.menu_sleep:
                startActivity(new Intent(Settings.ACTION_SOUND_SETTINGS));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
