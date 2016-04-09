package com.sigaritus.swu.travel.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sigaritus.swu.travel.R;
import com.sigaritus.swu.travel.ui.views.settings.SettingView;
import com.sigaritus.swu.travel.ui.views.settings.entity.SettingData;
import com.sigaritus.swu.travel.ui.views.settings.entity.SettingViewItemData;
import com.sigaritus.swu.travel.ui.views.settings.item.BasicItemViewH;

import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends AppCompatActivity {
    private SettingView mSettingView = null;
    private List<SettingViewItemData> mListData = new ArrayList<SettingViewItemData>();
    private SettingData mItemData = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mSettingView = (SettingView) findViewById(R.id.user_setting_view);
        initViews();
        mSettingView.setOnSettingViewItemClickListener(new SettingView.onSettingViewItemClickListener() {

            @Override
            public void onItemClick(int index) {
                // TODO Auto-generated method stub
                switch (index) {
                    case 0:
                        Intent intent = new Intent(SettingActivity.this,ResetPasswordActivity.class);
                        startActivity(intent);
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


    }

    private void initViews() {
        SettingViewItemData mItemViewData = new SettingViewItemData();
        mItemData = new SettingData();
        mItemData.setTitle("修改密码");


        mItemViewData.setData(mItemData);
        mItemViewData.setItemView(new BasicItemViewH(SettingActivity.this));
        mListData.add(mItemViewData);

        mItemViewData = new SettingViewItemData();
        mItemData = new SettingData();
        mItemData.setTitle("更换头像");

        mItemViewData.setData(mItemData);
        mItemViewData.setItemView(new BasicItemViewH(SettingActivity.this));
        mListData.add(mItemViewData);

        mItemViewData = new SettingViewItemData();
        mItemData = new SettingData();
        mItemData.setTitle("个人资料");


        mItemViewData.setData(mItemData);
        mItemViewData.setItemView(new BasicItemViewH(SettingActivity.this));
        mListData.add(mItemViewData);


        mSettingView.setAdapter(mListData);

        mListData =new ArrayList<SettingViewItemData>();
    }
}
