package com.kodz.unjenkins.server.endpoints.websocket.daemons;

import com.kodz.unjenkins.logging.Loggable;
import com.kodz.unjenkins.server.endpoints.websocket.providers.JobSearch;
import com.kodz.unjenkins.server.endpoints.websocket.providers.SubscriptionProvider;

import java.util.TimerTask;

/**
 * Created by Kurt on 3/16/16.
 */
public class SearchDaemon extends TimerTask implements Loggable{

    public void run(){
        logger().info("Refreshing Jobs Index");
        JobSearch.initializeJobSearch();
    }

}
