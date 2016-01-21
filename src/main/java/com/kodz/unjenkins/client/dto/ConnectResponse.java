package com.kodz.unjenkins.client.dto;

/**
 * Created by master on 1/21/2016.
 */
public class ConnectResponse {
    private String timeStamp;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
