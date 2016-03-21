package com.sigaritus.swu.travel.ui.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.sigaritus.swu.travel.R;
import com.sigaritus.swu.travel.ui.fragment.AddFragment;
import com.sigaritus.swu.travel.ui.fragment.BaseFragment;
import com.sigaritus.swu.travel.ui.fragment.DestinationFragment;
import com.sigaritus.swu.travel.ui.fragment.MateFragment;
import com.sigaritus.swu.travel.ui.fragment.MineFragment;
import com.sigaritus.swu.travel.ui.fragment.RecommandFragment;
import com.sigaritus.swu.travel.ui.fragment.SaddFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentTabHost fragmentTabHost;
    private ShareActionProvider mShareActionProvider;
    String[] tabs;
    private int[] drawables = {R.drawable.tab_explore_btn, R.drawable.tab_mate_btn, R.drawable.tab_add_btn,
            R.drawable.tab_destination_btn, R.drawable.tab_my_btn};
    private Class[] fragments = {RecommandFragment.class, MateFragment.class, AddFragment.class, DestinationFragment.class, MineFragment.class};
    public static ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabs = MainActivity.this.getResources().getStringArray(R.array.tabs);
        initViews();
    }


    private void initViews() {

//        container = (FrameLayout)findViewById(R.id.fragment_container);
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

        switch (item.getItemId()){
            case R.id.search:
                Intent intent = new Intent(this,SearchActivity.class);
                startActivity(intent);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
}