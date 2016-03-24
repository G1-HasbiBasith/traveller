package com.sigaritus.swu.travel.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVConstants;
import com.sigaritus.swu.travel.R;

public class PushActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        intent.putExtra(AVConstants.PUSH_INTENT_KEY, 1);
        AVAnalytics.trackAppOpened(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

}
