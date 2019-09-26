package com.example.myapplication;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

public class WwdJobCreator implements JobCreator {

    @Override
    public Job create(String tag) {
        switch (tag) {
            case WwdJob.TAG:
                return new WwdJob();
            default:
                return null;
        }
    }
}