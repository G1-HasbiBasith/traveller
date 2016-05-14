package com.sigaritus.swu.travel.ui.fragment.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.daimajia.slider.library.SliderLayout;
import com.sigaritus.swu.travel.R;
import com.sigaritus.swu.travel.entity.Diary;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by qibin on 2015/11/7.
 */
public class RecommendAdapter extends BaseRecyclerAdapter<Diary> {
    private ArrayList<AVObject> mHeaders = new ArrayList<>();

    public void setmHeaders(ArrayList<AVObject> mHeaders) {
        this.mHeaders = mHeaders;
    }

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View layout;
        if (getHeaderView() != null && viewType == TYPE_HEADER) {
            layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.recom_header, parent, false);
            return new ReconHeader(layout);
        }

        layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.recom_item, parent, false);
        return new ReconItem(layout);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, Diary data) {
        if (viewHolder instanceof ReconItem) {
            ((ReconItem)viewHolder).title.setText(data.getTitle());

        }
        if (viewHolder instanceof ReconHeader) {

        }
    }

    class ReconHeader extends BaseRecyclerAdapter.Holder {
        SliderLayout sliderLayout ;
        public ReconHeader(View itemView) {
            super(itemView);
            sliderLayout = (SliderLayout) itemView.findViewById(R.id.slider);
        }
    }

    class ReconItem extends BaseRecyclerAdapter.Holder {
        ImageView headImg;
        TextView title;
        CircleImageView userHeadImg;
        TextView username;
        TextView time;
        public ReconItem(View itemView) {
            super(itemView);
            headImg = (ImageView) itemView.findViewById(R.id.headimg);
            title = (TextView) itemView.findViewById(R.id.diary_title);
            userHeadImg = (CircleImageView) itemView.findViewById(R.id.userheadimg);
            username = (TextView) itemView.findViewById(R.id.r_username);
            time = (TextView) itemView.findViewById(R.id.time);

        }
    }
}
