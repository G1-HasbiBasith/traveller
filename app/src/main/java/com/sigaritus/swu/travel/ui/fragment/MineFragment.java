package com.sigaritus.swu.travel.ui.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.sigaritus.swu.travel.R;
import com.sigaritus.swu.travel.ui.views.settings.SettingView;
import com.sigaritus.swu.travel.ui.views.settings.entity.SettingData;
import com.sigaritus.swu.travel.ui.views.settings.entity.SettingViewItemData;
import com.sigaritus.swu.travel.ui.views.settings.item.BasicItemViewH;
import com.sigaritus.swu.travel.util.ToastUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MineFragment extends BaseFragment {
    private SettingView mSettingView = null;
    private Button logout= null;
    private TextView username= null;
    private SettingData mItemData = null;
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

        AVUser currentUser = AVUser.getCurrentUser();

        View view = inflater.inflate(R.layout.fragment_mine, container, false);

        AppCompatActivity ac =(AppCompatActivity)getActivity();
        ac.getSupportActionBar().hide();

        mSettingView = (SettingView) view.findViewById(R.id.main_setting_view);

        username = (TextView)view.findViewById(R.id.username);

        username.setText(currentUser.getUsername());

        logout = (Button)view.findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AVUser.logOut();             //清除缓存用户对象
                ToastUtils.showShort("已注销用户");
            }
        });



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
        SettingViewItemData mItemViewData = new SettingViewItemData();
        mItemData = new SettingData();
        mItemData.setTitle("消息");
        mItemData.setDrawable(getActivity().getResources().getDrawable(R.drawable.ic_email_black_36dp));

        mItemViewData.setData(mItemData);
        mItemViewData.setItemView(new BasicItemViewH(getActivity()));
        mListData.add(mItemViewData);

        mItemViewData = new SettingViewItemData();
        mItemData = new SettingData();
        mItemData.setTitle("足迹");
        mItemData.setDrawable(getActivity().getResources().getDrawable(R.drawable.ic_bookmark_border_black_36dp));

        mItemViewData.setData(mItemData);
        mItemViewData.setItemView(new BasicItemViewH(getActivity()));
        mListData.add(mItemViewData);

        mItemViewData = new SettingViewItemData();
        mItemData = new SettingData();
        mItemData.setTitle("游记");
        mItemData.setDrawable(getActivity().getResources().getDrawable(R.drawable.ic_description_black_36dp));


        mItemViewData.setData(mItemData);
        mItemViewData.setItemView(new BasicItemViewH(getActivity()));
        mListData.add(mItemViewData);

        mItemViewData = new SettingViewItemData();
        mItemData = new SettingData();
        mItemData.setTitle("收藏");
        mItemData.setDrawable(getActivity().getResources().getDrawable(R.drawable.ic_grade_black_36dp));

        mItemViewData.setData(mItemData);
        mItemViewData.setItemView(new BasicItemViewH(getActivity()));
        mListData.add(mItemViewData);

        mItemViewData = new SettingViewItemData();
        mItemData = new SettingData();
        mItemData.setTitle("分享");
        mItemData.setDrawable(getActivity().getResources().getDrawable(R.drawable.ic_share_black_36dp));


        mItemViewData.setData(mItemData);
        mItemViewData.setItemView(new BasicItemViewH(getActivity()));
        mListData.add(mItemViewData);

        mItemViewData = new SettingViewItemData();
        mItemData = new SettingData();
        mItemData.setTitle("设置");
        mItemData.setDrawable(getActivity().getResources().getDrawable(R.drawable.ic_dashboard_white_36dp));


        mItemViewData.setData(mItemData);
        mItemViewData.setItemView(new BasicItemViewH(getActivity()));
        mListData.add(mItemViewData);

        mSettingView.setAdapter(mListData);

        mListData =new ArrayList<SettingViewItemData>();
    }

    @Override
    public void onStop() {
        AppCompatActivity ac =(AppCompatActivity)getActivity();
        ac.getSupportActionBar().show();
        super.onStop();
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
