package com.sigaritus.swu.travel.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import com.sigaritus.swu.travel.R;
import com.sigaritus.swu.travel.TravelApp;
import com.sigaritus.swu.travel.ui.activity.SpotDetailActivity;
import com.sigaritus.swu.travel.ui.views.PinnedSectionListView;
import com.sigaritus.swu.travel.util.ToastUtils;

import java.util.Locale;

public class DestinationFragment extends BaseFragment implements View.OnClickListener{


    static class SimpleAdapter extends ArrayAdapter<Item> implements PinnedSectionListView.PinnedSectionListAdapter {

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
            Item item = getItem(position);
            if (item.type == Item.SECTION) {
                //view.setOnClickListener(PinnedSectionListActivity.this);
                view.setBackgroundColor(Color.argb(255,77,188,203));
                img.setVisibility(View.GONE);
            }else{
                img.setBackgroundResource(des_pic[position%4]);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), SpotDetailActivity.class);
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

    static class FastScrollAdapter extends SimpleAdapter implements SectionIndexer {

        private Item[] sections;

        public FastScrollAdapter(Context context, int resource, int textViewResourceId) {
            super(context, resource, textViewResourceId);
        }

        @Override protected void prepareSections( int sectionsNumber) {
            sections = new Item[sectionsNumber];
        }

        @Override protected void onSectionAdded(Item section, int sectionPosition) {
            sections[sectionPosition] = section;
        }

        @Override public Item[] getSections() {
            return sections;
        }

        @Override public int getPositionForSection(int section) {
            if (section >= sections.length) {
                section = sections.length - 1;
            }
            return sections[section].listPosition;
        }

        @Override public int getSectionForPosition(int position) {
            if (position >= getCount()) {
                position = getCount() - 1;
            }
            return getItem(position).sectionPosition;
        }

    }

    static class Item {

        public static final int ITEM = 0;
        public static final int SECTION = 1;

        public final int type;
        public final String text;

        public int sectionPosition;
        public int listPosition;

        public Item(int type, String text) {
            this.type = type;
            this.text = text;
        }

        @Override public String toString() {
            return text;
        }

    }


    @Override
    public void onClick(View view) {
        ToastUtils.showShort("item"+view.getTag());
    }

    // TODO: Rename and change types and number of parameters
    public static DestinationFragment newInstance() {
        DestinationFragment fragment = new DestinationFragment();

        return fragment;
    }

    public DestinationFragment() {
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
        View view = inflater.inflate(R.layout.fragment_destination, container, false);
        ListView list = (ListView)view.findViewById(R.id.destination_list);


        initializeHeaderAndFooter(list);
        initializeAdapter(list);
        initializePadding(list);
        return view;
    }
    private boolean hasHeaderAndFooter;
    private boolean isFastScroll;
    private boolean addPadding;
    private boolean isShadowVisible = true;
    private void initializePadding(ListView list) {
        float density = getResources().getDisplayMetrics().density;
        int padding = addPadding ? (int) (16 * density) : 0;
        list.setPadding(padding, padding, padding, padding);
    }

    private void initializeHeaderAndFooter(ListView list ){
        list.setAdapter(null);
        if (hasHeaderAndFooter) {
//            ListView list = getListView();

            LayoutInflater inflater = LayoutInflater.from(getActivity());
            TextView header1 = (TextView) inflater.inflate(android.R.layout.simple_list_item_1, list, false);
            header1.setText("First header");
            list.addHeaderView(header1);

            TextView header2 = (TextView) inflater.inflate(android.R.layout.simple_list_item_1, list, false);
            header2.setText("Second header");
            list.addHeaderView(header2);

            TextView footer = (TextView) inflater.inflate(android.R.layout.simple_list_item_1, list, false);
            footer.setText("Single foolter");
            list.addFooterView(footer);
        }
        initializeAdapter(list);
    }

    @SuppressLint("NewApi")
    private void initializeAdapter(ListView list) {
        list.setFastScrollEnabled(isFastScroll);
        if (isFastScroll) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                list.setFastScrollAlwaysVisible(true);
            }
            list.setAdapter(new FastScrollAdapter(getActivity(), R.layout.des_list_unit, R.id.des_text));
        } else {
            list.setAdapter(new SimpleAdapter(getActivity(), R.layout.des_list_unit, R.id.des_text));
        }
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
