package com.example.jpedretti.doodlz.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.example.jpedretti.doodlz.R;

/**
 Created by jpedretti on 05/12/2016.
 */

public class EraseImageDialogFragment extends BaseDialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());

        builder.setMessage(R.string.message_erase);

        builder.setPositiveButton(R.string.button_erase,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getDoodleFragment().getDoodleView().clear();
                    }
                }
        );

        builder.setNegativeButton(android.R.string.cancel, null);

        return builder.create();
    }
}
