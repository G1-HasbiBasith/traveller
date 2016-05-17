package com.sigaritus.swu.travel.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.sigaritus.swu.travel.R;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DiaryDetailActivity extends AppCompatActivity {

    @Bind(R.id.diary_webview)
    WebView diaryDetail;
    @Bind(R.id.diary_loading)
    AVLoadingIndicatorView diaryLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_diary_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String url = intent.getStringExtra("bookUrl");

        diaryDetail.getSettings().setJavaScriptEnabled(true);
        diaryDetail.getSettings().setSupportZoom(true);
        diaryDetail.setWebChromeClient(
                new WebChromeClient() {
                    public void onProgressChanged(WebView view, int progress) {
                        DiaryDetailActivity.this.setTitle("Loading...");
                        DiaryDetailActivity.this.setProgress(progress * 100);
                        startAnim();
                        if (progress == 100) {
                            DiaryDetailActivity.this.setTitle(R.string.app_name);
                            stopAnim();
                        }
                    }


                }
        );
        diaryDetail.setWebViewClient(new WebViewClient() {

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {                 // Handle the error

            }


            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        diaryDetail.loadUrl(url);

    }

    void startAnim() {
        Log.d("anim", "start");
        diaryLoading.setVisibility(View.VISIBLE);

    }

    void stopAnim() {
        Log.d("anim", "stop");
        diaryLoading.setVisibility(View.GONE);
    }
}
