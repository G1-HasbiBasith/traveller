package com.sigaritus.swu.travel.ui.activity.adapter;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mapapi.map.Text;
import com.joanzapata.iconify.widget.IconTextView;
import com.sigaritus.swu.travel.R;
import com.sigaritus.swu.travel.entity.Spot;

import java.util.List;

/**
 * Created by Administrator on 2016/4/27.
 */
public class SpotListAdapter extends RecyclerView.Adapter<SpotListAdapter.SpotItem> {

    Context mContext;
    List<Spot> spotList;

    public SpotListAdapter(List<Spot> spotList, Context mContext) {
        this.spotList = spotList;
        this.mContext = mContext;
    }

    @Override
    public SpotItem onCreateViewHolder(ViewGroup parent, int viewType) {

        return new SpotItem(LayoutInflater.from(mContext).inflate(R.layout.spot_list_item,
                parent,false));
    }

    @Override
    public void onBindViewHolder(SpotItem holder, int position) {
        Spot spot = spotList.get(position);
        holder.title.setText(spot.getName());
        holder.address.setText(spot.getCityname()+","
                +spot.getAdname()+","+spot.getAddress());
        holder.instruction.setText(spot.getType());
        holder.tel.setText(spot.getTel());


    }

    @Override
    public int getItemCount() {
        return spotList.size();
    }

    public List<Spot> getSpotList() {
        return spotList;
    }

    public void setSpotList(List<Spot> spotList) {
        this.spotList = spotList;
        notifyDataSetChanged();
    }

    class SpotItem extends RecyclerView.ViewHolder{

        TextView title;
        IconTextView star;
        TextView instruction;
        TextView address;
        TextView tel;
        public SpotItem(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.spot_title);
            star = (IconTextView) itemView.findViewById(R.id.add_star);
            instruction = (TextView) itemView.findViewById(R.id.instruction);
            address = (TextView) itemView.findViewById(R.id.spot_address);
            tel = (TextView) itemView.findViewById(R.id.tel);
        }
    }
}
