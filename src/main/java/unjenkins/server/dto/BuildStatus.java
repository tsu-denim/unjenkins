package unjenkins.server.dto;

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

}
