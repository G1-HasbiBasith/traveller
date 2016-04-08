package com.sigaritus.swu.travel.ui.fragment;

import android.support.v4.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sigaritus.swu.travel.dataprovider.HttpManager;
import com.sigaritus.swu.travel.util.ToastUtils;


public abstract class BaseFragment extends Fragment {

    @Override
    public void onDestroy() {
        super.onDestroy();
        //HttpManager.cancelAll(this);
    }

    protected void executeRequest(Request request) {
        HttpManager.addRequest(request, this);

    }

    protected Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtils.showLong(error.getMessage());
            }
        };
    }
}