package com.example.jpedretti.weatherforecast;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by jpedretti on 10/10/2016.
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    public FragmentAdapter(FragmentManager m) {
        super(m);
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putInt("day", position);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return 5;
    }
}
