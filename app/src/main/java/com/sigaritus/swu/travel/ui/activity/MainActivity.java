package com.sigaritus.swu.travel.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.SaveCallback;
import com.sigaritus.swu.travel.R;
import com.sigaritus.swu.travel.ui.fragment.DiaryFragment;
import com.sigaritus.swu.travel.ui.fragment.BaseFragment;
import com.sigaritus.swu.travel.ui.fragment.DestinationFragment;
import com.sigaritus.swu.travel.ui.fragment.MateFragment;
import com.sigaritus.swu.travel.ui.fragment.MineFragment;
import com.sigaritus.swu.travel.ui.fragment.RecommandFragment;


public class MainActivity extends AppCompatActivity {

    private FragmentTabHost fragmentTabHost;
    private ShareActionProvider mShareActionProvider;
    String[] tabs;
    private int[] drawables = {R.drawable.tab_explore_btn, R.drawable.tab_mate_btn, R.drawable.tab_add_btn,
            R.drawable.tab_destination_btn, R.drawable.tab_my_btn};
    private Class[] fragments = {RecommandFragment.class, MateFragment.class, DiaryFragment.class, DestinationFragment.class, MineFragment.class};
    public static ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabs = MainActivity.this.getResources().getStringArray(R.array.tabs);
        setPush();
        initViews();
    }

    private void setPush(){
        AVInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
            public void done(AVException e) {
                if (e == null) {
                    // 保存成功
                    String installationId = AVInstallation.getCurrentInstallation().getInstallationId();

                } else {
                    // 保存失败，输出错误信息
                }
            }
        });
        PushService.setDefaultPushCallback(this, PushActivity.class);

        PushService.subscribe(this, "public", PushActivity.class);
        PushService.subscribe(this, "private", PushActivity.class);
        PushService.subscribe(this, "protected", PushActivity.class);
    }

    private void initViews() {


        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        actionBar = this.getSupportActionBar();

        fragmentTabHost = (FragmentTabHost) findViewById(R.id.tab_host);
        fragmentTabHost.setup(this, getSupportFragmentManager(), R.id.real_tab_content);

        for (int i = 0; i < tabs.length; i++) {

            fragmentTabHost.addTab(fragmentTabHost.newTabSpec(tabs[i]).setIndicator(getTabview(i)),
                    fragments[i], null);
        }


    }

    private View getTabview(int index) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.tab_item_view, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.tabimageview);

        imageView.setBackgroundResource(drawables[index]);

        return view;
    }

    public void replaceFragment(int containerId, BaseFragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(containerId, fragment).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem shareItem = menu.findItem(R.id.action_share);
        mShareActionProvider =
                (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        getApplicationContext().deleteFile(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);

        mShareActionProvider.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);

        setShareIntent();

        // Associate searchable configuration with the SearchView

        return true;
    }

    private void setShareIntent() {
        if (mShareActionProvider != null) {
            // mShareActionProvider.setShareHistoryFileName(null);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "SUBJECT");
            intent.putExtra(Intent.EXTRA_TEXT, "traveller share test--");
            mShareActionProvider.setShareIntent(intent);
            //invalidateOptionsMenu();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.search:
                intent = new Intent(this,SearchActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_add_post:
               intent = new Intent(this,SendActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_add_tour:
                intent = new Intent(this,AddScheduleActivity.class);
                startActivity(intent);
                return true;
        }


        return super.onOptionsItemSelected(item);
    }
}