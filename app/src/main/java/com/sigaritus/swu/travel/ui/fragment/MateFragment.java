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

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.sigaritus.swu.travel.R;
import com.sigaritus.swu.travel.ui.fragment.adapter.TourListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;


public class MateFragment extends BaseFragment {

    @Bind(R.id.tour_list)
    RecyclerView tourlist;
    @Bind(R.id.schedule_ptr)
    PtrClassicFrameLayout ptr;
    private TourListAdapter adapter;
    public static MateFragment newInstance(String param1, String param2) {
        MateFragment fragment = new MateFragment();

        return fragment;
    }

    public MateFragment() {
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
        View view = inflater.inflate(R.layout.fragment_mate, container, false);
        ButterKnife.bind(this, view);


        tourlist.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TourListAdapter(getContext(),new ArrayList<AVObject>());
        tourlist.setAdapter(adapter);
        initData(false);
        ptr.setLastUpdateTimeRelateObject(this);
        ptr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                initData(true);
            }
        });


        return view;

    }

    private void initData(boolean isMore) {

        AVQuery<AVObject> scheduleQuery = new AVQuery<>("Schedule");
        scheduleQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                adapter.setTourList(list);

                ptr.refreshComplete();

            }
        });

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
