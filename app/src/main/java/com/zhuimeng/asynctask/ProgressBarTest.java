package com.zhuimeng.asynctask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

/**
 * Created by 追梦 on 2017/4/27.
 **/

public class ProgressBarTest extends AppCompatActivity {
    private ProgressBar mProgressBar;
    private ProgressAsyncTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progressbar);

        mProgressBar = (ProgressBar) findViewById(R.id.pb_progressbar);

        mTask = new ProgressAsyncTask();
        mTask.execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mTask != null && mTask.getStatus() == AsyncTask.Status.RUNNING) {
            // cancel方法只是将对应的AsyncTask标记为cancel状态，并不是真正的取消线程的执行
            mTask.cancel(true);
        }
    }

    class ProgressAsyncTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            // 模拟进度更新
            for (int i = 0; i < 100; i++) {
                if (isCancelled()) {
                    break;
                }
                //to publish updates
                publishProgress(i);

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (isCancelled()) {
                return;
            }
            // 获取进度更新值
            mProgressBar.setProgress(values[0]);
        }

    }
}
