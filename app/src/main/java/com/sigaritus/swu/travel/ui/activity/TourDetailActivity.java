package com.sigaritus.swu.travel.ui.activity;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.joanzapata.iconify.widget.IconTextView;
import com.sigaritus.swu.travel.R;
import com.sigaritus.swu.travel.util.ToastUtils;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class TourDetailActivity extends AppCompatActivity {

    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.cover_detail)
    ImageView cover;
    @Bind(R.id.user_image_detail)
    CircleImageView avatar;
    @Bind(R.id.username_detail)
    TextView username;
    @Bind(R.id.duration_detail)
    IconTextView duration;
    @Bind(R.id.maxnum_detail)
    IconTextView maxNum;
    @Bind(R.id.pubtime_detail)
    IconTextView pubtime;
    @Bind(R.id.description_detail)
    IconTextView description;
    @Bind(R.id.fees_detail)
    IconTextView fees;
    @Bind(R.id.attention_detail)
    IconTextView attention;
    @Bind(R.id.meet_place_detail)
    IconTextView meetPlace;
    @Bind(R.id.contact_detail)
    IconTextView contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_detail);
        ButterKnife.bind(this);
//
        AVObject data = getIntent().getParcelableExtra("tour");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCollapsingToolbarLayout.setTitle(data.getString("title"));

        Picasso.with(this).load(data.getAVFile("cover").getUrl())
                .placeholder(R.drawable.ic_default_image)
                .into(cover);
        Picasso.with(this).load(data.getAVFile("avatar").getUrl())
                .placeholder(R.drawable.ic_default_image)
                .into(avatar);
        username.setText(data.getString("username"));

        duration.setText("活动时长： " + data.getString("duration"));
        maxNum.setText("人数上限： " + data.getString("maxNum"));

        pubtime.setText(new SimpleDateFormat("yyyy-MM-dd")
                .format(data.getDate("createdAt")));
        description.setText(data.getString("description"));
        fees.setText(data.getString("fee"));
        attention.setText(data.getString("attention"));
        meetPlace.setText("{fa-map-marker} :"+data.getString("meetPlace"));
        contact.setText("{fa-phone} :"+data.getString("contact"));
    }
}
