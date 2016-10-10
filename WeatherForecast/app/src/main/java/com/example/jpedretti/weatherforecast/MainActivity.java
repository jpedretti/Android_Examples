package com.example.jpedretti.weatherforecast;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewPager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

    private ViewPager viewPager;
    private ActionBar actionBar;
    private static int notificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.pager);
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        setUpTabNavigation();

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (notificationId == 0) {
            postAlert(0);
        }
    }

    private void postAlert(int i) {
        NotificationCompat.Builder builder = getBuilder(i);

        PendingIntent pendingIntent = getPendingIntent();

        builder.setContentIntent(pendingIntent);

        notify(builder);
    }

    private void notify(NotificationCompat.Builder builder) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId,builder.build());
        notificationId++;
    }

    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class)
                .addNextIntent(intent);

        return stackBuilder.
                getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @NonNull
    private NotificationCompat.Builder getBuilder(int forecastIndex) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Weather Alert!")
                .setContentText(WeatherData.outlookArray[forecastIndex])
                .setSmallIcon(R.drawable.small_icon)
                .setAutoCancel(true)
                .setTicker("Wrap up warm!")
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        WeatherData.symbolArray[forecastIndex]));

        NotificationCompat.BigPictureStyle bigStyle = getStyle();

        builder.setStyle(bigStyle);
        return builder;
    }

    @NonNull
    private NotificationCompat.BigPictureStyle getStyle() {
        NotificationCompat.BigPictureStyle bigStyle = new NotificationCompat.BigPictureStyle();
        bigStyle.bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.snow_scene));
        return bigStyle;
    }

    private void setUpTabNavigation() {
        Calendar calendar = Calendar.getInstance();
        String weekDay;
        SimpleDateFormat dayFormat;
        dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());

        actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.addTab(actionBar.newTab().setText("Today").setTabListener(this));

        for (int i = 0; i < 5; i++) {
            calendar.add(Calendar.DAY_OF_WEEK, 1);
            weekDay = dayFormat.format(calendar.getTime());
            actionBar.addTab(actionBar.newTab().setText(weekDay).setTabListener(this));
        }
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
