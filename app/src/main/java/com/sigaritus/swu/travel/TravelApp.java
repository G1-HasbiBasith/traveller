package com.sigaritus.swu.travel;

import android.app.Application;
import android.content.Context;

import com.avos.avoscloud.AVOSCloud;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.IoniconsModule;
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
        Iconify.with(new IoniconsModule()).with(new FontAwesomeModule());
    }

    public static Context getContext() {
        return mContext;
    }
}
