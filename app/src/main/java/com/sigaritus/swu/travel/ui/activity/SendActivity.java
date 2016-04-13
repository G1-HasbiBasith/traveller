package com.sigaritus.swu.travel.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.miao.freesizedraggablelayout.DetailView;
import com.miao.freesizedraggablelayout.FreeSizeDraggableLayout;
import com.sigaritus.swu.travel.R;
import com.sigaritus.swu.travel.constants.Constants;
import com.sigaritus.swu.travel.dataprovider.UploadLatch;
import com.sigaritus.swu.travel.util.BitmapUtil;
import com.sigaritus.swu.travel.util.ToastUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SendActivity extends AppCompatActivity {


    @Bind(R.id.send_loc)
    ImageView loc;
    String[] list;

    @Bind(R.id.send_text)
    EditText send_text;
    @Bind(R.id.send_loading)
    AVLoadingIndicatorView loading;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.previewPics_layout)
    FreeSizeDraggableLayout previewPics;

    private SendHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        handler = new SendHandler();
        resize(1);
        setPreviewPicData(previewPics, list);

    }

    private void setPreviewPicData(FreeSizeDraggableLayout draggableLayout, String[] imglist) {
        List<DetailView> list = new ArrayList<>();
        list.add(new DetailView(new Point(0, 0), 1, 1, createAdd()));
        if (imglist != null&&imglist.length>0) {
            Log.d("imglist  ",imglist.length+"-----------");
            switch ((imglist.length+1) / 4) {
                case 0:
                    //pics less than 4 and 4 included in;
                    for (int i = 0; i < imglist.length; i++) {
                        list.add(new DetailView(new Point(i+1, 0), 1, 1, getImageView(imglist[i])));
                    }
                    break;
                case 1:
                    //pics more 4 less than 8;
                    for (int i = 0; i < imglist.length; i++) {
                        if (i < 3) {
                            list.add(new DetailView(new Point(i+1,0), 1, 1, getImageView(imglist[i])));
                        } else {
                            list.add(new DetailView(new Point(i-3, 1), 1, 1, getImageView(imglist[i])));
                        }
                    }
                    break;
                case 2:
                    for (int i = 0; i < imglist.length; i++) {
                        if (i <3) {
                            list.add(new DetailView(new Point(i+1, 0), 1, 1, getImageView(imglist[i])));
                        } else if (i <7) {
                            list.add(new DetailView(new Point(i-3, 1), 1, 1, getImageView(imglist[i])));
                        } else {
                            list.add(new DetailView(new Point(i-7, 2), 1, 1, getImageView(imglist[i])));
                        }
                    }
                    break;
            }
        }
        draggableLayout.setList(list);

    }

    private View createAdd() {
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setImageResource(R.drawable.ic_default_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SendActivity.this, PicActivity.class);
                startActivityForResult(intent, Constants.REQUEST_CODE);
            }
        });
        return imageView;
    }

    public ImageView getImageView(String url) {
        Bitmap bmp = BitmapUtil.getSmallBitmap(url, 70, 70);
        ImageView unit_img = new ImageView(getApplicationContext());
        unit_img.setImageBitmap(bmp);
        return unit_img;
    }

    public void resize(int row) {

        previewPics.setUnitWidthNum(4);
        previewPics.setUnitHeightNum(row);


    }

    class SendHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                stopAnim();
                ToastUtils.showShort("发送成功");
                finish();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_send, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                if (send_text.getText() == null || send_text.getText().length() == 0) {
                    ToastUtils.showShort("不能发送空内容");
                } else {

                    if (list.length > 0) {
                        startAnim();
                        new Thread() {
                            @Override
                            public void run() {
                                AVObject post = new AVObject("Post");
                                AVUser user = AVUser.getCurrentUser();
                                post.put("username", user.getUsername());
                                post.put("content", send_text.getText());
                                post.put("avatar", user.getAVFile("avatar"));
                                for (int i = 0; i < list.length; i++) {
                                    try {
                                        AVObject image = new AVObject("Image");
                                        AVFile img = AVFile.withAbsoluteLocalPath(user.getUsername() + System.currentTimeMillis() + i + "", list[i]);
                                        image.put("file", img);
                                        image.put("post", post);

                                        CountDownLatch latch = new CountDownLatch(1); //创建一个CountDownLatch对象用来阻塞这个for循环
                                        UploadLatch upload = new UploadLatch(image, latch, i + 1, list.length);  //下面有一个UploadLatch类继承自Thread
                                        upload.start();
                                        try {
                                            latch.await(); //阻塞for循环
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }

                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                }
                                handler.sendEmptyMessage(1);
                            }


                        }.start();


                    }
                }
        }
        return super.onOptionsItemSelected(item);
    }

    void startAnim() {
        Log.d("anim", "start");
        loading.setVisibility(View.VISIBLE);
    }

    void stopAnim() {
        Log.d("anim", "stop");
        loading.setVisibility(View.GONE);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (list != null) {
            if (list.length > 9) {
                ToastUtils.showLong("最多只能选择9张图片，蟹蟹。");
            }
            resize(((list.length+1) / 4)+1);
            setPreviewPicData(previewPics, list);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Constants.RESULT_PIC) {
            if (requestCode == Constants.REQUEST_CODE) {
                Bundle b = data.getBundleExtra("pic");
                list = b.getStringArray("piclist");

                Log.d("size", list.length + "");

            }
        }


    }
}
