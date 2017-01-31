import com.kodz.unjenkins.client.JenkinsConsumer;
import com.kodz.unjenkins.client.dto.JobStats;
import com.kodz.unjenkins.client.helper.Configuration;
import com.kodz.unjenkins.logging.Loggable;
import com.kodz.unjenkins.server.dto.BuildStatus;
import com.kodz.unjenkins.server.dto.JobStatus;
import com.kodz.unjenkins.server.dto.Subscription;
import com.kodz.unjenkins.server.endpoints.websocket.providers.SubscriptionProvider;

import org.awaitility.Duration;
import org.eclipse.jetty.util.log.Log;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.Callable;


import javax.ws.rs.core.Response;

import static org.awaitility.Awaitility.await;


public class SmokeIT implements Loggable{

    @BeforeClass
    public static void setup() throws Exception {
        Configuration configuration = new Configuration();
        JenkinsConsumer.initializeClient();
    }
    @Test
    public void getJobStatusTest() throws Exception {
        String jobName = "Test";
        JobStatus jobStatus = SubscriptionProvider.getJobStatus(jobName);
        assert jobStatus.getName().equals(jobName) : "Job name should equal " + jobName + " but found " + jobStatus.getName();
    }

    @Test
    public void isUpdatableFalseTest() throws Exception {
        String jobName = "Test";
        JobStatus jobStatusA = SubscriptionProvider.getJobStatus(jobName);
        JobStatus jobStatusB = SubscriptionProvider.getJobStatus(jobName);

        assert !SubscriptionProvider.updatable(jobStatusA, jobStatusB) : "This should return false, two duplicate groups of builds do not warrant an update.";

    }

    @Test
    public void isUpdateableTrueTest() throws UnsupportedEncodingException, InterruptedException {
        String jobName = "TestBuilds";
        logger().info("Getting job status for " + jobName);
        JobStatus jobStatusA = SubscriptionProvider.getJobStatus(jobName);
        logger().info("Triggering new build for " + jobName);
        JenkinsConsumer.jenkinsResource.getNewBuild(jobName, URLEncoder.encode("0sec", "UTF-8"));
        logger().info("Waiting for new build to appear in Jenkins...");
        await().atMost(Duration.TWO_SECONDS).until(newBuildIsAdded(jobStatusA));
        logger().info("Getting updated job status...");
        JobStatus jobStatusB = SubscriptionProvider.getJobStatus(jobName);
        logger().info("Checking for a true response...");
        assert SubscriptionProvider.updatable(jobStatusA, jobStatusB) : "This should return true, build groups are different.";
    }

    private Callable<Boolean> newBuildIsAdded(JobStatus jobStatus) {
        return () -> {
            JobStatus jobStatusB = SubscriptionProvider.getJobStatus(jobStatus.getName());
            BuildStatus oldBuild = jobStatus.getBuildStatusList().stream().min(BuildStatus::compareTo).orElse(null);
            BuildStatus newBuild = jobStatusB.getBuildStatusList().stream().min(BuildStatus::compareTo).orElse(null);
            assert oldBuild != null : "Can't compare without any builds";
            assert newBuild != null : "Can't compare without any builds";
            return newBuild.getBuildNumber() > oldBuild.getBuildNumber();
        };
    }
}
