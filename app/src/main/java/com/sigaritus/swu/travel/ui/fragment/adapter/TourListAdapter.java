package com.sigaritus.swu.travel.ui.fragment.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;

import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.sigaritus.swu.travel.R;
import com.sigaritus.swu.travel.ui.activity.TourDetailActivity;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.zip.Inflater;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2016/3/26.
 */
public class TourListAdapter extends RecyclerView.Adapter<TourListAdapter.ListViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<AVObject> tourList;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;



    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , AVObject data);
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener!=null){
            mOnItemClickListener.onItemClick(view, (AVObject)view.getTag());
        }
    }
    public TourListAdapter() {

    }

    public TourListAdapter(Context mContext) {

        this.mContext = mContext;
    }

    public TourListAdapter(Context mContext,List<AVObject> tourList) {
        this.tourList = tourList;
        this.mContext = mContext;
    }

    public List<AVObject> getTourList() {
        return tourList;
    }

    public void setTourList(List<AVObject> tourList) {
        this.tourList = tourList;
        notifyDataSetChanged();
    }

    public void setmOnItemClickListener(OnRecyclerViewItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =LayoutInflater.
                from(mContext).inflate(R.layout.guide_list_item, parent, false);
        view.setOnClickListener(this);
        ListViewHolder holder = new ListViewHolder(view);

        return holder;

    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        //set data

        holder.title.setText(tourList.get(position).getString("title"));
        holder.time.setText(new SimpleDateFormat("yyyy-MM-dd")
                .format(tourList.get(position).getDate("createdAt")));
        holder.place.setText("{fa-map-marker} "
                +tourList.get(position).getString("meetPlace"));
        holder.pub_user.setText(tourList.get(position).getString("username"));
        Picasso.with(mContext).load(tourList.get(position).getAVFile("cover").getUrl())
                .placeholder(R.drawable.ic_default_image).into(holder.cover);
        Picasso.with(mContext).load(tourList.get(position).getAVFile("avatar").getUrl())
                .placeholder(R.drawable.ic_default_image).into(holder.user_img);
//
        holder.itemView.setTag(tourList.get(position));
    }

    @Override
    public int getItemCount() {
        return tourList.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView cover;
        TextView title;
        TextView time;
        TextView place;
        TextView pub_user;
        CircleImageView user_img;


        ListViewHolder(View itemView) {

            super(itemView);
            cover = (ImageView) itemView.findViewById(R.id.tour_cover);
            title = (TextView)itemView.findViewById(R.id.tour_title);
            time = (TextView)itemView.findViewById(R.id.pub_time);
            place = (TextView)itemView.findViewById(R.id.event_place);
            pub_user = (TextView)itemView.findViewById(R.id.pub_name);
            user_img = (CircleImageView)itemView.findViewById(R.id.profile_image);



        }
    }
}
