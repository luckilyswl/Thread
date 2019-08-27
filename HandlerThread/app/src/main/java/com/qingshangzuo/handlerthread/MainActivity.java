package com.qingshangzuo.handlerthread;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static final int MSG_MAIN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MyThread thread = new MyThread();
        thread.start();
        thread.getLooper();

        final Button sendButton = findViewById(R.id.button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = thread.getHandler();
                Message msg = handler.obtainMessage();
                msg.what = MainActivity.MSG_MAIN;
                msg.obj = "testing HandlerThread";
                handler.sendMessage(msg);
                return;
            }
        });
    }
}
