package com.qingshangzuo.thread_demo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public static final int MSG_MAIN = 100;
    private static final String TAG = "MainActivity";

    private Button mStartThread;
    private Button mSendMessage;
    private Handler mHandler = new MyHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MyThread thread = new MyThread(mHandler);
        mStartThread = (Button) findViewById(R.id.startThread);
        mStartThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "开启线程");
                thread.start();
            }
        });
        mSendMessage = (Button) findViewById(R.id.send_message);
        mSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Handler workerHandler = thread.getWorkerHandler();
                Message msg = workerHandler.obtainMessage();
                msg.what = MSG_MAIN;
                msg.obj = "来自主线程的消息";
                workerHandler.sendMessage(msg);
            }
        });

    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MyThread.MSG_WORKER_THREAD) {
                Log.d(TAG,"Message:"+msg.obj);
            }
        }
    }
}