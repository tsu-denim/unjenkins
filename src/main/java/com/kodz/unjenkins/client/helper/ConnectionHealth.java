package com.kodz.unjenkins.client.helper;

import com.kodz.unjenkins.client.dto.HealthCheck;
import com.kodz.unjenkins.server.endpoints.http.daemons.QueryDaemon;
import com.kodz.unjenkins.server.endpoints.websocket.daemons.FetchDaemon;
import com.kodz.unjenkins.server.endpoints.websocket.daemons.NotifyDaemon;
import com.kodz.unjenkins.server.endpoints.websocket.daemons.PingDaemon;
import com.kodz.unjenkins.server.endpoints.websocket.daemons.SearchDaemon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
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
    private static Boolean isPingDaemonRunning = false;
    private static Boolean isQueryDaemonRunning = false;
    private static ArrayList<Timer> timers = new ArrayList<>();

    public static Boolean isPingDaemonRunning() {
        return isPingDaemonRunning;
    }

    public static void setIsPingDaemonRunning(Boolean isPingDaemonRunning) {
        ConnectionHealth.isPingDaemonRunning = isPingDaemonRunning;
    }

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

    public static Boolean isQueryDaemonRunning() {
        return isQueryDaemonRunning;
    }

    public static void setIsQueryDaemonRunning(Boolean isQueryDaemonRunning) {
        ConnectionHealth.isQueryDaemonRunning = isQueryDaemonRunning;
    }

    private synchronized static void initializeConnectionHealth(){
        printStatus();
        logger.debug("HealthCheck initializing...");
        if (!getIsHealthCheckRunning()){
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new ConnectionTest(), 5000, Configuration.Setting.getHeartbeatInterval());
            setIsHealthCheckRunning(true);
            timers.add(timer);
        };

        if (!isNotifyDaemonRunning()){
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new NotifyDaemon(), 5000, 5000);
            setIsNotifyDaemonRunning(true);
            timers.add(timer);
        };

        if (!isFetchDaemonRunning()){
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new FetchDaemon(), 5000, 5000);
            setIsFetchDaemonRunning(true);
            timers.add(timer);
        };

        if (!isSearchDaemonRunning()){
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new SearchDaemon(), 5000, 300000);
            setIsSearchDaemonRunning(true);
            timers.add(timer);
        };
        if (!isPingDaemonRunning()){
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new PingDaemon(), 5000, 5000);
            setIsPingDaemonRunning(true);
            timers.add(timer);
        };
        printStatus();
    }

    public static void printStatus(){
        logger.debug("HealthCheck is running: " + getIsHealthCheckRunning());
        logger.debug("Fetch Daemon is running: " + isFetchDaemonRunning());
        logger.debug("Notify Daemon is running: " + isNotifyDaemonRunning());
        logger.debug("Search Daemon is running: " + isSearchDaemonRunning());
        logger.debug("Ping Daemon is running: " + isSearchDaemonRunning());
    }

    public static void stopTimers(){
        timers.forEach(t -> t.cancel());
    }

}
