package com.sigaritus.swu.travel.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sigaritus.swu.travel.R;

import fr.tvbarthel.lib.blurdialogfragment.BlurDialogFragment;
import fr.tvbarthel.lib.blurdialogfragment.SupportBlurDialogFragment;


public class SaddFragment extends SupportBlurDialogFragment{


    public static SaddFragment newInstance(String param1, String param2) {
        SaddFragment fragment = new SaddFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public SaddFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    protected float getDownScaleFactor() {
        // Allow to customize the down scale factor.
        return 5.0f;
    }

    @Override
    protected int getBlurRadius() {
        // Allow to customize the blur radius factor.
        return 7;
    }

    @Override
    protected boolean isActionBarBlurred() {
        // Enable or disable the blur effect on the action bar.
        // Disabled by default.
        return true;
    }

    @Override
    protected boolean isDimmingEnable() {
        // Enable or disable the dimming effect.
        // Disabled by default.
        return true;
    }

    @Override
    protected boolean isRenderScriptEnable() {
        // Enable or disable the use of RenderScript for blurring effect
        // Disabled by default.
        return true;
    }

    @Override
    protected boolean isDebugEnable() {
        // Enable or disable debug mode.
        // False by default.
        return true;
    }

    //Default values are set to :

    /**
     * Since image is going to be blurred, we don't care about resolution.
     * Down scale factor to reduce blurring time and memory allocation.
     */
    static final float DEFAULT_BLUR_DOWN_SCALE_FACTOR = 4.0f;

    /**
     * Radius used to blur the background
     */
    static final int DEFAULT_BLUR_RADIUS = 8;

    /**
     * Default dimming policy.
     */
    static final boolean DEFAULT_DIMMING_POLICY = false;

    /**
     * Default debug policy.
     */
    static final boolean DEFAULT_DEBUG_POLICY = false;

    /**
     * Default action bar blurred policy.
     */
    static final boolean DEFAULT_ACTION_BAR_BLUR = false;

    /**
     * Default use of RenderScript.
     */
    static final boolean DEFAULT_USE_RENDERSCRIPT = false;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment, null,false);
        builder.setView(view);
        return builder.create();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }



}
