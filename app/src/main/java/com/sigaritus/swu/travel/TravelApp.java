package com.sigaritus.swu.travel;

import android.app.Application;
import android.content.Context;

import com.avos.avoscloud.AVOSCloud;
import com.sigaritus.swu.travel.constants.Constants;

/**
 * Created by Administrator on 2016/1/15.
 */
public class TravelApp extends Application{

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        AVOSCloud.initialize(this, Constants.AppID,Constants.AppKey);
    }

    public static Context getContext() {
        return mContext;
    }
}
