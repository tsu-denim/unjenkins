package com.kodz.unjenkins.server.dto;

import com.kodz.unjenkins.server.endpoints.websocket.sockets.SubscriptionSocket;

/**
 * Created by Kurt on 3/11/16.
 */
public class UpdateNotification {
    SubscriptionSocket subscriptionSocket;
    JobStatus jobStatus;

    public SubscriptionSocket getSubscriptionSocket() {
        return subscriptionSocket;
    }

    public void setSubscriptionSocket(SubscriptionSocket subscriptionSocket) {
        this.subscriptionSocket = subscriptionSocket;
    }

    public JobStatus getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(JobStatus jobStatus) {
        this.jobStatus = jobStatus;
    }
}
