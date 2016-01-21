package com.kodz.unjenkins.client.helper;

import com.kodz.unjenkins.client.DeploymentBuddyConsumer;
import com.kodz.unjenkins.client.dto.HealthCheck;
import com.kodz.unjenkins.client.proxy.DeploymentBuddyResource;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.TimerTask;

/**
 * Created by master on 1/18/2016.
 */
public class ConnectionTest extends TimerTask {

    public void run(){
        HealthCheck healthCheck = DeploymentBuddyConsumer.deploymentBuddyResource.getHealthCheck();
        ConnectionHealth.setHealthCheck(healthCheck);
        if (healthCheck.getConnected()){
            System.out.println("Jenkins Health Check Status: Connected");
        }
        else {
            System.out.println("Jenkins Health Check Status: Disconnected");
        }
    }



}