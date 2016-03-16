package com.sigaritus.swu.travel.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.sigaritus.swu.travel.R;
import com.sigaritus.swu.travel.ui.views.pinnedList.adapter.FastScrollAdapter;
import com.sigaritus.swu.travel.ui.views.pinnedList.adapter.SimpleAdapter;
import com.sigaritus.swu.travel.util.ToastUtils;

import java.util.HashMap;

public class DestinationFragment extends BaseFragment implements View.OnClickListener {

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

    @Override
    public void onStop() {

        super.onStop();

    }


}
