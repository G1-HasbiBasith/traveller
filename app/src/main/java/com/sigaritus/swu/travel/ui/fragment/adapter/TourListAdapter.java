package com.sigaritus.swu.travel.ui.fragment.adapter;

import android.app.usage.UsageEvents;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.sigaritus.swu.travel.R;

import java.util.List;
import java.util.zip.Inflater;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2016/3/26.
 */
public class TourListAdapter extends RecyclerView.Adapter<TourListAdapter.ListViewHolder> {

    private Context mContext;
    private List<AVObject> eventlist;

    public TourListAdapter() {

    }

    public TourListAdapter(Context mContext) {

        this.mContext = mContext;
    }

    public TourListAdapter(List<AVObject> eventlist, Context mContext) {
        this.eventlist = eventlist;
        this.mContext = mContext;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ListViewHolder(LayoutInflater.
                from(mContext).inflate(R.layout.guide_list_item, parent, false));

    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        //set data
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView time;
        TextView place;
        TextView like;
        CircleImageView user_img;

        ListViewHolder(View itemView) {

            super(itemView);
            title = (TextView)itemView.findViewById(R.id.tour_title);
            time = (TextView)itemView.findViewById(R.id.pub_time);
            place = (TextView)itemView.findViewById(R.id.event_place);
            like = (TextView)itemView.findViewById(R.id.like_num);
            user_img = (CircleImageView)itemView.findViewById(R.id.profile_image);


        }
    }
}
