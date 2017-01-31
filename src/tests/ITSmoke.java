import com.kodz.unjenkins.client.JenkinsConsumer;
import com.kodz.unjenkins.client.dto.JobStats;
import com.kodz.unjenkins.client.helper.Configuration;

import org.junit.Test;

import java.net.URLEncoder;

/**
 * Created by kwaechter on 1/31/17.
 */
public class ITSmoke {

    @Test
    public void smokeTest(){
        Configuration configuration = new Configuration();
        try {
            JenkinsConsumer.initializeClient();
            JobStats jobStats = JenkinsConsumer.jenkinsResource.getJob("Test",
                    URLEncoder.encode("displayName[displayName],builds[number,url]{0,5}", "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
