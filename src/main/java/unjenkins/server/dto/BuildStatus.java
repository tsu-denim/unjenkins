package unjenkins.server.dto;

import unjenkins.client.dto.BuildDetail;

/**
 * Created by Kurt on 11/23/15.
 */
public class BuildStatus {
    private int passedTestCount;
    private int failedTestCount;
    private int totalTestCount;
    private int skippedTestCount;
    private int buildNumber;
    private boolean isPassed;
    private boolean isFailed;
    private boolean isAborted;
    private boolean isUnstable;
    private String url;
    //TODO: convert buildDetail to above properties
    private BuildDetail buildDetail;

    public BuildDetail getBuildDetail() {
        return buildDetail;
    }

    public void setBuildDetail(BuildDetail buildDetail) {
        this.buildDetail = buildDetail;
    }
}
