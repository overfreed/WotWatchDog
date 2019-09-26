package com.example.myapplication;

import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class WwdJob extends Job {

    public static final String TAG = "job_demo_tag";
    public static String log = "log";
    //public static boolean flagExecute;

    @Override
    @NonNull
    protected Result onRunJob(Params params) {
     //   if (flagExecute) {

       String currentDate=new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        log+=currentDate+" \n";
            scheduleJob();
     //   }
        return Result.SUCCESS;
    }


    public static void scheduleJob() {
        new JobRequest.Builder(WwdJob.TAG)
                .setExecutionWindow(30_000L, 40_000L)
                .build()
                .schedule();



    }

}
