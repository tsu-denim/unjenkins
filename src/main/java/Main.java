import unjenkins.client.JenkinsConsumer;
import unjenkins.client.dto.BuildDetail;
import unjenkins.client.dto.JobStats;
import unjenkins.client.dto.View;
import unjenkins.server.JenkinsDataProvider;
import unjenkins.server.dto.Metric;
import unjenkins.server.dto.ViewQuery;

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
        ViewQuery viewQuery = new ViewQuery("Content Management", "CM API and UI", "test");
        callJenkins(viewQuery);


    }

    public static void callJenkins (ViewQuery viewQuery) throws IOException {

        Metric metric = JenkinsDataProvider.getMetric(viewQuery);



       System.in.read();
    }
}
