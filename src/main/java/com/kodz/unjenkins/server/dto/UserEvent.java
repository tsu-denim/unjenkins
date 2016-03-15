package com.kodz.unjenkins.server.dto;

import java.util.ArrayList;

/**
 * Created by Kurt on 3/15/16.
 */
public class UserEvent {
    String event = null;
    UserEventType userEventType = null;
    ArrayList<String> values = new ArrayList<>();

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public UserEventType getUserEventType() {
        return userEventType;
    }

    public void setUserEventType(UserEventType userEventType) {
        this.userEventType = userEventType;
    }

    public ArrayList<String> getValues() {
        return values;
    }

    public void setValues(ArrayList<String> values) {
        this.values = values;
    }
}
