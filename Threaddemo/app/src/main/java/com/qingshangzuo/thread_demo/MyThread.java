package com.qingshangzuo.thread_demo;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/**
 * Created by fallb on 2015/10/7.
 */
public class MyThread extends Thread {
    public static final int MSG_WORKER_THREAD = 100;
    private static final  String TAG = "MyThread";

    private Handler mWorkerHandler;
    private Handler mMainHandler;

    public MyThread(Handler handler) {
        mMainHandler = handler;
        mWorkerHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == MainActivity.MSG_MAIN){
                    Log.d(TAG,"Message:"+msg.obj);
                }
            }
        };
    }

    @Override
    public void run() {
        Looper.prepare();
        Message msg = mMainHandler.obtainMessage();
        msg.what = MyThread.MSG_WORKER_THREAD;
        msg.obj="子线程发出的消息";
        mMainHandler.sendMessage(msg);

        Looper.loop();
    }

    public Handler getWorkerHandler() {
        return mWorkerHandler;
    }
}