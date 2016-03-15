package com.kodz.unjenkins.server.dto;

import com.kodz.unjenkins.server.endpoints.websocket.sockets.SubscriptionSocket;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kurt on 3/11/16.
 */
public class Subscription {
    SubscriptionSocket subscriptionSocket;
    ArrayList<JobStatus> jobs;

    public ArrayList<JobStatus> getJobs() {
        return jobs;
    }

    public void setJobs(ArrayList<JobStatus> jobs) {
        this.jobs = jobs;
    }

    public SubscriptionSocket getSubscriptionSocket() {
        return subscriptionSocket;
    }

    public void setSubscriptionSocket(SubscriptionSocket subscriptionSocket) {
        this.subscriptionSocket = subscriptionSocket;
    }
}
