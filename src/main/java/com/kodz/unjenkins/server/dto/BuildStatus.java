package com.kodz.unjenkins.server.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.kodz.unjenkins.client.dto.Action;
import com.kodz.unjenkins.client.dto.BuildDetail;

/**
 * Created by Kurt on 11/23/15.
 */

public class BuildStatus implements Comparable<BuildStatus>{
    private int passedTestCount;
    private int failedTestCount;
    private int totalTestCount;
    private int skippedTestCount;
    private int buildNumber;
    private boolean isPassed ;
    private boolean isFailed ;
    private boolean isAborted ;
    private boolean isUnstable ;
    private boolean isBuilding ;
    private boolean isCorrupt ;
    private String url;
    //TODO: finish adding getters and setters
    private BuildDetail buildDetail;

    public  BuildStatus(){

    }

    public BuildStatus(BuildDetail buildDetail){
        importFromDetail(buildDetail);
    }

    private BuildDetail getBuildDetail() {
        return buildDetail;
    }

    private void setBuildDetail(BuildDetail buildDetail) {
        this.buildDetail = buildDetail;
    }

    private void importFromDetail(BuildDetail buildDetail){
        setBuildDetail(buildDetail);
        this.buildNumber = getBuildDetail().getNumber();
        Action testResults = getBuildDetail().getTestResults();
        this.passedTestCount = testResults.getPassedCount();
        this.failedTestCount = testResults.getFailCount();
        this.skippedTestCount = testResults.getSkipCount();
        this.totalTestCount = testResults.getTotalCount();
        this.url = getBuildDetail().getUrl();
        if (getBuildDetail().getResult().equals("SUCCESS")){
            setIsPassed(true);
            setIsFailed(false);
            setIsUnstable(false);
            setIsAborted(false);
            setIsCorrupt(false);

        }
        if (getBuildDetail().getResult().equals("FAILURE")){
            setIsPassed(false);
            setIsFailed(true);
            setIsUnstable(false);
            setIsAborted(false);
            setIsCorrupt(false);
        }
        if (getBuildDetail().getResult().equals("UNSTABLE")){
            setIsPassed(false);
            setIsFailed(false);
            setIsUnstable(true);
            setIsAborted(false);
            setIsCorrupt(false);
        }
        if (getBuildDetail().getResult().equals("ABORTED")){
            setIsPassed(false);
            setIsFailed(false);
            setIsUnstable(false);
            setIsAborted(true);
            setIsCorrupt(false);
        }
        if (getBuildDetail().getResult().equals("CORRUPT")){
            setIsPassed(false);
            setIsFailed(false);
            setIsUnstable(false);
            setIsAborted(false);
            setIsCorrupt(true);
        }
        setIsBuilding(getBuildDetail().getBuilding());

    }

    public void setIsAborted(boolean isAborted) {
        this.isAborted = isAborted;
    }

    public void setIsFailed(boolean isFailed) {
        this.isFailed = isFailed;
    }

    public void setIsPassed(boolean isPassed) {
        this.isPassed = isPassed;
    }

    public void setIsCorrupt(boolean isCorrupt) {
        this.isCorrupt = isCorrupt;
    }

    public void setIsUnstable(boolean isUnstable) {
        this.isUnstable = isUnstable;
    }

    public boolean isAborted() {
        return isAborted;
    }

    public boolean isBuilding() {
        return isBuilding;
    }

    public boolean isCorrupt() {
        return isCorrupt;
    }

    public boolean isFailed() {
        return isFailed;
    }

    public boolean isPassed() {
        return isPassed;
    }

    public boolean isUnstable() {
        return isUnstable;
    }

    public void setIsBuilding(boolean isBuilding) {
        this.isBuilding = isBuilding;
    }

    public int getBuildNumber() {
        return buildNumber;
    }

    public int getFailedTestCount() {
        return failedTestCount;
    }

    public int getPassedTestCount() {
        return passedTestCount;
    }

    public int getSkippedTestCount() {
        return skippedTestCount;
    }

    public int getTotalTestCount() {
        return totalTestCount;
    }

    public String getUrl() {
        return url;
    }

   @Override
    public int compareTo(BuildStatus buildStatus){
       try {
           int compareStatus = (buildStatus).getBuildNumber();
           return compareStatus - this.getBuildNumber();
       }
        catch (NullPointerException e){
            System.out.println("Null build status, returning 1 for comparison.");
            return 1;
        }
    }

    @Override
    public String toString(){
        return "";
    }
}
