package com.sigaritus.swu.travel.ui.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sigaritus.swu.travel.R;
import com.sigaritus.swu.travel.entity.Diary;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2016/4/7.
 */
public class DiaryListAdapter extends RecyclerView.Adapter<DiaryListAdapter.DiaryHolder> {

    private Context mContext;
    private List<Diary> diaryList;

    public DiaryListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public DiaryListAdapter(Context mContext, List<Diary> diaryList) {
        this.mContext = mContext;
        this.diaryList = diaryList;
    }

    @Override
    public DiaryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DiaryHolder(LayoutInflater.from(mContext).inflate(R.layout.recom_item, parent, false));
    }

    @Override
    public void onBindViewHolder(DiaryHolder holder, int position) {
        holder.username.setText(diaryList.get(position).getUserName());
        holder.title.setText(diaryList.get(position).getTitle());
        holder.time.setText(diaryList.get(position).getStartTime());
        Picasso.with(mContext).load(diaryList.get(position).getHeadImage())
                .placeholder(R.drawable.ic_default_image).into(holder.headImage);
        Picasso.with(mContext).load(diaryList.get(position).getUserHeadImg())
                .placeholder(R.drawable.ic_default_image).into(holder.usrheadimg);

    }

    @Override
    public int getItemCount() {
        return diaryList.size();
    }

    class DiaryHolder extends RecyclerView.ViewHolder {
        ImageView headImage;
        TextView title;
        CircleImageView usrheadimg;
        TextView username;
        TextView time;

        public DiaryHolder(View itemView) {
            super(itemView);
            headImage = (ImageView) itemView.findViewById(R.id.headimg);
            title = (TextView) itemView.findViewById(R.id.diary_title);
            usrheadimg = (CircleImageView) itemView.findViewById(R.id.usrheadimg);
            username = (TextView) itemView.findViewById(R.id.username);
            time = (TextView) itemView.findViewById(R.id.time);
        }
    }
}
