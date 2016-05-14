package com.sigaritus.swu.travel.ui.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sigaritus.swu.travel.R;
import com.sigaritus.swu.travel.constants.Constants;
import com.sigaritus.swu.travel.entity.Diary;
import com.sigaritus.swu.travel.ui.fragment.adapter.DiaryListAdapter;
import com.sigaritus.swu.travel.ui.fragment.adapter.FullyLinearLayoutManager;
import com.sigaritus.swu.travel.util.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommandFragment extends BaseFragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private SliderLayout mSlider;
    private RecyclerView diarylist;
    private List<Diary> datalist;
    private DiaryListAdapter adapter;
    private RecyclerViewHeader header;

    // TODO: Rename and change types and number of parameters
    public static RecommandFragment newInstance() {
        RecommandFragment fragment = new RecommandFragment();

        return fragment;
    }

    public RecommandFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_recommand, container, false);

        mSlider = (SliderLayout) view.findViewById(R.id.slider);
        diarylist = (RecyclerView) view.findViewById(R.id.travel_diary);
        header = (RecyclerViewHeader) view.findViewById(R.id.recon_header);

        diarylist.setLayoutManager(new LinearLayoutManager(getContext()));

        header.attachTo(diarylist);

        executeRequest(new JsonObjectRequest(Request.Method.GET, Constants.qunar_travelDiary, null,
                responseListener(), errorListener()) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("apikey", Constants.BaiduApiKey);
                // MyLog.d(TAG, "headers=" + headers);
                return headers;
            }
        });


        initializeSlider(mSlider);
        return view;
    }


    protected Response.Listener<JSONObject> responseListener() {
        return new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    Boolean fail = response.getBoolean("ret");
                    JSONObject data = response.getJSONObject("data");
                    JSONArray results = data.getJSONArray("books");

                    Gson gson = new Gson();
                    datalist = new ArrayList<>();
                    datalist = gson.fromJson(results.toString(), new TypeToken<List<Diary>>() {
                    }.getType());
                    adapter = new DiaryListAdapter(getActivity(), datalist);
                    diarylist.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
    }

    protected Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtils.showShort("error" + error.getMessage());
            }
        };
    }


    private void initializeSlider(final SliderLayout mSlider) {

        AVQuery<AVObject> bannerQuery = new AVQuery<>("Recommend");
        bannerQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                for (AVObject banner : list) {
                    TextSliderView textSliderView = new TextSliderView(getActivity());
                    // initialize a SliderLayout
                    textSliderView
                            .description(banner.getString("title"))
                            .image(banner.getAVFile("cover").getThumbnailUrl(false, 300, 300))
                            .setScaleType(BaseSliderView.ScaleType.Fit)
                            .setOnSliderClickListener(RecommandFragment.this);

                    //add your extra information
                    textSliderView.bundle(new Bundle());
                    textSliderView.getBundle()
                            .putString("extra", banner.getString("title"));

                    mSlider.addSlider(textSliderView);
                }
            }
        });

        mSlider.setPresetTransformer(SliderLayout.Transformer.DepthPage);
        mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.setDuration(4000);
        mSlider.addOnPageChangeListener(this);



    }



    // TODO: Rename method, update argument and hook method into UI event


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onStop() {
        mSlider.stopAutoCycle();
        super.onStop();

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
