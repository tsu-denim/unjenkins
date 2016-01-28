package com.kodz.unjenkins.client.helper;

import com.kodz.unjenkins.client.dto.HealthCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;

/**
 * Created by master on 1/21/2016.
 */
public class ConnectionHealth {
    public static Logger logger = LoggerFactory.getLogger(ConnectionHealth.class);
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
        logger.debug("HealthCheck initializing...");
        if (!getIsHealthCheckRunning()){
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new ConnectionTest(), 5000, Configuration.Setting.getHeartbeatInterval());
            setIsHealthCheckRunning(true);
        };
        printStatus();
    }

    public static void printStatus(){
        logger.debug("HealthCheck is running: " + getIsHealthCheckRunning());
    }

}
