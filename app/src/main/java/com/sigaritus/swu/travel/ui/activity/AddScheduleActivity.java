package com.sigaritus.swu.travel.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.sigaritus.swu.travel.R;
import com.sigaritus.swu.travel.constants.Constants;
import com.sigaritus.swu.travel.util.BitmapUtil;
import com.sigaritus.swu.travel.util.ToastUtils;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddScheduleActivity extends AppCompatActivity {
    @Bind(R.id.schedule_toolbar)
    Toolbar toolbar ;
    @Bind(R.id.schedule_title)
    EditText title;
    @Bind(R.id.cover_pic)
    ImageView cover;
    @Bind(R.id.duration)
    EditText duration;
    @Bind(R.id.num)
    EditText maxNum;
    @Bind(R.id.description)
    EditText description;
    @Bind(R.id.attention)
    EditText attention;
    @Bind(R.id.fees)
    EditText fees;
    @Bind(R.id.meet_place)
    EditText meetPlace;
    @Bind(R.id.contact)
    EditText contact;
    private String[] list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);


    }

    @OnClick(R.id.cover_pic)
    public void selectCover(){
        Intent intent = new Intent(this, PicActivity.class);
        startActivityForResult(intent, Constants.REQUEST_CODE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_schedule, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.action_save_schedule){
            if (duration.getText()==null||maxNum.getText()==null||description.getText()==null||
                    attention.getText()==null||fees.getText()==null||meetPlace.getText()==null
                    ||contact.getText()==null||title.getText()==null){
                ToastUtils.showLong("需要完整的信息");
            }else{
                AVObject schedule = new AVObject("Schedule");
                try {

                    AVUser user = AVUser.getCurrentUser();
                    schedule.put("title",title.getText());
                    schedule.put("username",user.getUsername());
                    schedule.put("duration",duration.getText());
                    schedule.put("maxNum",maxNum.getText());
                    schedule.put("description",description.getText());
                    schedule.put("attention",attention.getText());
                    schedule.put("fee",fees.getText());
                    schedule.put("meetPlace",meetPlace.getText());
                    schedule.put("contact",contact.getText());
                    schedule.put("avatar",user.getAVFile("avatar"));
                    AVFile cover = AVFile.withAbsoluteLocalPath("cover" + System.currentTimeMillis()+AVUser.getCurrentUser().getUsername() + ".jpg", list[0]);
                    schedule.put("cover",cover);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                schedule.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        ToastUtils.showLong("发送成功");
                        finish();
                    }
                });
            }
        }
        return true;

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (list != null) {
            if (list.length > 1) {
                ToastUtils.showLong("封面选一张就好了，蟹蟹");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Constants.RESULT_PIC) {
            if (requestCode == Constants.REQUEST_CODE) {
                Bundle b = data.getBundleExtra("pic");
                list = b.getStringArray("piclist");
                Log.d("size", list.length + "");
                resetCover();

            }
        }


    }

    private void resetCover() {
        Bitmap bmp = BitmapUtil.getSmallBitmap(list[0], 200, 200);
        cover.setImageBitmap(bmp);

    }

}
