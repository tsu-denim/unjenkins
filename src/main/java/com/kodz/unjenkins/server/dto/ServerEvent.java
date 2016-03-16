package com.kodz.unjenkins.server.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by Kurt on 3/15/16.
 */
public class ServerEvent {
    ServerEventType serverEventType = null;
    ArrayList<JobStatus> jobStatus = new ArrayList<>();
    @JsonProperty("jobs")
    public ArrayList<JobStatus> getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(ArrayList<JobStatus> jobStatus) {
        this.jobStatus = jobStatus;
    }

    @JsonProperty("serverEvent")
    public ServerEventType getServerEventType() {
        return serverEventType;
    }

    public void setServerEventType(ServerEventType serverEventType) {
        this.serverEventType = serverEventType;
    }

}
