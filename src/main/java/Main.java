import unjenkins.client.JenkinsConsumer;
import unjenkins.client.dto.BuildDetail;
import unjenkins.client.dto.JobStats;
import unjenkins.client.dto.View;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;


/**
 * Created by Kurt on 11/20/15.
 */

public class Main {

    public static void main(String[] stringargs) throws IOException {
        JenkinsConsumer.initializeClient();
        callJenkins("Content Management", "CM API and UI");


    }

    public static void callJenkins (String viewName, String subViewName) throws IOException {

        View view = JenkinsConsumer.jenkinsResource.getSubView(viewName, subViewName);

        ArrayList<JobStats> jobStatses = new ArrayList<JobStats>();
        view.getJobs().stream().filter(t -> t.getName().contains("test")).forEach(t -> {
            try {
                jobStatses.add(JenkinsConsumer.jenkinsResource.getJob(t.getName(),
                        URLEncoder.encode("displayName[displayName],builds[number,url]", "UTF-8")));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });

        ArrayList<BuildDetail> buildDetails = new ArrayList<BuildDetail>();

                jobStatses.forEach(t -> {
            t.getBuilds().stream().parallel().forEach(m -> {
                try {
                    buildDetails.add(JenkinsConsumer.jenkinsResource.getBuildDetail(t.getDisplayName(), m.getNumber(),
                            URLEncoder.encode("actions[failCount,skipCount,totalCount],result[result],number[number],building[building],url[url]", "UTF-8")));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            });
        });




       System.in.read();
    }
}
