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

import com.sigaritus.swu.travel.R;
import com.sigaritus.swu.travel.ui.fragment.adapter.TourListAdapter;


public class MateFragment extends BaseFragment {

    private RecyclerView tourlist;
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

        tourlist = (RecyclerView)view.findViewById(R.id.tour_list);
        tourlist.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TourListAdapter(getContext());
        tourlist.setAdapter(adapter);

        return view;

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
