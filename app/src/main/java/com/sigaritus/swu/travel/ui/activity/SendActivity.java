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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.miao.freesizedraggablelayout.DetailView;
import com.miao.freesizedraggablelayout.FreeSizeDraggableLayout;
import com.sigaritus.swu.travel.R;
import com.sigaritus.swu.travel.constants.Constants;
import com.sigaritus.swu.travel.dataprovider.UploadLatch;
import com.sigaritus.swu.travel.ui.activity.adapter.PreviewImageAdapter;
import com.sigaritus.swu.travel.util.BitmapUtil;
import com.sigaritus.swu.travel.util.ToastUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SendActivity extends AppCompatActivity {


    @Bind(R.id.send_loc)
    ImageView loc;
    @Bind(R.id.place_label)
    TextView placeLabel;
    String[] list;
    @Bind(R.id.send_content_layout)
    ViewGroup layout;
    @Bind(R.id.send_text)
    EditText send_text;
    @Bind(R.id.send_loading)
    AVLoadingIndicatorView loading;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.imgpreview_grid)
    RecyclerView img_preview_grid;
    PreviewImageAdapter mAdapter;
    private SendHandler handler;

    public AMapLocationClient mLocationClient = null;

    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {

                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(amapLocation.getTime());
                    df.format(date);//定位时间
                    Log.d("amplocation--", amapLocation.getLocationType() +
                            amapLocation.getLatitude() +
                            amapLocation.getLongitude() + amapLocation.getAddress() + "---" +
                            amapLocation.getCountry() + amapLocation.getProvince() + amapLocation.getCity()
                            + amapLocation.getDistrict() +
                            amapLocation.getStreet());
                    placeLabel.setText(amapLocation.getAddress());
                    mLocationClient.stopLocation();
                    //amapLocation.getAOIName();//获取当前定位点的AOI信息
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_send);
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        handler = new SendHandler();
        mAdapter = new PreviewImageAdapter(new String[]{},getApplicationContext());
        mAdapter.setOnItemClickLitener(new PreviewImageAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(SendActivity.this, PicActivity.class);
                startActivityForResult(intent, Constants.REQUEST_CODE);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        img_preview_grid.setAdapter(mAdapter);

        img_preview_grid.setLayoutManager(new GridLayoutManager(this, 4));
    }

    private void initLocData() {
        AMapLocationClientOption mLocationOption = null;
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(1000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位

    }

    @OnClick(R.id.send_loc)
    public void getLoc() {
        mLocationClient.startLocation();

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
        layout.setVisibility(View.GONE);
    }

    void stopAnim() {
        Log.d("anim", "stop");
        loading.setVisibility(View.GONE);
        layout.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (list != null) {
            if (list.length > 9) {
                ToastUtils.showLong("最多只能选择9张图片，蟹蟹。");
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
                mAdapter.setImg_list(list);

            }
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.onDestroy();
    }
}
