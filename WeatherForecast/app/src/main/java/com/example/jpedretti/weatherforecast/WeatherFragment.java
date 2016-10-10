package com.example.jpedretti.weatherforecast;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by jpedretti on 10/10/2016.
 */
public class WeatherFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout,container,false);

        Bundle args = getArguments();
        int i = args.getInt("day");

        TextView textOutlook = (TextView) rootView.findViewById(R.id.text_outlook);
        ImageView symbolView = (ImageView) rootView.findViewById(R.id.image_symbol);
        TextView tempsView = (TextView) rootView.findViewById(R.id.text_temp);
        TextView windView = (TextView) rootView.findViewById(R.id.text_min);
        TextView realFeelView = (TextView) rootView.findViewById(R.id.text_real_fell);
        textOutlook.setText(WeatherData.outlookArray[i]);
        symbolView.setImageResource(WeatherData.symbolArray[i]);
        tempsView.setText(WeatherData.tempsArray[i] + "ºC");
        windView.setText("Min " + WeatherData.minArray[i] + "ºC");
        realFeelView.setText("Real feel " + WeatherData.realFeelArray[i] + "ºC");

        return rootView;
    }
}
