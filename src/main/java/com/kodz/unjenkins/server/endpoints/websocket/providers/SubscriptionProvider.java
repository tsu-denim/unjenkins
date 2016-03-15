package com.kodz.unjenkins.server.endpoints.websocket.providers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodz.unjenkins.client.JenkinsConsumer;
import com.kodz.unjenkins.client.dto.BuildDetail;
import com.kodz.unjenkins.client.dto.JobStats;
import com.kodz.unjenkins.client.proxy.JenkinsResource;
import com.kodz.unjenkins.server.dto.BuildStatus;
import com.kodz.unjenkins.server.dto.JobStatus;
import com.kodz.unjenkins.server.dto.Subscription;
import com.kodz.unjenkins.server.dto.UpdateNotification;
import com.kodz.unjenkins.server.endpoints.websocket.rooms.SubscriptionRoom;
import com.kodz.unjenkins.server.endpoints.websocket.sockets.SubscriptionSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Kurt on 3/11/16.
 */
public class SubscriptionProvider {
    public static Logger logger = LoggerFactory.getLogger(SubscriptionProvider.class);

    static ArrayList<JobStatus> jobPool = new ArrayList<>();
    static ArrayList<Subscription> subscriptions = new ArrayList<>();
    static ArrayList<UpdateNotification> notifyQueue = new ArrayList<>();

    public synchronized static ArrayList<JobStatus> getJobs() {

        return jobPool;
    }

    public synchronized static ArrayList<JobStatus> getJobsFromPool(ArrayList<String> jobNames) {

        addJobsToPool(jobNames);
        ArrayList<JobStatus> returnFromPool = new ArrayList<>();

        for (String name : jobNames) {
            for (JobStatus job : getJobs()) {
                if (job.getName().equals(name)) {
                    returnFromPool.add(job);
                    logger.info("Returning {} from pool", job.getName());
                }
            }
        }
        return returnFromPool;
    }

    public synchronized static void addSubscription(Subscription subscription) {
        logger.info("Adding subscription for the following jobs to the pool: {} ", subscription.getJobs());
        getSubscriptions().add(subscription);
    }

    public synchronized static ArrayList<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public synchronized static void removeSubscription(SubscriptionSocket subscription) {
        ArrayList<Subscription> subsToRemove = new ArrayList<>();

        getSubscriptions().forEach(sub -> {
            if (sub.getSubscriptionSocket().equals(subscription)) {
                subsToRemove.add(sub);
            }
        });
        subsToRemove.forEach(sub -> {
            getSubscriptions().remove(sub);
        });
    }

    public synchronized static void notifySubscribers() {
        logger.info("Sending the following number of notifications {}", getNotifyQueue().size());

        for (int i = 0; i < getNotifyQueue().size(); i++) {
            UpdateNotification updateNotification = getNotifyQueue().get(i);
                SubscriptionRoom.sendMessage(updateNotification);
                getNotifyQueue().remove(i);
        }
    }

    public synchronized static void addNotification(UpdateNotification updateNotification) {
        logger.info("Adding notification to queue for job {}", updateNotification.getJobStatus().getName());
        getNotifyQueue().add(updateNotification);
    }

    public synchronized static ArrayList<UpdateNotification> getNotifyQueue() {
        return notifyQueue;
    }

    public synchronized static void updateJobs() {
        logger.info("Updating job pool...");
        ArrayList<JobStatus> jobStatuses = new ArrayList<>();
        boolean isUpdateFound = false;
        for (int i = 0; i < getJobs().size(); i++) {
            String jobName = getJobs().get(i).getName();
            try {
                JobStats jobStats = JenkinsConsumer.jenkinsResource.getJob(jobName,
                        URLEncoder.encode("displayName[displayName],builds[number,url]{0,5}", "UTF-8"));
                JobStatus status = new JobStatus();
                status.setName(jobStats.getDisplayName());
                List<BuildStatus> buildStatusList = jobStats.getBuilds().stream().parallel().map(t -> {
                    try {
                        BuildDetail buildDetail = JenkinsConsumer.jenkinsResource.getBuildDetail(jobStats.getDisplayName(),
                                t.getNumber(), URLEncoder.encode("actions[failCount,skipCount,totalCount],result[result],number[number],building[building],url[url]", "UTF-8"));
                        return new BuildStatus(buildDetail);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        throw new RuntimeException();
                    }
                }).collect(Collectors.toList());
                status.setBuildStatusList(buildStatusList);
                Collections.sort(status.getBuildStatusList());
                if (updateable(getJobs().get(i), status)) {
                    logger.info("Jobs do not match, updating...");
                    getJobs().set(i, status);
                    jobStatuses.add(status);
                    isUpdateFound = true;
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if (isUpdateFound) {
                for (Subscription subscription : getSubscriptions()) {
                    for (JobStatus jobStatus : subscription.getJobs()) {
                        for (JobStatus jobStatus1 : jobStatuses) {
                            if (jobStatus.getName().equals(jobStatus1.getName())) {
                                logger.info("Subscriber found, adding notification for {}", jobStatus.getName());
                                UpdateNotification updateNotification = new UpdateNotification();
                                updateNotification.setJobStatus(jobStatus1);
                                updateNotification.setSubscriptionSocket(subscription.getSubscriptionSocket());
                                addNotification(updateNotification);
                            }
                        }
                    }

                }
            }


        }
    }

    public synchronized static void cleanUpJobs() {
    }

    public synchronized static void addJobsToPool(ArrayList<String> jobsToAdd) {
        ArrayList<String> jobsNotFound = new ArrayList<>();

        if (getJobs().size() > 0) {
            for (String name : jobsToAdd) {
                for (JobStatus jobStatus : getJobs()) {
                    if (!(jobStatus.getName().equals(name))) {
                        jobsNotFound.add(name);
                        logger.info("Job {} not found in pool, adding to queue.", name);
                    }
                }
                ;
            }
            ;
        } else {
            jobsNotFound = jobsToAdd;
            logger.info("Job pool empty, queueing all requested jobs: {}", jobsNotFound);
        }

        ArrayList<JobStats> jobStats = new ArrayList<>();

        jobsNotFound.forEach(job -> {
            try {
                jobStats.add(JenkinsConsumer.jenkinsResource.getJob(job,
                        URLEncoder.encode("displayName[displayName],builds[number,url]{0,5}", "UTF-8")));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });

        for (JobStats jobStats1 : jobStats) {
            JobStatus status = new JobStatus();
            status.setName(jobStats1.getDisplayName());
            List<BuildStatus> buildStatusList = jobStats1.getBuilds().stream().parallel().map(t -> {
                try {
                    BuildDetail buildDetail = JenkinsConsumer.jenkinsResource.getBuildDetail(jobStats1.getDisplayName(),
                            t.getNumber(), URLEncoder.encode("actions[failCount,skipCount,totalCount],result[result],number[number],building[building],url[url]", "UTF-8"));
                    return new BuildStatus(buildDetail);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    throw new RuntimeException();
                }
            }).collect(Collectors.toList());
            status.setBuildStatusList(buildStatusList);
            Collections.sort(status.getBuildStatusList());
            getJobs().add(status);

        }
    }

    public static boolean updateable(JobStatus old, JobStatus fresh) {
        if (old.getBuildStatusList().get(0).getBuildNumber() != fresh.getBuildStatusList().get(0).getBuildNumber()) {
            logger.info("Build numbers do not match, build is updateable.");
            return true;
        } else if (old.getBuildStatusList().get(0).getBuildNumber() == fresh.getBuildStatusList().get(0).getBuildNumber()) {
            if (old.getBuildStatusList().get(0).isBuilding() != fresh.getBuildStatusList().get(0).isBuilding()) {
                logger.info("Build numbers match, but isBuilding flags are different, build is updateable.");
                return true;
            }
        }
        logger.info("Build numbers match and build is finished building. Build is not updateable.");
        return false;
    }

}
