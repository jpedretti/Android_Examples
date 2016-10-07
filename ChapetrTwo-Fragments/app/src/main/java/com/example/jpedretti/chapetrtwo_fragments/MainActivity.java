package com.example.jpedretti.chapetrtwo_fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button analogButton, digitalButton;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);
        getActionBar().setTitle("Clock");
        toolbar.setSubtitle("tells the time");
        toolbar.setLogo(R.drawable.microsoft);

        analogButton = (Button) findViewById(R.id.button_analog);
        digitalButton = (Button) findViewById(R.id.button_digital);

        analogButton.setOnClickListener(this);
        digitalButton.setOnClickListener(this);
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

    @Override
    public void onClick(View clickedButton) {
        Fragment fragment;

        if(clickedButton == analogButton){
            fragment = new FragmentAnalog();
        } else{
            fragment = new FragmentDigital();
        }

        replaceFragment(fragment);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fManager = getFragmentManager();
        FragmentTransaction transaction = fManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(R.id.fragment_container,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
