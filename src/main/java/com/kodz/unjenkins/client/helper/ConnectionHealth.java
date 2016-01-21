package com.kodz.unjenkins.client.helper;

import com.kodz.unjenkins.client.dto.HealthCheck;

import java.util.Timer;

/**
 * Created by master on 1/21/2016.
 */
public class ConnectionHealth {
    private static HealthCheck healthCheck;
    private static Boolean isHealthCheckRunning = false;

    static {initializeConnectionHealth();}

    public static HealthCheck getHealthCheck() {
        return healthCheck;
    }

    public static void setHealthCheck(HealthCheck healthCheck) {
        ConnectionHealth.healthCheck = healthCheck;
    }

    public static Boolean getIsHealthCheckRunning() {
        return isHealthCheckRunning;
    }

    public static void setIsHealthCheckRunning(Boolean isHealthCheckRunning) {
        ConnectionHealth.isHealthCheckRunning = isHealthCheckRunning;
    }

    private synchronized static void initializeConnectionHealth(){
        printStatus();
        System.out.println("HealthCheck initializing...");
        if (!getIsHealthCheckRunning()){
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new ConnectionTest(), 5000, 5000);
            setIsHealthCheckRunning(true);
        };
        printStatus();
    }

    public static void printStatus(){
        System.out.println("HealthCheck is running: " + getIsHealthCheckRunning());
    }

}
