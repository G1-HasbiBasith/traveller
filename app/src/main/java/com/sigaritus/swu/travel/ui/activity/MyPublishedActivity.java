package com.sigaritus.swu.travel.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.sigaritus.swu.travel.R;
import com.sigaritus.swu.travel.util.ToastUtils;

public class MyPublishedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_text);
        final EditText text = (EditText)findViewById(R.id.edit_text);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.action_save){

                    saveText(text.getText().toString());

                    ToastUtils.showShort("保存！");
                }
                return false;
            }
        });
    }

    private void saveText(String s) {

        AVObject post = new AVObject("Post");
        AVUser user = AVUser.getCurrentUser();
        post.put("username",user.getUsername());
        post.put("content",s);

        post.saveInBackground();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_send, menu);
        return true;
    }
}
