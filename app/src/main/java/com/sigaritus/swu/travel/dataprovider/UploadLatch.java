package com.sigaritus.swu.travel.dataprovider;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;

import java.util.concurrent.CountDownLatch;

public class UploadLatch extends Thread {

    private AVObject file;
    private CountDownLatch latch;
    private int currentnum;
    private int totalnum;

    //构造函数
    //注意AVFile和CountDownLatch一定要传进来
    public UploadLatch(AVObject file, CountDownLatch latch, int a, int b) {
        this.file = file;
        this.latch = latch;
        this.currentnum = a;
        this.totalnum = b;
    }

    //执行Thread.start()时回自动调用这个方法
    public void run() {
        file.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                latch.countDown();
            }
        });
    }
}