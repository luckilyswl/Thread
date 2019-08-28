package com.qingshangzuo.handlerpostactivity2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class MainActivity extends AppCompatActivity {

    private Button btnDown;
    private ImageView img;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnDown =  findViewById(R.id.btn_download);
        img =  findViewById(R.id.ivImage);
        dialog = new ProgressDialog(this);
        dialog.setTitle("提示信息");
        dialog.setMessage("正在下载中，请稍后...");
        dialog.setCancelable(false);
        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(mRunnable).start();
                dialog.show();
            }
        });
    }
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
// TODO Auto-generated method stub
            byte[] data = new HttpUtils().getData();
            Message msg = Message.obtain();
            msg.obj = data;
            msg.what = 0x001;
            handler.sendMessage(msg);
        }
    };
    private Handler handler = new Handler() {
        // 在Handler中获取消息，重写handleMessage()方法
        @Override
        public void handleMessage(Message msg) {
// 判断消息码是否为0x001
            if(msg.what == 0x001){
                byte[] data=(byte[])msg.obj;
                Bitmap bmp= BitmapFactory.decodeByteArray(data, 0, data.length);
                img.setImageBitmap(bmp);
                dialog.dismiss();
            }
        }
    };

    /**
     * 简单的封装一个http下载类
     * */
    public class HttpUtils {
        //指定一张图片
        private String URL_PATH = "http://img.wangxiao.cn/files/RemoteFiles/20140420/095185001.jpg";
        public HttpUtils(){
        }
        //返回一个byte类型的数组
        public byte[] getData(){
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(URL_PATH);
            HttpResponse httpResponse = null;
            try {
                httpResponse = httpClient.execute(httpGet);
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    byte[] data = EntityUtils.toByteArray(httpResponse
                            .getEntity());
                    return data;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}//end