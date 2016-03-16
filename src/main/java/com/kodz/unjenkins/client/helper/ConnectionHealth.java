package com.kodz.unjenkins.client.helper;

import com.kodz.unjenkins.client.dto.HealthCheck;
import com.kodz.unjenkins.server.endpoints.websocket.daemons.FetchDaemon;
import com.kodz.unjenkins.server.endpoints.websocket.daemons.NotifyDaemon;
import com.kodz.unjenkins.server.endpoints.websocket.daemons.SearchDaemon;
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
    private static Boolean isFetchDaemonRunning = false;
    private static Boolean isNotifyDaemonRunning = false;
    private static Boolean isSearchDaemonRunning = false;

    public static Boolean isSearchDaemonRunning() {
        return isSearchDaemonRunning;
    }

    public static void setIsSearchDaemonRunning(Boolean isSearchDaemonRunning) {
        ConnectionHealth.isSearchDaemonRunning = isSearchDaemonRunning;
    }

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

    public static Boolean isFetchDaemonRunning() {
        return isFetchDaemonRunning;
    }

    public static Boolean isHealthCheckRunning() {
        return isHealthCheckRunning;
    }

    public static void setIsFetchDaemonRunning(Boolean isFetchDaemonRunning) {
        ConnectionHealth.isFetchDaemonRunning = isFetchDaemonRunning;
    }

    public static Boolean isNotifyDaemonRunning() {
        return isNotifyDaemonRunning;
    }

    public static void setIsNotifyDaemonRunning(Boolean isNotifyDaemonRunning) {
        ConnectionHealth.isNotifyDaemonRunning = isNotifyDaemonRunning;
    }

    private synchronized static void initializeConnectionHealth(){
        printStatus();
        logger.debug("HealthCheck initializing...");
        if (!getIsHealthCheckRunning()){
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new ConnectionTest(), 5000, Configuration.Setting.getHeartbeatInterval());
            setIsHealthCheckRunning(true);
        };

        if (!isNotifyDaemonRunning()){
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new NotifyDaemon(), 5000, Configuration.Setting.getDaemonInterval());
            setIsNotifyDaemonRunning(true);
        };

        if (!isFetchDaemonRunning){
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new FetchDaemon(), 5000, Configuration.Setting.getDaemonInterval());
            setIsFetchDaemonRunning(true);
        };

        if (!isSearchDaemonRunning){
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new SearchDaemon(), 5000, 300000);
            setIsFetchDaemonRunning(true);
        };
        printStatus();
    }

    public static void printStatus(){
        logger.debug("HealthCheck is running: " + getIsHealthCheckRunning());
        logger.debug("Fetch Daemon is running: " + isFetchDaemonRunning());
        logger.debug("Notify Daemon is running: " + isNotifyDaemonRunning());
        logger.debug("Search Daemon is running: " + isSearchDaemonRunning());
    }

}
