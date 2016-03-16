package com.sigaritus.swu.travel.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.sigaritus.swu.travel.R;
import com.sigaritus.swu.travel.ui.activity.SendTextActivity;
import com.sigaritus.swu.travel.ui.views.timeline.TimeLineAdapter;
import com.sigaritus.swu.travel.ui.views.timeline.TimeLineModel;

import java.util.ArrayList;
import java.util.List;

public class AddFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private RecyclerView mRecyclerView;

    private TimeLineAdapter mTimeLineAdapter;

    private List<TimeLineModel> mDataList = new ArrayList<>();

    public static AddFragment newInstance() {
        AddFragment fragment = new AddFragment();
        return fragment;
    }

    public AddFragment() {
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

        View view = inflater.inflate(R.layout.fragment_add, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        initView();

        ImageView send_text = (ImageView) view.findViewById(R.id.send_text);

        send_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SendTextActivity.class);
                getActivity().startActivity(intent);
            }
        });

        return view;
    }

    private void initView() {

        AVUser currentUser = AVUser.getCurrentUser();

        AVQuery<AVObject> query = new AVQuery<AVObject>("Post");
        query.whereEqualTo("username", currentUser.getUsername());

        query.findInBackground(new FindCallback<AVObject>() {
            public void done(List<AVObject> avObjects, AVException e) {
                if (e == null) {
                    Log.d("成功", "查询到" + avObjects.size() + " 条符合条件的数据");
                    for (int i = 0; i < avObjects.size(); i++) {

                        TimeLineModel model = new TimeLineModel();
                        model.setContent(avObjects.get(i).getString("content"));
                        model.setTime(avObjects.get(i).getDate("createdAt").toString());
                        mDataList.add(model);
                    }

                    mTimeLineAdapter = new TimeLineAdapter(mDataList);
                    mRecyclerView.setAdapter(mTimeLineAdapter);
                    mDataList  = new ArrayList<>();
                } else {
                    Log.d("失败", "查询错误: " + e.getMessage());
                }
            }
        });

    }

    // TODO: Rename method, update argument and hook method into UI event


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

}
