package com.kodz.unjenkins.client.helper;

import com.kodz.unjenkins.client.DeploymentBuddyConsumer;
import com.kodz.unjenkins.client.dto.HealthCheck;
import com.kodz.unjenkins.client.proxy.DeploymentBuddyResource;
import com.kodz.unjenkins.logging.ErrorRoom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.TimerTask;

/**
 * Created by master on 1/18/2016.
 */
public class ConnectionTest extends TimerTask {
    public static Logger logger = LoggerFactory.getLogger(ConnectionTest.class);
    public void run(){
        HealthCheck healthCheck = DeploymentBuddyConsumer.deploymentBuddyResource.getHealthCheck();
        ConnectionHealth.setHealthCheck(healthCheck);
        if (healthCheck.getConnected()){
            logger.info("Jenkins Health Check Status: Connected");
            ErrorRoom.getInstance().writeAllMembers("jenkinsConnectionStatus: connected");
        }
        else {
            if (healthCheck.getReconnecting()) {
                logger.info("Jenkins Health Check Status: Reconnecting");
                ErrorRoom.getInstance().writeAllMembers("jenkinsConnectionStatus: reconnected");
            }
            else {
                logger.info("Jenkins Health Check Status: Disconnected");
                ErrorRoom.getInstance().writeAllMembers("jenkinsConnectionStatus: disconnected");
            }

        }
    }



}