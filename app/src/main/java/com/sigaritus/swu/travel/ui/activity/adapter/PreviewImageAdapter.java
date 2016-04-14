package com.sigaritus.swu.travel.ui.activity.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sigaritus.swu.travel.R;
import com.sigaritus.swu.travel.util.BitmapUtil;
import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2016/4/14.
 */
public class PreviewImageAdapter extends RecyclerView.Adapter<PreviewImageAdapter.GridImage>{

    String[] img_list;
    Context mContext;
    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public PreviewImageAdapter(String[] img_list, Context mContext) {
        this.img_list = img_list;
        this.mContext = mContext;
    }

    public String[] getImg_list() {
        return img_list;
    }

    public void setImg_list(String[] img_list) {
        this.img_list = img_list;
        notifyDataSetChanged();
    }

    @Override
    public GridImage onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GridImage(LayoutInflater.from(mContext).inflate(R.layout.preview_gird,parent,false));
    }

    @Override
    public void onBindViewHolder(final GridImage holder,final int position) {

        if (mOnItemClickLitener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }

            if (img_list.length>0&&position<img_list.length){
                Log.d("-------",""+position);
//                Picasso.with(mContext).load("file://"+img_list[position]).resize(80, 80)
//                        .centerCrop().into(holder.image);
                Bitmap bmp = BitmapUtil.getSmallBitmap(img_list[position], 80, 80);
                holder.image.setImageBitmap(bmp);

            }else {
                Picasso.with(mContext).load(R.drawable.ic_default_image).into(holder.image);

            }



    }



    @Override
    public int getItemCount() {
        if (img_list==null){
            return 1;
        }else{
            return img_list.length==0?1:img_list.length+1;
        }

    }

    class GridImage extends RecyclerView.ViewHolder{
        ImageView image;
        public GridImage(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.pre_img);
        }
    }
}
