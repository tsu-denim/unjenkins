import unjenkins.client.JenkinsConsumer;
import unjenkins.dto.BuildDetail;
import unjenkins.dto.JobStats;
import unjenkins.dto.View;

import java.io.IOException;
import java.net.URLEncoder;




/**
 * Created by Kurt on 11/20/15.
 */

public class Main {

    public static void main(String[] stringargs) throws IOException {
        JenkinsConsumer.initializeServer();

        String jobname = "content-management-service-tests-dev";

        JobStats jobStats = JenkinsConsumer.jenkinsResource.getJob(jobname,
                URLEncoder.encode("displayName[displayName],builds[number,url]", "UTF-8"));
        Integer buildNumber = jobStats.getBuilds().get(0).getNumber();

        BuildDetail build = JenkinsConsumer.jenkinsResource.getBuildDetail(jobname, buildNumber,
                URLEncoder.encode("actions[failCount,skipCount,totalCount],result[result],number[number],building[building],url[url]", "UTF-8"));

        View view = JenkinsConsumer.jenkinsResource.getSubView("Content Management", "CM API and UI");



        System.out.println(jobStats.getDisplayName());
        System.out.println(build.getNumber());
        System.out.println(build.getTestResults().getTotalCount());
        System.out.println(view.getName());
        System.in.read();

    }
}
