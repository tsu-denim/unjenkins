package com.kodz.unjenkins.server.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by Kurt on 11/23/15.
 */
public class Metric {
    private ArrayList<JobStatus> jobStatusArrayList = new ArrayList<JobStatus>();
    private ViewQuery viewQuery;
    private long refreshDate;

    @JsonProperty("jobs")
    public ArrayList<JobStatus> getJobStatusArrayList() {
        return jobStatusArrayList;
    }

    public void setJobStatusArrayList(ArrayList<JobStatus> jobStatusArrayList) {
        this.jobStatusArrayList = jobStatusArrayList;
    }

    @JsonIgnore
    public ViewQuery getViewQuery() {
        return viewQuery;
    }

    public void setViewQuery(ViewQuery viewQuery) {
        this.viewQuery = viewQuery;
    }

    @JsonIgnore
    public long getRefreshDate() {
        return refreshDate;
    }

    public void setRefreshDate(long refreshDate) {
        this.refreshDate = refreshDate;
    }
}
