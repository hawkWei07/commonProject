package cn.hawk.commonproject.ui.activities;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Build;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.hawk.commonlib.base.BaseActivity;
import cn.hawk.commonproject.R;
import cn.hawk.commonproject.services.SampleJobService;

/**
 * Created by kehaowei on 2017/8/15.
 */

public class SchedulerActivity extends BaseActivity {

    @BindView(R.id.btn_start)
    Button btnStart;
    @BindView(R.id.btn_cancel)
    Button btnCancel;

    @Override
    protected int getContentId() {
        return R.layout.activity_scheduler;
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void startJobs() {
        Log.d("Hawk", "startJobs: start here");
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        JobInfo.Builder builder = new JobInfo.Builder(SampleJobService.count + 1, new ComponentName(this, SampleJobService.class));
        builder.setMinimumLatency(3000);
        builder.setOverrideDeadline(5000);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE);
        builder.setRequiresCharging(false);
        builder.setRequiresDeviceIdle(false);
        PersistableBundle bundle = new PersistableBundle();
        bundle.putString("from", SchedulerActivity.class.getSimpleName());
        builder.setExtras(bundle);
        if (scheduler.schedule(builder.build()) == JobScheduler.RESULT_FAILURE) {
            Log.d("Hawk", "startJobs: failed");
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void cancelJobs() {
        Log.d("Hawk", "cancelJobs: cancel here");
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        List<JobInfo> list = scheduler.getAllPendingJobs();
        for (JobInfo jobInfo : list) {
            Log.d("Hawk", "Job " + jobInfo.getId() + " pending");
        }
        scheduler.cancelAll();
        SampleJobService.count = 0;
    }

    @OnClick({R.id.btn_start, R.id.btn_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                startJobs();
                break;
            case R.id.btn_cancel:
                cancelJobs();
                break;
        }
    }
}
