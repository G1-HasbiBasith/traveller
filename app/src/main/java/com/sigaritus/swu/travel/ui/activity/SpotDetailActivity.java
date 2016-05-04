package com.sigaritus.swu.travel.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sigaritus.swu.travel.R;
import com.sigaritus.swu.travel.constants.Constants;
import com.sigaritus.swu.travel.entity.Diary;
import com.sigaritus.swu.travel.entity.Spot;
import com.sigaritus.swu.travel.ui.activity.adapter.SpotListAdapter;
import com.sigaritus.swu.travel.ui.fragment.adapter.DiaryListAdapter;
import com.sigaritus.swu.travel.util.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SpotDetailActivity extends BaseActivity {

    @Bind(R.id.spot_list)
    RecyclerView spotList;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    SpotListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot_detail);
        ButterKnife.bind(this);

        Intent intent =getIntent();
        String spotName = intent.getBundleExtra("spot").getString("cityName");

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setTitle(spotName);

        spotList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SpotListAdapter(new ArrayList<Spot>(),getApplicationContext() );
        spotList.setAdapter(mAdapter);
        String requestUrl = String.format(Constants.DestinationSpot,spotName);
        executeRequest(new JsonObjectRequest(Request.Method.GET,requestUrl,null,responseListener(),errorListener()));

    }
    protected Response.Listener<JSONObject> responseListener(){
        return  new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status");

                    if (status.equals("1")){
                        JSONArray pois = response.getJSONArray("pois");
                        List<Spot> spotDataList = new ArrayList<Spot>();
                        Gson gson = new Gson();
                        for (int i = 0; i < pois.length(); i++) {
                            JSONObject poi = pois.getJSONObject(i);
                            poi.remove("biz_type");
                            Log.d("--i",poi.toString());
                            Spot spot = gson.fromJson(poi.toString(),Spot.class);
                            spotDataList.add(spot);
                        }
//
                        mAdapter.setSpotList(spotDataList);

                    }else{
                        ToastUtils.showLong(status);
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
    }

    protected Response.ErrorListener errorListener(){
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtils.showShort("error" + error.getMessage());
            }
        };
    }

}
