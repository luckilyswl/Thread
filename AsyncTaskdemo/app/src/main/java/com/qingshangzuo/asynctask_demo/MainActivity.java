package com.qingshangzuo.asynctask_demo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private Button mStartButton;
    private Button mCancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = findViewById(R.id.progress);
        //设置progressBar暂时不可见，一会在异步任务中显示
        mProgressBar.setVisibility(View.GONE);

        final MyTask myTask = new MyTask();
        mStartButton = findViewById(R.id.start);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTask.execute();  // 只能在主线程中调用
            }
        });

        mCancelButton = findViewById(R.id.cancel);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTask.cancel(false);
            }
        });
    }

    private class MyTask extends AsyncTask<Void,Integer,String>{

        private static final String TAG = "MyTask";


        @Override
        protected void onPreExecute() {  // 预执行
            super.onPreExecute();
            //准备工作，显示进度条
            mProgressBar.setVisibility(View.VISIBLE);
            //打印日志，查看执行路径
            Log.d(TAG,"onPostExecute");
        }

        @Override
        protected String doInBackground(Void... voids) {  // 运行
            Log.d(TAG,"doInBackground");
            //每 1s 对进度条更新10%
            synchronized (this){
                for (int i = 1;i<=10;i++){
                    try{
                        Thread.sleep(1000*1);
                        publishProgress(i*10);
                        if(isCancelled()){
                            return "Task cancelled";
                        }
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
            return "Task executed";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {  // 进度更新
            super.onProgressUpdate(values);
            mProgressBar.setProgress(values[0]);
            Log.d(TAG,"onProgressUpdate");
        }

        @Override
        protected void onPostExecute(String s) {   //发布执行
            super.onPostExecute(s);
            Log.d(TAG,s);
            Log.d(TAG,"onPostExecute");
        }

        @Override
        protected void onCancelled(String s) {  //取消
            super.onCancelled(s);
            Log.d(TAG,s);
            Log.d(TAG,"onCancelled");
        }
    }
}
