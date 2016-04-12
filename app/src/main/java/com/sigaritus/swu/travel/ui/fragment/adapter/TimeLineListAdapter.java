package com.sigaritus.swu.travel.ui.fragment.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
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
import com.sigaritus.swu.travel.util.GlobalDialog;
import com.sigaritus.swu.travel.util.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/4/12.
 */
public class TimeLineListAdapter extends BaseAdapter {

    private Context mContext;
    private List<AVObject> postlist;
    private List<List<ImageInfo>> imageinfos;
    private LayoutInflater mInflater;


    public TimeLineListAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public TimeLineListAdapter(Context mContext, List<AVObject> postlist) {
        this.mContext = mContext;
        this.postlist = postlist;
        mInflater = LayoutInflater.from(mContext);

    }

    @Override
    public int getCount() {
        return postlist.size();
    }

    @Override
    public Object getItem(int i) {
        return postlist.get(i);
    }


    @Override
    public long getItemId(int i) {
        return i;
    }

    public List<AVObject> getPostlist() {
        return postlist;
    }

    public void setPostlist(List<AVObject> postlist) {
        this.postlist = postlist;
        notifyDataSetChanged();
    }

    public List<List<ImageInfo>> getImageinfos() {
        return imageinfos;
    }

    public void setImageinfos(List<List<ImageInfo>> imageinfos) {
        this.imageinfos = imageinfos;
        Log.d("imageinfos",imageinfos.size()+"----");
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.timeline_item, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.post_content.setText(postlist.get(position).getString("content"));
        holder.username.setText(postlist.get(position).getString("username"));
        holder.create_time.setText(new SimpleDateFormat("yyyy-mm-dd")
                .format(postlist.get(position).getDate("createdAt")));
        setImage(mContext, holder.user_avatar,
                postlist.get(position).getAVFile("avatar").getThumbnailUrl(false, 50, 50));

        holder.nineGridView.setAdapter(new ClickNineGridViewAdapter(mContext, imageinfos.get(position)));

        return convertView;
    }

    private void setImage(Context context, ImageView imageView, String url) {
        Glide.with(context).load(url)//
                .placeholder(R.drawable.ic_default_image)//
                .error(R.drawable.ic_default_image)//
                .diskCacheStrategy(DiskCacheStrategy.ALL)//
                .into(imageView);
    }

    class ViewHolder implements View.OnClickListener{

        @Bind(R.id.tv_avatar)
        ImageView user_avatar;
        @Bind(R.id.tv_content)
        TextView post_content;
        @Bind(R.id.tv_username)
        TextView username;
        @Bind(R.id.tv_createTime)
        TextView create_time;
        @Bind(R.id.nineGrid)
        NineGridView nineGridView;
        private PopupWindow window;
        private PopupWindow editWindow;
        private View rootView;

        public ViewHolder(View convertView) {
            this.rootView = convertView;
            ButterKnife.bind(this,convertView);
        }


        @OnClick(R.id.more)
        public void more(View view) {
            View popupView = mInflater.inflate(R.layout.popup_reply, null);
            popupView.findViewById(R.id.favour).setOnClickListener(this);
            popupView.findViewById(R.id.comment).setOnClickListener(this);
            window = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setOutsideTouchable(true);
            window.setFocusable(true);
            window.setAnimationStyle(R.style.popup_more_anim);
            window.setBackgroundDrawable(new ColorDrawable(Color.BLACK));
            popupView.measure(0, 0);
            int xoff = -popupView.getMeasuredWidth();
            int yoff = -(popupView.getMeasuredHeight() + view.getHeight()) / 2;
            window.showAsDropDown(view, xoff, yoff);
        }

        @OnClick(R.id.delete)
        public void delete(View view) {
            final GlobalDialog delDialog = new GlobalDialog(mContext);
            delDialog.setCanceledOnTouchOutside(true);
            delDialog.getTitle().setText("提示");
            delDialog.getContent().setText("确定删除吗?");
            delDialog.setLeftBtnText("取消");
            delDialog.setRightBtnText("确定");
            delDialog.setLeftOnclick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.showShort("取消");
                    delDialog.dismiss();
                }
            });
            delDialog.setRightOnclick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.showShort("确定");
                    delDialog.dismiss();
                }
            });
            delDialog.show();
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.favour:
                    ToastUtils.showShort("赞+1");
                    if (window != null) window.dismiss();
                    break;
                case R.id.comment:
                    View editView = mInflater.inflate(R.layout.replay_input, null);
                    editWindow = new PopupWindow(editView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    editWindow.setOutsideTouchable(true);
                    editWindow.setFocusable(true);
                    editWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

                    EditText replyEdit = (EditText) editView.findViewById(R.id.reply);
                    replyEdit.setFocusable(true);
                    replyEdit.requestFocus();
                    // 以下两句不能颠倒
                    editWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
                    editWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    editWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
                    // 显示键盘
                    final InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

                    editWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            if (imm.isActive()) imm.toggleSoftInput(0, InputMethodManager.RESULT_SHOWN);
                        }
                    });
                    if (window != null) window.dismiss();
                    break;
            }
        }

    }



}
