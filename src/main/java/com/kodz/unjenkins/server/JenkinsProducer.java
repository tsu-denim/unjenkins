package com.kodz.unjenkins.server;

import com.kodz.unjenkins.client.JenkinsConsumer;
import com.kodz.unjenkins.client.dto.View;
import com.kodz.unjenkins.server.dto.BuildStatus;
import com.kodz.unjenkins.server.dto.JobStatus;
import com.kodz.unjenkins.server.dto.ViewQuery;
import com.kodz.unjenkins.client.dto.BuildDetail;
import com.kodz.unjenkins.client.dto.JobStats;
import com.kodz.unjenkins.server.dto.Metric;
import com.kodz.unjenkins.server.exceptions.ViewNotFound;

import javax.ws.rs.client.ResponseProcessingException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Kurt on 11/30/15.
 */
public class JenkinsProducer {

    private static ArrayList<Metric> cachedMetrics = new ArrayList<Metric>();
    private static Metric currentMetric = new Metric();
    private static long timeToLive = 10000L;

    public synchronized static Metric getMetric(ViewQuery viewQuery) throws ViewNotFound {
        int cachedCount = 0;
        for (Metric metric : cachedMetrics) {
            if ((metric.getViewQuery().getName().equals(viewQuery.getName()))
                    && (metric.getViewQuery().getRegexFilter().equals(viewQuery.getRegexFilter()))) {
                cachedCount++;
            }
        }
        if (cachedCount>0){
            //get from existing cache and update if necessary
            return getCachedMetric(viewQuery);
        }

        //View and filter combination not yet in cache, add to cache and return
        if (viewQuery.isSubView()){
            System.out.println("Cache item not found, adding " + (viewQuery.getName() + "." + viewQuery.getFolder()) + " to cache.");
        }
        else {
            System.out.println("Cache item not found, adding " + viewQuery.getName() + " to cache.");

        }
        Metric metric = getNewMetric(viewQuery);
        cachedMetrics.add(metric);
        return metric;
    }

    private synchronized static Metric getCachedMetric(ViewQuery viewQuery) throws ViewNotFound{

        for (int i = 0; i<cachedMetrics.size();i++ ){
            Metric metric = cachedMetrics.get(i);
           if ((metric.getViewQuery().getName().equals(viewQuery.getName()))
                    && (metric.getViewQuery().getRegexFilter().equals(viewQuery.getRegexFilter()))){
               if ((System.currentTimeMillis() - metric.getRefreshDate()) > timeToLive){
                   if (viewQuery.isSubView()){
                       System.out.println(viewQuery.getName() + "." + viewQuery.getFolder() + " cache stale, reloading.");
                   }
                   else {
                       System.out.println(viewQuery.getName() + " cache stale, reloading.");

                   }
                   //update list, set return value
                   try {
                       currentMetric = getNewMetric(viewQuery);
                       cachedMetrics.set(i, currentMetric);
                   } catch (ViewNotFound viewNotFound) {
                       viewNotFound.printStackTrace();
                   }
               }
               else{
                   //do not update list and set return value to existing cached value
                   currentMetric=cachedMetrics.get(i);
                   if (viewQuery.isSubView()){
                       System.out.println(viewQuery.getName() + "." + viewQuery.getFolder() + " dashboard found in cache within time to live.");
                   }
                   else {
                       System.out.println(viewQuery.getName() + " dashboard found in cache within time to live.");

                   }
               }
           }
        }

        return currentMetric;
    }

    private static Metric getNewMetric(ViewQuery viewQuery) throws ViewNotFound {

        Metric metric = new Metric();
        metric.setViewQuery(viewQuery);

        View view = getCurrentView(viewQuery);

        for (JobStats jobStats : getStatuses(view, viewQuery.getRegexFilter())) {JobStatus status = new JobStatus();
            status.setName(jobStats.getDisplayName());
            List<BuildStatus> buildStatusList = jobStats.getBuilds().stream().parallel().map(t -> {
                try {
                    BuildDetail buildDetail = JenkinsConsumer.jenkinsResource.getBuildDetail(jobStats.getDisplayName(),
                            t.getNumber(),URLEncoder.encode("actions[failCount,skipCount,totalCount],result[result],number[number],building[building],url[url]", "UTF-8"));
                    return new BuildStatus(buildDetail);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    throw new RuntimeException();
                }
            }).collect(Collectors.toList());
            status.setBuildStatusList(buildStatusList);
            Collections.sort(status.getBuildStatusList());
            metric.getJobStatusArrayList().add(status);} ;

        metric.setRefreshDate(System.currentTimeMillis());
        return metric;
    }

    private static View getCurrentView(ViewQuery viewQuery) throws ViewNotFound {
        View view;
        if (viewQuery.isSubView() == true){
            view =  JenkinsConsumer.jenkinsResource.getSubView(viewQuery.getName(), viewQuery.getFolder());
        }
        else {
            try{
                view =  JenkinsConsumer.jenkinsResource.getView(viewQuery.getName());
            }
            catch (ResponseProcessingException e){
            throw new ViewNotFound();
            }
        }
        return view;
    }

    private static ArrayList<JobStats> getStatuses(View view, String viewFilter){
        ArrayList<JobStats> jobStatuses = new ArrayList<JobStats>();
        view.getJobs().stream().filter(t -> t.getName().matches(viewFilter)).forEach(t -> {
            try {
                jobStatuses.add(JenkinsConsumer.jenkinsResource.getJob(t.getName(),
                        URLEncoder.encode("displayName[displayName],builds[number,url]{0,5}", "UTF-8")));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });
        return jobStatuses;
    }

}


