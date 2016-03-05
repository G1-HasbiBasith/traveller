package com.sigaritus.swu.travel.ui.views.pinnedList.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sigaritus.swu.travel.R;
import com.sigaritus.swu.travel.ui.activity.SpotDetailActivity;
import com.sigaritus.swu.travel.ui.views.pinnedList.PinnedSectionListView;

/**
 * Created by Administrator on 2016/3/5.
 */
public class SimpleAdapter extends ArrayAdapter<Item> implements PinnedSectionListView.PinnedSectionListAdapter {

    public SimpleAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);

        final int sectionsNumber = 6;
        final String[] sections = {"热门地点","欧洲国家","港澳台","美洲和大洋洲","亚洲国家","国内城市"};
        final String[][] spots ={{"日本","斯里兰卡","土耳其","韩国"},{"奥地利","西班牙","德国","法国"},
                {"香港","澳门","基隆","嘉义"},{"纽约","洛杉矶","悉尼","墨尔本"},
                {"阿拉伯联合酋长国","新加坡","马来西亚","泰国"},
                {"喀什","苏州","重庆","拉萨"}};

        prepareSections(sectionsNumber);

        int sectionPosition = 0, listPosition = 0;
        for (char i=0; i<sectionsNumber; i++) {
            Item section = new Item(Item.SECTION, sections[i]);
            section.sectionPosition = sectionPosition;
            section.listPosition = listPosition++;
            onSectionAdded(section, sectionPosition);
            add(section);

            final int itemsNumber = 4;
            for (int j=0;j<itemsNumber;j++) {
                Item item = new Item(Item.ITEM, spots[i][j]);
                item.sectionPosition = sectionPosition;
                item.listPosition = listPosition++;
                add(item);
            }

            sectionPosition++;
        }
    }

    protected void prepareSections(int sectionsNumber) { }
    protected void onSectionAdded(Item section, int sectionPosition) { }


    @Override public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout view = (LinearLayout) super.getView(position, convertView, parent);
//            view.setTextColor(Color.DKGRAY);
        final int[] des_pic ={R.drawable.des1,R.drawable.des2,R.drawable.des3,R.drawable.des4};
        TextView text = (TextView)view.getChildAt(0);
        ImageView img = (ImageView)view.getChildAt(1);

        view.setTag("" + position);
        final Item item = getItem(position);
        if (item.type == Item.SECTION) {
            //view.setOnClickListener(PinnedSectionListActivity.this);
            view.setBackgroundColor(Color.argb(255, 77, 188, 203));
            text.setTextColor(Color.WHITE);

        }else{
            img.setBackgroundResource(des_pic[position%4]);
            img.setVisibility(View.VISIBLE);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), SpotDetailActivity.class);
                    Bundle spot = new Bundle();
                    spot.putInt("spottype",item.type);
                    spot.putString("spotname",item.text);
                    intent.putExtra("spot",spot);
                    getContext().startActivity(intent);

                }
            });

        }
        return view;
    }

    @Override public int getViewTypeCount() {
        return 2;
    }

    @Override public int getItemViewType(int position) {
        return getItem(position).type;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == Item.SECTION;
    }

}
