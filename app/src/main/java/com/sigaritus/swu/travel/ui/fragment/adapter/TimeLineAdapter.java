package com.sigaritus.swu.travel.ui.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.ClickNineGridViewAdapter;
import com.sigaritus.swu.travel.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/26.
 */
public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.TimeLine> {

    private Context mContext;
    private List<AVObject> postlist;
    private ArrayList<ImageInfo> imageinfo = new ArrayList<>();

    public TimeLineAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public TimeLineAdapter(Context mContext, List<AVObject> postlist) {
        this.mContext = mContext;
        this.postlist = postlist;

    }

    @Override
    public TimeLine onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TimeLine(LayoutInflater.from(mContext).inflate(R.layout.timeline_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final TimeLine holder, int position) {
        holder.post_content.setText(postlist.get(position).getString("content"));
        holder.username.setText(postlist.get(position).getString("username"));
        holder.create_time.setText(new SimpleDateFormat("yyyy-mm-dd")
                .format(postlist.get(position).getDate("createdAt")));
        setImage(mContext, holder.user_avatar,
                postlist.get(position).getAVFile("avatar").getThumbnailUrl(false, 50, 50));

        holder.bind(position);

        AVQuery<AVObject> innerQuery = AVQuery.getQuery("Post");
        innerQuery.whereEqualTo("objectId", postlist.get(position).getObjectId());
        AVQuery<AVObject> imgquery = new AVQuery<AVObject>("Image");

        imgquery.whereMatchesQuery("post", innerQuery);

        imgquery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                for (AVObject image : list) {
                    AVFile file = image.getAVFile("file");
                    ImageInfo info = new ImageInfo();
                    info.setThumbnailUrl(file.getThumbnailUrl(false, 100, 100));
                    info.setBigImageUrl(file.getUrl());
                    imageinfo.add(info);
                }

                holder.nineGridView.setAdapter(new ClickNineGridViewAdapter(mContext, imageinfo));

            }
        });

    }

    @Override
    public int getItemCount() {
        return postlist.size();
    }

    private void setImage(Context context, ImageView imageView, String url) {
        Glide.with(context).load(url)//
                .placeholder(R.drawable.ic_default_image)//
                .error(R.drawable.ic_default_image)//
                .diskCacheStrategy(DiskCacheStrategy.ALL)//
                .into(imageView);
    }

    public class TimeLine extends RecyclerView.ViewHolder {
        ImageView user_avatar;
        TextView username;
        TextView post_content;
        TextView create_time;
        NineGridView nineGridView;
        private PopupWindow window;
        private PopupWindow editWindow;

        public TimeLine(View itemView) {
            super(itemView);
            user_avatar = (ImageView) itemView.findViewById(R.id.tv_avatar);
            username = (TextView) itemView.findViewById(R.id.tv_username);
            post_content = (TextView) itemView.findViewById(R.id.tv_content);
            create_time = (TextView) itemView.findViewById(R.id.tv_createTime);
            nineGridView = (NineGridView) itemView.findViewById(R.id.nineGrid);

        }

        public void bind(int position) {

        }

    }
}
