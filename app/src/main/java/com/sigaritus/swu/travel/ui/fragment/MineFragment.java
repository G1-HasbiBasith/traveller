package com.sigaritus.swu.travel.ui.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sigaritus.swu.travel.R;
import com.sigaritus.swu.travel.ui.views.settings.SettingView;
import com.sigaritus.swu.travel.ui.views.settings.entity.SettingData;
import com.sigaritus.swu.travel.ui.views.settings.entity.SettingViewItemData;
import com.sigaritus.swu.travel.ui.views.settings.item.BasicItemViewH;

import java.util.ArrayList;
import java.util.List;

public class MineFragment extends BaseFragment {
    private SettingView mSettingView = null;

    private SettingData mItemData = null;
    private SettingViewItemData mItemViewData = null;
    private List<SettingViewItemData> mListData = new ArrayList<SettingViewItemData>();
    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    public MineFragment() {
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

        View view = inflater.inflate(R.layout.fragment_mine, container, false);

        mSettingView = (SettingView)view.findViewById(R.id.main_setting_view);

        initViews();

        mSettingView.setOnSettingViewItemClickListener(new SettingView.onSettingViewItemClickListener() {

            @Override
            public void onItemClick(int index) {
                // TODO Auto-generated method stub
                switch (index) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    default:
                        break;
                }
            }
        });

        return view;
    }

    private void initViews() {
        mItemViewData = new SettingViewItemData();
        mItemData = new SettingData();
        mItemData.setTitle("消息");

        mItemViewData.setData(mItemData);
        mItemViewData.setItemView(new BasicItemViewH(getActivity()));
        mListData.add(mItemViewData);

        mItemViewData = new SettingViewItemData();
        mItemData = new SettingData();
        mItemData.setTitle("足迹");

        mItemViewData.setData(mItemData);
        mItemViewData.setItemView(new BasicItemViewH(getActivity()));
        mListData.add(mItemViewData);

        mItemViewData = new SettingViewItemData();
        mItemData = new SettingData();
        mItemData.setTitle("收藏");

        mItemViewData.setData(mItemData);
        mItemViewData.setItemView(new BasicItemViewH(getActivity()));
        mListData.add(mItemViewData);

        mSettingView.setAdapter(mListData);
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
