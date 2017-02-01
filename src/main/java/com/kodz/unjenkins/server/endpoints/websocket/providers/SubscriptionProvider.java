package com.kodz.unjenkins.server.endpoints.websocket.providers;

import com.kodz.unjenkins.client.JenkinsConsumer;
import com.kodz.unjenkins.client.dto.BuildDetail;
import com.kodz.unjenkins.client.dto.JobStats;
import com.kodz.unjenkins.server.dto.BuildStatus;
import com.kodz.unjenkins.server.dto.JobStatus;
import com.kodz.unjenkins.server.dto.Subscription;
import com.kodz.unjenkins.server.dto.UpdateNotification;
import com.kodz.unjenkins.server.endpoints.websocket.rooms.SubscriptionRoom;
import com.kodz.unjenkins.server.endpoints.websocket.sockets.SubscriptionSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Created by Kurt on 3/11/16.
 */
public class SubscriptionProvider {

    public static Logger logger = LoggerFactory.getLogger(SubscriptionProvider.class);
    private static boolean suppressJobPoolCleanupLogMessage = false;
    private static boolean suppressSubscriberUpdateLogMessage = false;

    public synchronized static CopyOnWriteArrayList<JobStatus> getJobs() {
        return SubscriptionPools.safeJobPool;
    }

    public synchronized static ArrayList<JobStatus> getJobsFromPool(ArrayList<String> jobNames) {
        ArrayList<JobStatus> returnFromPool = new ArrayList<>();
        addJobsToPool(jobNames);

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
        SubscriptionPools.safeSubscriptions.add(subscription);
    }

    public synchronized static CopyOnWriteArrayList<Subscription> getSubscriptions() {
        return SubscriptionPools.safeSubscriptions;
    }

    public synchronized static void removeSubscription(SubscriptionSocket subscription) {
        ArrayList<Subscription> subsToRemove = new ArrayList<>();

        getSubscriptions().forEach(sub -> {
            if (sub.getSubscriptionSocket().equals(subscription)) {
                subsToRemove.add(sub);
            }
        });
        getSubscriptions().removeAll(subsToRemove);
    }

    public synchronized static void notifySubscribers() {
        for (UpdateNotification updateNotification; (updateNotification = SubscriptionPools.messageQueue.poll()) != null; ) {
            logger.info("Sending update to client for job {}", updateNotification.getJobStatus().getName());
            SubscriptionRoom.sendMessage(updateNotification);
        }
    }

    public synchronized static void addNotification(UpdateNotification updateNotification) {
        logger.info("Adding notification to queue for job {}", updateNotification.getJobStatus().getName());
        SubscriptionPools.messageQueue.offer(updateNotification);
    }

    public synchronized static void updateJobs() {
        if (!(SubscriptionPools.safeJobPool.isEmpty())) {
            suppressSubscriberUpdateLogMessage = false;
            logger.info("Updating job pool...");
            for (int i = 0; i < getJobs().size(); i++) {
                String jobName = getJobs().get(i).getName();
                try {
                    JobStatus status = getJobStatus(jobName);

                    if (updatable(getJobs().get(i), status)) {
                        logger.info("Jobs do not match, updating...");
                        getJobs().set(i, status);
                        if(SubscriptionPools.jobUpdateQueue.stream().filter(t -> t.getName().equals(status.getName())).count() < 1){
                            logger.info("Adding job {} to update queue.", status.getName());
                            SubscriptionPools.jobUpdateQueue.offer(status);
                        }
                        else {
                            logger.info("Job {} already in queue, ignoring...", status.getName());
                        }
                    }

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            for (JobStatus jobStatus; (jobStatus = SubscriptionPools.jobUpdateQueue.poll()) != null; ) {
                for (Subscription subscription : SubscriptionPools.safeSubscriptions) {
                    //TODO: This is needs updated to use a stream filter instead, this creates a weird cartesian product
                    for (JobStatus job : subscription.getJobs()) {
                        if (job.getName().equals(jobStatus.getName())) {
                            UpdateNotification updateNotification = new UpdateNotification();
                            updateNotification.setSubscriptionSocket(subscription.getSubscriptionSocket());
                            updateNotification.setJobStatus(jobStatus);
                            addNotification(updateNotification);
                        }
                    }
                }
            }
        } else {
            if (!suppressSubscriberUpdateLogMessage){
                suppressSubscriberUpdateLogMessage = true;
                logger.info("No subscribers connected, nothing to update.");
            }
        }
    }

    public synchronized static void cleanUpJobs() {
        ArrayList<JobStatus> allJobNames = new ArrayList<>();
        for (Subscription subscription : SubscriptionPools.safeSubscriptions) {
            allJobNames.addAll(subscription.getJobs());
        }

        List<String> activeNames = allJobNames.stream().map(t -> t.getName()).collect(Collectors.toList());
        Set<String> uniqueActive = new HashSet<String>(activeNames);

        List<String> allPoolNames = getJobs().stream().map(t -> t.getName()).collect(Collectors.toList());
        Set<String> uniquePool = new HashSet<String>(allPoolNames);

        uniquePool.removeAll(uniqueActive);

        if (uniquePool.size() > 0) {
            suppressJobPoolCleanupLogMessage = false;
            logger.info("Cleanup: Jobs Pool contains {} orhpaned jobs. Removing...", uniquePool.size());
            ArrayList<JobStatus> jobsToDelete = new ArrayList<>();
            for (JobStatus jobStatus : getJobs()) {
                for (String jobToDelete : uniquePool) {
                    if (jobStatus.getName().equals(jobToDelete)) {
                        jobsToDelete.add(jobStatus);
                    }
                }
            }

            getJobs().removeAll(jobsToDelete);
            List<String> allUpdatedPoolNames = getJobs().stream().map(t -> t.getName()).collect(Collectors.toList());
            Set<String> uniqueUpdatedPool = new HashSet<String>(allUpdatedPoolNames);

            uniqueUpdatedPool.removeAll(uniqueActive);
            logger.info("Cleanup complete. {} orphaned entries remain.", uniqueUpdatedPool.size());
        } else {
            if(!suppressJobPoolCleanupLogMessage){
                suppressJobPoolCleanupLogMessage =true;
                logger.info("Nothing to cleanup.");
            }
        }
    }

    public synchronized static void addJobsToPool(ArrayList<String> jobsToAdd) {

        if (getJobs().size() > 0) {
            for (String name : jobsToAdd) {
                if (getJobs().stream().filter(t -> t.getName().equals(name)).count() < 1) {
                    try {
                        JobStatus status = getJobStatus(name);
                        SubscriptionPools.safeJobPool.add(status);

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

            }

        } else {

            jobsToAdd.forEach(name -> {
                try {
                    JobStatus status = getJobStatus(name);
                    SubscriptionPools.safeJobPool.add(status);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            });
        }


    }

    public static boolean updatable(JobStatus old, JobStatus fresh) {
        boolean isBuildFresh = false;
        boolean isBuildSame = false;

        if (!old.getBuildStatusList().isEmpty() && !fresh.getBuildStatusList().isEmpty()){
            BuildStatus oldBuild = old.getBuildStatusList().stream().min(BuildStatus::compareTo).get();
            BuildStatus newBuild = fresh.getBuildStatusList().stream().min(BuildStatus::compareTo).get();
            isBuildFresh = newBuild.getBuildNumber() > oldBuild.getBuildNumber();
            isBuildSame = newBuild.getBuildNumber() == oldBuild.getBuildNumber();

            if (isBuildFresh) {
                logger.info("new build found: {} is newer than {}", newBuild.getBuildNumber(), oldBuild.getBuildNumber());
                return true;
            } else if (isBuildSame) {
                if (oldBuild.isBuilding()) {
                    if (!newBuild.isBuilding()) {
                        logger.info("build just finished: {} building status went from {} to {}",
                                oldBuild.getBuildNumber(), oldBuild.isBuilding(), newBuild.isBuilding());
                        return true;
                    }
                }
            }
            logger.info("job still fresh: old {}, new {}, isBuildingOld {}, isBuildingNew {}",
                    oldBuild.getBuildNumber(), newBuild.getBuildNumber(), oldBuild.isBuilding(), newBuild.isBuilding());
        }

        return false;
    }

    public static JobStatus getJobStatus(String jobName) throws UnsupportedEncodingException {

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

        return status;
    }

    public static void sendPings() {
        SubscriptionPools.safeSubscriptions.forEach(t -> t.getSubscriptionSocket().sendPing());
    }

}
