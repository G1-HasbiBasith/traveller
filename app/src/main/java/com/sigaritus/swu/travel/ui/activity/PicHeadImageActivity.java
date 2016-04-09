package com.sigaritus.swu.travel.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.sigaritus.swu.travel.R;
import com.sigaritus.swu.travel.constants.Constants;
import com.sigaritus.swu.travel.util.ToastUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.FileNotFoundException;

public class PicHeadImageActivity extends AppCompatActivity {

    ImageView head  =null;
    Button choose_pic = null;
    Button upload = null;
    AVLoadingIndicatorView loading =null;
    private String[] list=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_head_image);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_pic);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        head = (ImageView) findViewById(R.id.head_image);
        choose_pic = (Button) findViewById(R.id.choose_pic);
        upload = (Button) findViewById(R.id.upload);
        loading = (AVLoadingIndicatorView) findViewById(R.id.pic_loading);
        choose_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(PicHeadImageActivity.this,PicActivity.class);
                startActivityForResult(intent, Constants.REQUEST_CODE);

            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AVUser user = AVUser.getCurrentUser();
                startAnim();


                try {
                    AVFile head_img_file = AVFile.withAbsoluteLocalPath(user.getUsername()+"",list[0]);
                    user.put("avatar",head_img_file);
                    user.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            ToastUtils.showLong("头像上传成功");
                            finish();
                        }
                    });
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }



            }
        });
    }

    void startAnim(){
        Log.d("anim","start");
        head.setVisibility(View.GONE);
        choose_pic.setVisibility(View.GONE);
        upload.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
    }

    void stopAnim(){
        Log.d("anim", "stop");
        loading.setVisibility(View.GONE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Constants.RESULT_PIC) {
            if (requestCode == Constants.REQUEST_CODE) {
                Bundle b = data.getBundleExtra("pic");
                list = b.getStringArray("piclist");
                if (list!=null){
                    Log.d("size", list[0] + "");

                    Bitmap bitmap = BitmapFactory.decodeFile(list[0]);
                    head.setImageBitmap(bitmap);
                }else{
                    ToastUtils.showLong("你他妈没选图片");
                }

            }
        }
    }
}
