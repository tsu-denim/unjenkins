package com.kodz.unjenkins.server.dto;

import java.util.ArrayList;

/**
 * Created by Kurt on 3/15/16.
 */
public class ServerEvent {
    ServerEventType serverEventType = null;
    JobStatus jobStatus = null;

    public JobStatus getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(JobStatus jobStatus) {
        this.jobStatus = jobStatus;
    }

    public ServerEventType getServerEventType() {
        return serverEventType;
    }

    public void setServerEventType(ServerEventType serverEventType) {
        this.serverEventType = serverEventType;
    }

}
