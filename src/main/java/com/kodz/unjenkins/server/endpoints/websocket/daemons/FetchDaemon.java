package com.kodz.unjenkins.server.endpoints.websocket.daemons;


import com.kodz.unjenkins.logging.Loggable;
import com.kodz.unjenkins.server.endpoints.websocket.providers.SubscriptionProvider;


import java.util.TimerTask;

/**
 * Created by Kurt on 3/11/16.
 */
public class FetchDaemon extends TimerTask implements Loggable {

    public void run(){
        SubscriptionProvider.updateJobs();
    }



}