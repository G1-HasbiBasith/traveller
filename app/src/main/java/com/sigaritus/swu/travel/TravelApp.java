package com.sigaritus.swu.travel;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2016/1/15.
 */
public class TravelApp extends Application{

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

    }

    public static Context getContext() {
        return mContext;
    }
}
