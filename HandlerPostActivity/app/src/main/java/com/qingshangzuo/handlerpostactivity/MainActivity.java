package com.qingshangzuo.handlerpostactivity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnMes1,btnMes2;
    private TextView tvMessage;

    //声明一个handler对象
    private static Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMes1 = findViewById(R.id.btnMes1);
        btnMes2 = findViewById(R.id.btnMes2);
        tvMessage = findViewById(R.id.tvMessage);

        btnMes1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //启动子线程
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                tvMessage.setText("使用Handler.post在工作线程中发送一段执行到消息队列中，在主线程中执行。");
                            }
                        });
                    }
                }).start();
            }
        });

        btnMes2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                tvMessage.setText("使用Handler.postDelayed在工作线程中发送一段执行到消息队列中，在主线程中延迟3s执行。");
                            }
                        },3000);
                    }
                }).start();
            }
        });
    }
}
