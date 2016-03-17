package com.kodz.unjenkins.server.endpoints.websocket.daemons;

import com.kodz.unjenkins.server.endpoints.websocket.providers.SubscriptionPools;
import com.kodz.unjenkins.server.endpoints.websocket.providers.SubscriptionProvider;

import java.util.TimerTask;

/**
 * Created by Kurt on 3/17/16.
 */
public class PingDaemon extends TimerTask {

    public void run(){
        SubscriptionProvider.sendPings();
    }
}
