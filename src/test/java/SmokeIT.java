import com.kodz.unjenkins.client.JenkinsConsumer;
import com.kodz.unjenkins.client.dto.JobStats;
import com.kodz.unjenkins.client.helper.Configuration;
import com.kodz.unjenkins.server.dto.JobStatus;
import com.kodz.unjenkins.server.dto.Subscription;
import com.kodz.unjenkins.server.endpoints.websocket.providers.SubscriptionProvider;

import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


import javax.ws.rs.core.Response;

/**
 * Created by kwaechter on 1/31/17.
 */
public class SmokeIT {

    @Before
    public void setup() throws Exception {
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
        JobStatus jobStatusA = SubscriptionProvider.getJobStatus(jobName);
        Response response = JenkinsConsumer.jenkinsResource.getNewBuild(jobName, URLEncoder.encode("0sec", "UTF-8"));
        Thread.sleep(1000);
        JobStatus jobStatusB = SubscriptionProvider.getJobStatus(jobName);

        assert SubscriptionProvider.updatable(jobStatusA, jobStatusB) :  "This should return true, build groups are different.";

    }
}
