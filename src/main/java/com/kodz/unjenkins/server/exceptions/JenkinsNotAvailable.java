package com.kodz.unjenkins.server.exceptions;

import com.kodz.unjenkins.client.dto.ServerSetting;
import com.kodz.unjenkins.client.helper.Configuration;
import com.kodz.unjenkins.logging.Loggable;

/**
 * Created by Kurt on 3/8/16.
 */
public class JenkinsNotAvailable extends Exception implements Loggable {
    public String getJenkinsMessage(){
        logger().error("Bad network connection, could not connect to Jenkins.");
        return "Unable to connect to Jenkins, the network connection may be offline or the server at "
                + Configuration.Setting.getRemoteJenkinsHostDomain() + ":" + Configuration.Setting.getRemoteJenkinsHostPort() + " may not be responding.";
    }
}
