package com.kodz.unjenkins.client.dto;

/**
 * Created by master on 1/19/2016.
 */
public class HealthCheck {
     Boolean isConnected = false;
     Boolean isHeartbeatRunning = false;
     Boolean isReconnectQueued = false;
     Boolean isReconnecting = false;
     String currentTimeStamp;

    public Boolean getConnected() {
        return isConnected;
    }

    public void setConnected(Boolean connected) {
        isConnected = connected;
    }

    public Boolean getHeartbeatRunning() {
        return isHeartbeatRunning;
    }

    public void setCurrentTimeStamp(String currentTimeStamp) {
        this.currentTimeStamp = currentTimeStamp;
    }

    public Boolean getReconnecting() {
        return isReconnecting;
    }

    public void setHeartbeatRunning(Boolean heartbeatRunning) {
        isHeartbeatRunning = heartbeatRunning;
    }

    public Boolean getReconnectQueued() {
        return isReconnectQueued;
    }

    public void setReconnecting(Boolean reconnecting) {
        isReconnecting = reconnecting;
    }

    public String getCurrentTimeStamp() {
        return currentTimeStamp;
    }

    public void setReconnectQueued(Boolean reconnectQueued) {
        isReconnectQueued = reconnectQueued;
    }


}
