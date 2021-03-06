package com.sigaritus.swu.travel.ui.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.sigaritus.swu.travel.R;
import com.sigaritus.swu.travel.entity.Diary;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2016/4/7.
 */
public class DiaryListAdapter extends RecyclerView.Adapter<DiaryListAdapter.DiaryHolder> implements View.OnClickListener{

    private Context mContext;
    private List<Diary> diaryList;

    public DiaryListAdapter(Context mContext) {
        this.mContext = mContext;
    }
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;



    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , String data);
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener!=null){
            mOnItemClickListener.onItemClick(view, (String)view.getTag());
        }
    }

    public void setmOnItemClickListener(OnRecyclerViewItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public DiaryListAdapter(Context mContext, List<Diary> diaryList) {
        this.mContext = mContext;
        this.diaryList = diaryList;
    }

    @Override
    public DiaryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recom_item, parent, false);
        view.setOnClickListener(this);
        DiaryHolder holder = new DiaryHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(DiaryHolder holder, int position) {
        holder.username.setText(diaryList.get(position).getUserName());
        holder.title.setText(diaryList.get(position).getTitle());
        holder.time.setText(diaryList.get(position).getStartTime());
        Picasso.with(mContext).load(diaryList.get(position).getHeadImage())
                .placeholder(R.drawable.ic_default_image).into(holder.headImage);
        Picasso.with(mContext).load(diaryList.get(position).getUserHeadImg())
                .placeholder(R.drawable.ic_default_image).into(holder.userheadimg);

        holder.itemView.setTag(diaryList.get(position).getBookUrl());
    }

    @Override
    public int getItemCount() {
        return diaryList.size();
    }

    class DiaryHolder extends RecyclerView.ViewHolder {
        ImageView headImage;
        TextView title;
        CircleImageView userheadimg;
        TextView username;
        TextView time;

        public DiaryHolder(View itemView) {
            super(itemView);
            headImage = (ImageView) itemView.findViewById(R.id.headimg);
            title = (TextView) itemView.findViewById(R.id.diary_title);
            userheadimg = (CircleImageView) itemView.findViewById(R.id.userheadimg);
            username = (TextView) itemView.findViewById(R.id.r_username);
            time = (TextView) itemView.findViewById(R.id.time);
        }
    }
}
