package cn.hawk.commonproject.services;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by kehaowei on 2017/8/15.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class SampleJobService extends JobService {

    public static int count = 0;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            JobParameters parameters = (JobParameters) msg.obj;
            Log.d("Hawk", "handleMessage: " + parameters.toString());
            Log.d("Hawk", "get bundle " + (null == parameters.getExtras() ? null : parameters.getExtras().get("from")));
            if (count < 10) {
                Log.d("Hawk", "handleMessage: job do in " + count);
                count++;
                jobFinished(parameters, true);
            } else {
                Log.d("Hawk", "handleMessage: job do in " + count);
                jobFinished(parameters, false);
            }
        }
    };

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d("Hawk", "onStartJob: " + params);
        Message message = new Message();
        message.what = 1;
        message.obj = params;
        handler.sendMessageDelayed(message, 2000);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d("Hawk", "onStopJob: " + params);
        return false;
    }
}
