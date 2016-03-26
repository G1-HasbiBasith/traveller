package com.sigaritus.swu.travel.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.sigaritus.swu.travel.R;

import com.sigaritus.swu.travel.ui.fragment.adapter.TimeLineAdapter;
import com.wang.avi.AVLoadingIndicatorView;



import java.util.ArrayList;
import java.util.List;

public class DiaryFragment extends  BaseFragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private RecyclerView mRecyclerView;


    private AVLoadingIndicatorView loading;


    public static DiaryFragment newInstance() {
        DiaryFragment fragment = new DiaryFragment();
        return fragment;
    }

    public DiaryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add, container, false);
        loading = (AVLoadingIndicatorView)view.findViewById(R.id.avloadingIndicatorView);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        initView();

        return view;
    }

    private void initView() {
        startAnim();

        final AVUser currentUser = AVUser.getCurrentUser();

        AVQuery<AVObject> query = new AVQuery<AVObject>("Post");
        query.whereEqualTo("username", currentUser.getUsername());

        query.findInBackground(new FindCallback<AVObject>() {
            public void done(List<AVObject> avObjects, AVException e) {
                if (e == null) {
                    Log.d("成功", "查询到" + avObjects.size() + " 条符合条件的数据");
                    mRecyclerView.setAdapter(new TimeLineAdapter(getContext(),avObjects));

                    stopAnim();

                } else {
                    Log.d("失败", "查询错误: " + e.getMessage());
                }
            }
        });

    }


    void startAnim(){
        Log.d("anim","start");
        loading.setVisibility(View.VISIBLE);
    }

    void stopAnim(){
        Log.d("anim","stop");
        loading.setVisibility(View.GONE);
    }




}
