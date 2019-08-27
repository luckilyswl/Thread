package com.qingshangzuo.handlerthread;

import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.util.logging.Handler;

public class MyThread extends HandlerThread {

    private static final String TAG = "MyThread";
    private Handler mHandler;

    public MyThread() {
        super(TAG);
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what == MainActivity.MSG_MAIN){
                    handlerRequest(msg.obj);
                }
            }
        };
        return;
    }

    private void handlerRequest(Object obj){
        Log.d(TAG, "handlerRequest:" + obj + ",thread:" + Thread.currentThread().getName());
        Looper looper = Looper.getMainLooper();
        Handler handler = new Handler(looper);
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"message is handled，thread："+Thread.currentThread().getName());
                return;
            }
        });
        return;
    }

    public Handler getHandler() {
        return mHandler;
    }
}