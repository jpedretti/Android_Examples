package com.example.jpedretti.doodlz.dialog;

import android.content.Context;
import android.support.v4.app.DialogFragment;

import com.example.jpedretti.doodlz.MainActivityFragment;
import com.example.jpedretti.doodlz.R;

/**
 Created by jpedretti on 13/12/2016.
 */

public abstract class BaseDialogFragment extends DialogFragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setDialogOnScreen(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        setDialogOnScreen(false);
    }

    protected MainActivityFragment getDoodleFragment() {
        return (MainActivityFragment) getFragmentManager().findFragmentById(R.id.fragment);
    }

    private void setDialogOnScreen(boolean a) {
        MainActivityFragment fragment = getDoodleFragment();

        if(fragment != null) {
            fragment.setDialogOnScreen(a);
        }
    }
}
