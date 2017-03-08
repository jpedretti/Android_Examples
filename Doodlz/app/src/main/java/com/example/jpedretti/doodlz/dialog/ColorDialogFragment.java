package com.example.jpedretti.doodlz.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.SeekBar;

import com.example.jpedretti.doodlz.DoodleView;
import com.example.jpedretti.doodlz.R;

/**
 Created by jpedretti on 05/12/2016.
 */

public class ColorDialogFragment extends BaseDialogFragment {
    private SeekBar alphaSeekBar;
    private SeekBar redSeekBar;
    private SeekBar greenSeekBar;
    private SeekBar blueSeekBar;
    private View colorView;
    private int color;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder  builder =
                new AlertDialog.Builder(getActivity());
        View colorDialogView = getActivity().getLayoutInflater().inflate(
                R.layout.fragment_color, null);
        builder.setView(colorDialogView);

        builder.setTitle(R.string.title_color_dialog);

        alphaSeekBar = (SeekBar) colorDialogView.findViewById(R.id.alpha_seek_bar);
        redSeekBar = (SeekBar) colorDialogView.findViewById(
                R.id.red_seek_bar);
        greenSeekBar = (SeekBar) colorDialogView.findViewById(
                R.id.green_seek_bar);
        blueSeekBar = (SeekBar) colorDialogView.findViewById(
                R.id.blue_seek_bar);
        colorView = colorDialogView.findViewById(R.id.color_view);

        alphaSeekBar.setOnSeekBarChangeListener(colorChangedListener);
        redSeekBar.setOnSeekBarChangeListener(colorChangedListener);
        greenSeekBar.setOnSeekBarChangeListener(colorChangedListener);
        blueSeekBar.setOnSeekBarChangeListener(colorChangedListener);

        final DoodleView doodleView = getDoodleFragment().getDoodleView();
        color = doodleView.getDrawingColor();

        alphaSeekBar.setProgress(Color.alpha(color));
        redSeekBar.setProgress(Color.red(color));
        greenSeekBar.setProgress(Color.green(color));
        blueSeekBar.setProgress(Color.blue(color));

        builder.setPositiveButton(R.string.button_set_color,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        doodleView.setDrawingColor(color);
                    }
                }
        );

        return builder.create();
    }

    private final SeekBar.OnSeekBarChangeListener colorChangedListener =
            new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                   setColorViewColor();
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) { }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) { }
            };

    private void setColorViewColor() {
        color = Color.argb( alphaSeekBar.getProgress(), redSeekBar.getProgress(),
                greenSeekBar.getProgress(), blueSeekBar.getProgress());
        colorView.setBackgroundColor(color);
    }


}
