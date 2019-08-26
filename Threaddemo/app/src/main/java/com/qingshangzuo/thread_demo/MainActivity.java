package com.qingshangzuo.thread_demo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    TextView textView;
    ProgressBar progressBar;
    MyAsyncTask myAsyncTash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        progressBar = findViewById(R.id.progressBar);
    }


    static class MyAsyncTask extends AsyncTask<Integer,Integer,Integer>{
        WeakReference<MainActivity> weakReference;

        public MyAsyncTask(MainActivity activity){
            weakReference = new WeakReference<MainActivity>(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            MainActivity activity = weakReference.get();
            if(activity != null) activity.progressBar.setProgress(0);
            Log.d(TAG,"onPreExecute");
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            int sum = 0;
            for(int i = 1;i < 100;i++){
                try{
                    Log.d(TAG,"doInBackground:" + Thread.currentThread().getName());
                    Thread.sleep(100);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                sum += i;
                publishProgress(i);
                if(isCancelled()) break;
            }
            return -1;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.d(TAG,"onProgressUpdate:");
            MainActivity activity = weakReference.get();
            if(activity != null){
                activity.textView.setText("progress" + values[0]);
                activity.progressBar.setProgress(values[0]);
            }
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            Log.d(TAG,"onPostExecute");
            MainActivity activity = weakReference.get();
            if(activity != null){
                activity.textView.setText("congratulation !!!  finished");
                activity.progressBar.setProgress(0);
            }
        }
    }

    public void calculate(View v) {

        myAsyncTash = new MyAsyncTask(this);
        myAsyncTash.execute(0);
    }


    protected void stop() {
        myAsyncTash.cancel(true);
    }
}
