package unjenkins.server.dto;

import unjenkins.server.dto.JobStatus;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Kurt on 11/23/15.
 */
public class Metric {
    private ArrayList<JobStatus> jobStatusArrayList = new ArrayList<JobStatus>();
    private ViewQuery viewQuery;
    private long refreshDate;


    public ArrayList<JobStatus> getJobStatusArrayList() {
        return jobStatusArrayList;
    }

    public void setJobStatusArrayList(ArrayList<JobStatus> jobStatusArrayList) {
        this.jobStatusArrayList = jobStatusArrayList;
    }

    public ViewQuery getViewQuery() {
        return viewQuery;
    }

    public void setViewQuery(ViewQuery viewQuery) {
        this.viewQuery = viewQuery;
    }

    public long getRefreshDate() {
        return refreshDate;
    }

    public void setRefreshDate(long refreshDate) {
        this.refreshDate = refreshDate;
    }
}
