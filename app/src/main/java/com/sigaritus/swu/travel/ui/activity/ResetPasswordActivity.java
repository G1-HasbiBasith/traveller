package com.sigaritus.swu.travel.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.UpdatePasswordCallback;
import com.sigaritus.swu.travel.R;
import com.sigaritus.swu.travel.util.ToastUtils;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText origin_pd;
    EditText new_pd;
    Button confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        origin_pd = (EditText) findViewById(R.id.original_password);
        new_pd = (EditText) findViewById(R.id.new_password);
        confirm = (Button) findViewById(R.id.confirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AVUser user = AVUser.getCurrentUser();
                user.updatePasswordInBackground(origin_pd.getText()+"", new_pd.getText()+"", new UpdatePasswordCallback() {
                    @Override
                    public void done(AVException e) {
                        ToastUtils.showLong("修改完成，请重新登录");
                    }
                });
            }
        });

    }

}
