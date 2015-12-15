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
import javax.ws.rs.client.SyncInvoker;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kurt on 11/30/15.
 */
public class JenkinsDataProvider {

    private static ArrayList<Metric> cachedMetrics = new ArrayList<Metric>();
    private static Metric currentMetric = new Metric();
    private static Metric staleMetric = new Metric();
    private static boolean isMetricStale = false;
    static long timeout = 10000L;


    public static Metric getMetric(ViewQuery viewQuery) throws ViewNotFound {
        if (cachedMetrics.stream()
                //only look at metrics matching view name and name filter
                .filter(t -> {
                    return ((t.getViewQuery().getName() == viewQuery.getName())
                            && (t.getViewQuery().getRegexFilter() == viewQuery.getRegexFilter()));
                }).count() > 0){
            //get from existing cache and update if necessary
            return getCachedMetric(viewQuery);
        }
        //View and filter combination not yet in cache, add to cache and return
        System.out.println("Cache item not found, adding " + viewQuery.getName() + " to cache.");
        Metric metric = getNewMetric(viewQuery);
        cachedMetrics.add(metric);
        return metric;
    }

    private static Metric getCachedMetric(ViewQuery viewQuery) throws ViewNotFound{

        for (int i = 0; i<cachedMetrics.size();i++ ){
            Metric metric = cachedMetrics.get(i);
           if ((metric.getViewQuery().getName() == viewQuery.getName())
                    && (metric.getViewQuery().getRegexFilter() == viewQuery.getRegexFilter())){
               if ((System.currentTimeMillis() - metric.getRefreshDate()) > timeout){
                   System.out.println(viewQuery.getName() + " not found, reloading cache");
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
                   System.out.println(viewQuery.getName() + " dashboard found in cache within timeout");
               }
           }
        }

        return currentMetric;
    }

    private static Metric getNewMetric(ViewQuery viewQuery) throws ViewNotFound {

        Metric metric = new Metric();
        metric.setViewQuery(viewQuery);

        View view = getCurrentView(viewQuery);

        getStatuses(view, viewQuery.getRegexFilter()).forEach(t -> {
            JobStatus status = new JobStatus();
            status.setName(t.getDisplayName());
            t.getBuilds().stream().parallel().forEach(m -> {
                try {
                    BuildDetail buildDetail = JenkinsConsumer.jenkinsResource.getBuildDetail(t.getDisplayName(), m.getNumber(),
                            URLEncoder.encode("actions[failCount,skipCount,totalCount],result[result],number[number],building[building],url[url]", "UTF-8"));
                    status.getBuildStatusList().add(new BuildStatus(buildDetail));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            });
            Collections.sort(status.getBuildStatusList());
            metric.getJobStatusArrayList().add(status);
        });
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


