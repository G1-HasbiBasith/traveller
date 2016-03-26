package com.sigaritus.swu.travel.ui.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.sigaritus.swu.travel.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016/3/26.
 */
public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.TimeLine>{

    private Context mContext;
    private List<AVObject> postlist;


    public TimeLineAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public TimeLineAdapter(Context mContext, List<AVObject> postlist) {
        this.mContext = mContext;
        this.postlist = postlist;

    }

    @Override
    public TimeLine onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TimeLine(LayoutInflater.from(mContext).inflate(R.layout.timeline_item,parent,false));
    }

    @Override
    public void onBindViewHolder(final TimeLine holder, int position) {
        holder.post_content.setText(postlist.get(position).getString("content"));

        holder.bind(position);


    }

    @Override
    public int getItemCount() {
        return postlist.size();
    }

    public class TimeLine extends RecyclerView.ViewHolder{
        ImageView user_avatar ;
        NineGridImageView post_image;
        TextView post_content;
        NineGridImageViewAdapter mAdapter = new NineGridImageViewAdapter<AVObject>() {
            @Override
            protected void onDisplayImage(Context context, ImageView imageView, AVObject photo) {
                AVFile img = photo.getAVFile("file");
                Picasso.with(context)
                        .load(img.getThumbnailUrl(false,160,160))
                        .placeholder(R.drawable.ic_default_image)
                        .into(imageView);
            }

            @Override
            protected ImageView generateImageView(Context context) {
                return super.generateImageView(context);
            }

            @Override
            protected void onItemImageClick(Context context, int index, List<AVObject> photoList) {
//                showBigPicture(context, photoList.get(index).getBigUrl());
            }
        };
        public TimeLine(View itemView) {
            super(itemView);
            user_avatar = (ImageView)itemView.findViewById(R.id.user_avatar);
            post_image = (NineGridImageView)itemView.findViewById(R.id.post_img);
            post_content = (TextView)itemView.findViewById(R.id.post_content);
            post_image.setAdapter(mAdapter);
        }
        public void bind(int position){
            AVQuery<AVObject> innerQuery = AVQuery.getQuery("Post");
            innerQuery.whereEqualTo("objectId", postlist.get(position).getObjectId());
            AVQuery<AVObject> imgquery = new AVQuery<AVObject>("Image");

            imgquery.whereMatchesQuery("post",innerQuery);
            imgquery.findInBackground(new FindCallback<AVObject>() {
                @Override
                public void done(List<AVObject> list, AVException e) {
                    post_image.setImagesData(list);

                }
            });
        }

    }
}
