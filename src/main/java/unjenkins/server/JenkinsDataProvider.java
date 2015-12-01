package unjenkins.server;

import unjenkins.client.JenkinsConsumer;
import unjenkins.client.dto.BuildDetail;
import unjenkins.client.dto.JobStats;
import unjenkins.client.dto.View;
import unjenkins.server.dto.BuildStatus;
import unjenkins.server.dto.JobStatus;
import unjenkins.server.dto.Metric;
import unjenkins.server.dto.ViewQuery;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Kurt on 11/30/15.
 */
public class JenkinsDataProvider {

    private static ArrayList<Metric> cachedMetrics = new ArrayList<Metric>();
    private static Metric currentMetric;
    static long timeout = 10000L;


    public static Metric getMetric(ViewQuery viewQuery){
        if (cachedMetrics.stream()
                //only look at metrics matching view name and name filter
                .filter(t -> {
                    return ((t.getViewQuery().getName() == viewQuery.getName())
                            && (t.getViewQuery().getFilter() == viewQuery.getFilter()));
                }).count() > 0){
            //get from existing cache and update if necessary
            return getCachedMetric(viewQuery);
        }
        //View and filter combination not yet in cache, add to cache and return
        Metric metric = getNewMetric(viewQuery);
        cachedMetrics.add(metric);
        return metric;

    }

    private static Metric getCachedMetric(ViewQuery viewQuery){
        cachedMetrics.stream()
                //only look at metrics matching view name and name filter
                .filter(t -> {
                    return ((t.getViewQuery().getName() == viewQuery.getName())
                            && (t.getViewQuery().getFilter() == viewQuery.getFilter()));
                })
                //refresh metric if out of date
                .forEach(t -> {
                    if ((System.currentTimeMillis() - t.getRefreshDate()) > timeout){
                        //update list, set return value
                        setCurrentMetric(getNewMetric(viewQuery));
                        t = getCurrentMetric();
                    }
                    else{
                        //do not update list and set return value to existing cached value
                        setCurrentMetric(t);
                    }
                });

        return getCurrentMetric();
    }

    private static Metric getCurrentMetric() {
        return currentMetric;
    }

    private static void setCurrentMetric(Metric currentMetric) {
        JenkinsDataProvider.currentMetric = currentMetric;
    }

    private static Metric getNewMetric(ViewQuery viewQuery){

        Metric metric = new Metric();
        metric.setViewQuery(viewQuery);

        View view = getCurrentView(viewQuery);

        getStatuses(view, viewQuery.getFilter()).forEach(t -> {
            JobStatus status = new JobStatus();
            status.setName(t.getDisplayName());
            t.getBuilds().stream().parallel().forEach(m -> {
                try {
                    BuildDetail buildDetail = JenkinsConsumer.jenkinsResource.getBuildDetail(t.getDisplayName(), m.getNumber(),
                            URLEncoder.encode("actions[failCount,skipCount,totalCount],result[result],number[number],building[building],url[url]", "UTF-8"));
                    BuildStatus buildStatus = new BuildStatus();
                    buildStatus.setBuildDetail(buildDetail);
                    status.getBuildStatusList().add(buildStatus);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            });
            metric.getJobStatusArrayList().add(status);
        });
        metric.setRefreshDate(System.currentTimeMillis());
        return metric;
    }

    private static View getCurrentView(ViewQuery viewQuery){
        View view;
        if (viewQuery.isSubView() == true){
            view =  JenkinsConsumer.jenkinsResource.getSubView(viewQuery.getName(), viewQuery.getFolder());
        }
        else {
            view =  JenkinsConsumer.jenkinsResource.getView(viewQuery.getName());
        }
        return view;
    }

    private static ArrayList<JobStats> getStatuses(View view, String viewFilter){
        ArrayList<JobStats> jobStatuses = new ArrayList<JobStats>();
        view.getJobs().stream().filter(t -> t.getName().contains(viewFilter)).forEach(t -> {
            try {
                jobStatuses.add(JenkinsConsumer.jenkinsResource.getJob(t.getName(),
                        URLEncoder.encode("displayName[displayName],builds[number,url]", "UTF-8")));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });
        return jobStatuses;
    }

}


