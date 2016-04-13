package com.sigaritus.swu.travel.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.lzy.ninegrid.ImageInfo;
import com.sigaritus.swu.travel.R;

import com.sigaritus.swu.travel.ui.fragment.adapter.TimeLineAdapter;
import com.sigaritus.swu.travel.ui.fragment.adapter.TimeLineListAdapter;
import com.wang.avi.AVLoadingIndicatorView;



import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class DiaryFragment extends  BaseFragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //private RecyclerView mRecyclerView;
    @Bind(R.id.ptr)
    PtrClassicFrameLayout ptr;
    @Bind(R.id.diarylistView)
    ListView diarylistView;


    private TimeLineListAdapter mAdapter;

    private List<List<ImageInfo>> imageinfos = new ArrayList<List<ImageInfo>>();
    private SwipeRefreshLayout refreshLayout;

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

        View view = inflater.inflate(R.layout.fragment_add_, container, false);
        ButterKnife.bind(this,view);
//        loading = (AVLoadingIndicatorView)view.findViewById(R.id.avloadingIndicatorView);


//        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.diary_swipe_refresh_layout);
//
//        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                refreshContent();
//            }
//        });
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new TimeLineListAdapter(getContext(), new ArrayList<AVObject>());
        diarylistView.setAdapter(mAdapter);
//        initView();
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

//    private void refreshContent() {
//        initView();
//        refreshLayout.setRefreshing(false);
//    }

    private void initData(boolean isMore) {
        startAnim();

        final AVUser currentUser = AVUser.getCurrentUser();

        AVQuery<AVObject> query = new AVQuery<AVObject>("Post");

        query.setLimit(10);
        query.orderByDescending("createdAt");

        query.findInBackground(new FindCallback<AVObject>() {
            public void done(final List<AVObject> avObjects, AVException e) {
                if (e == null) {
                    Log.d("成功", "查询到" + avObjects.size() + " 条符合条件的数据");

//                    if (isMore) {
//                        data.addAll(0, evaluationBaseData.getData().getEvaluataions());
//                    } else {
//                        data = evaluationBaseData.getData().getEvaluataions();
//                    }
                    for (AVObject obj :avObjects) {
                        AVQuery<AVObject> innerQuery = AVQuery.getQuery("Post");
                        innerQuery.whereEqualTo("objectId", obj.getObjectId());

                        AVQuery<AVObject> imgquery = new AVQuery<AVObject>("Image");

                        imgquery.whereMatchesQuery("post", innerQuery);
                        final List<ImageInfo> imageinfo= new ArrayList<ImageInfo>();
                        imgquery.findInBackground(new FindCallback<AVObject>() {
                            @Override
                            public void done(List<AVObject> list, AVException e) {

                                for (AVObject image : list) {
                                    AVFile file = image.getAVFile("file");
                                    ImageInfo info = new ImageInfo();
                                    info.setThumbnailUrl(file.getThumbnailUrl(false, 100, 100));
                                    info.setBigImageUrl(file.getUrl());
                                    imageinfo.add(info);
                                }
                            }
                        });
                        imageinfos.add(imageinfo);
                        mAdapter.setImageinfos(imageinfos);
                        mAdapter.setPostlist(avObjects);

                        ptr.refreshComplete();
                        stopAnim();

                    }

                } else {
                    Log.d("失败", "查询错误: " + e.getMessage());
                }
            }
        });



    }


    void startAnim(){
        Log.d("anim","start");
//        loading.setVisibility(View.VISIBLE);
    }

    void stopAnim(){
        Log.d("anim","stop");
//        loading.setVisibility(View.GONE);
    }




}
