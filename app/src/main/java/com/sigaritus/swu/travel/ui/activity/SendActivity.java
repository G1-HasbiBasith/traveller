package com.sigaritus.swu.travel.ui.activity;

import android.content.Intent;
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
import com.sigaritus.swu.travel.R;
import com.sigaritus.swu.travel.constants.Constants;
import com.sigaritus.swu.travel.dataprovider.UploadLatch;
import com.sigaritus.swu.travel.util.ToastUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.FileNotFoundException;
import java.util.concurrent.CountDownLatch;

public class SendActivity extends AppCompatActivity {

    private ImageView pic;
    private ImageView loc;
    private String[] list;
    private EditText send_text;
    private AVLoadingIndicatorView loading;
    private SendHandler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        pic = (ImageView) findViewById(R.id.send_pic);
        loc = (ImageView) findViewById(R.id.send_loc);

        send_text = (EditText) findViewById(R.id.send_text);

        loading = (AVLoadingIndicatorView)findViewById(R.id.send_loading);

        handler = new SendHandler();

        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SendActivity.this, PicActivity.class);
                startActivityForResult(intent, Constants.REQUEST_CODE);

            }
        });
        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    class SendHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==1){
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
                        new Thread(){
                            @Override
                            public void run() {
                                AVObject post = new AVObject("Post");
                                AVUser user = AVUser.getCurrentUser();
                                post.put("username", user.getUsername());
                                post.put("content", send_text.getText());

                                for (int i = 0; i < list.length; i++) {
                                    try {
                                        AVObject image = new AVObject("Image");
                                        AVFile img = AVFile.withAbsoluteLocalPath(user.getUsername() + System.currentTimeMillis() + i + "", list[i]);
                                        image.put("file",img);
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

    void startAnim(){
        Log.d("anim","start");
        loading.setVisibility(View.VISIBLE);
    }

    void stopAnim(){
        Log.d("anim","stop");
        loading.setVisibility(View.GONE);
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
