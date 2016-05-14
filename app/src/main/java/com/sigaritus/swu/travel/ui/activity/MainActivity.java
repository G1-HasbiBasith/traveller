package com.sigaritus.swu.travel.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
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

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {


    private ShareActionProvider mShareActionProvider;

    @Bind(R.id.bottom_navigation)
    AHBottomNavigation bottomNavigation;

    public static ActionBar actionBar;
    private BaseFragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
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

        AHBottomNavigationItem recommand = new AHBottomNavigationItem(getString(R.string.tab1),R.drawable.recomm);
        AHBottomNavigationItem mate = new AHBottomNavigationItem(getString(R.string.tab2),R.drawable.mate);
        AHBottomNavigationItem footer = new AHBottomNavigationItem(getString(R.string.tab3),R.drawable.footer);
        AHBottomNavigationItem destination = new AHBottomNavigationItem(getString(R.string.tab4),R.drawable.destination);
        AHBottomNavigationItem mine = new AHBottomNavigationItem(getString(R.string.tab5),R.drawable.mine);

        bottomNavigation.addItem(recommand);
        bottomNavigation.addItem(mate);
        bottomNavigation.addItem(footer);
        bottomNavigation.addItem(destination);
        bottomNavigation.addItem(mine);

        bottomNavigation.setCurrentItem(0);
        bottomNavigation.setForceTitlesDisplay(true);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.fragment_container,RecommandFragment.newInstance()).commit();

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, boolean wasSelected) {

                switch (position){
                    case 0:
                        replaceFragment(R.id.fragment_container,RecommandFragment.newInstance());
                        break;
                    case 1:
                        replaceFragment(R.id.fragment_container,MateFragment.newInstance());
                        break;
                    case 2:
                        replaceFragment(R.id.fragment_container, DiaryFragment.newInstance());
                        break;
                    case 3:
                        replaceFragment(R.id.fragment_container,DestinationFragment.newInstance());
                        break;
                    case 4:
                        replaceFragment(R.id.fragment_container, MineFragment.newInstance());
                        break;
                }
            }
        });


    }


    public void replaceFragment(int containerId, BaseFragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(containerId, fragment).commit();

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