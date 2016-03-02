package com.sigaritus.swu.travel.util;

import android.widget.Toast;

import com.sigaritus.swu.travel.TravelApp;

/**
 * Created by Administrator on 2015/10/26.
 */
public class ToastUtils {
    public static void showLong(String message){

        Toast.makeText(TravelApp.getContext(),message,Toast.LENGTH_LONG).show();
        
    }
    public static void showShort(String message){

        Toast.makeText(TravelApp.getContext(),message,Toast.LENGTH_SHORT).show();

    }
}
