package com.sigaritus.swu.travel.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sigaritus.swu.travel.TravelApp;
import com.sigaritus.swu.travel.entity.Weather;
import com.sigaritus.swu.travel.TravelApp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/7.
 */
public class HttpManager {

    private static RequestQueue mQueue= Volley.newRequestQueue(TravelApp.getContext());

    public HttpManager(Context mContext) {
        //

    }

    public static void addRequest(Request<?> request, Object tag) {
        if (tag != null) {
            request.setTag(tag);
        }
        mQueue.add(request);
    }

    public static void cancelAll(Object tag) {
        if (tag!=null) {
            mQueue.cancelAll(tag);
        }
    }

    public List<Weather> resObj(JSONObject resp){
        try {
            Boolean fail = resp.getBoolean("error");
            JSONArray results = resp.getJSONArray("results");
            Log.i("----array","---"+results);
            Gson gson = new Gson();
            ArrayList<Weather> list = new ArrayList<Weather>();
            list = gson.fromJson(results.toString(), new TypeToken<List<Weather>>() {
            }.getType());
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  null;
    }

}
