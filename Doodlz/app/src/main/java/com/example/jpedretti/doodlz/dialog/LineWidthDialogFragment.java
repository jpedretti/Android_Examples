package com.example.jpedretti.doodlz.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.example.jpedretti.doodlz.DoodleView;
import com.example.jpedretti.doodlz.R;

/**
 Created by jpedretti on 05/12/2016.
 */

public class LineWidthDialogFragment extends BaseDialogFragment {

    private ImageView widthImageView;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());
        View lineWidthDialogView = getActivity().getLayoutInflater().inflate(R.layout.fragment_line_width, null);

        builder.setView(lineWidthDialogView);

        builder.setTitle(R.string.title_line_width_dialog);

        widthImageView = (ImageView) lineWidthDialogView.findViewById(R.id.width_image_view);

        final DoodleView doodleView = getDoodleFragment().getDoodleView();
        final SeekBar widthSeekBar = (SeekBar) lineWidthDialogView.findViewById(R.id.width_seek_bar);

        widthSeekBar.setOnSeekBarChangeListener(lineWidthChanged);
        widthSeekBar.setProgress(doodleView.getLineWidth());

        builder.setPositiveButton(R.string.button_set_line_width,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        doodleView.setLineWidth(widthSeekBar.getProgress());
                    }
                }
        );

        return builder.create();
    }

    private final SeekBar.OnSeekBarChangeListener lineWidthChanged = new SeekBar.OnSeekBarChangeListener() {

        final Bitmap bitmap = Bitmap.createBitmap(
                400, 100, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap); // draws into bitmap

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            Paint paint = new Paint();
            paint.setColor(getDoodleFragment().getDoodleView().getDrawingColor());
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeWidth(progress);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                bitmap.eraseColor(
                        getResources().getColor(android.R.color.transparent, getActivity().getTheme())
                );
            } else {
                bitmap.eraseColor(
                        getResources().getColor(android.R.color.transparent)
                );
            }
            canvas.drawLine(30, 50, 370, 50, paint);
            widthImageView.setImageBitmap(bitmap);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };
}
