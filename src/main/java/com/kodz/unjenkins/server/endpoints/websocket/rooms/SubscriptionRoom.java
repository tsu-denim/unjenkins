package com.kodz.unjenkins.server.endpoints.websocket.rooms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodz.unjenkins.client.helper.ConnectionHealth;
import com.kodz.unjenkins.server.dto.*;
import com.kodz.unjenkins.server.endpoints.websocket.providers.JobSearch;
import com.kodz.unjenkins.server.endpoints.websocket.providers.SubscriptionProvider;
import com.kodz.unjenkins.server.endpoints.websocket.sockets.SubscriptionSocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kurt on 3/11/16.
 */
public class SubscriptionRoom {

        private static final SubscriptionRoom INSTANCE = new SubscriptionRoom();

        public static SubscriptionRoom getInstance() {
            return INSTANCE;
        }

        private List<SubscriptionSocket> members = new ArrayList<>();

        public void join(SubscriptionSocket socket) {
            members.add(socket);
        }

        public void writeAllMembers(final String message){
            this.members.forEach(t -> {
                try {
                    t.session.getRemote().sendString(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        public void removeMember(SubscriptionSocket subscriptionSocket){
            this.members.remove(subscriptionSocket);
            SubscriptionProvider.removeSubscription(subscriptionSocket);
        }

        public void processCommand (SubscriptionSocket subscriptionSocket, String message){
            try {
                if (!ConnectionHealth.getHealthCheck().getConnected()){
                    throw new JsonMappingException("Bad Gateway to Jenkins");
                }
                UserEvent userEvent = fromJson(message);
                switch (userEvent.getUserEventType())
                {
                    case addJobs:  Subscription subscription = new Subscription();
                        subscription.setSubscriptionSocket(subscriptionSocket);
                        subscription.setJobs(SubscriptionProvider.getJobsFromPool(userEvent.getValues()));
                        SubscriptionProvider.addSubscription(subscription);
                        sendMessage(subscription);
                        break;
                    case query:
                        sendMessage(toJson(JobSearch.query(userEvent.getValues().get(0))), subscriptionSocket);
                        break;
                    default:
                        JsonError jsonError = new JsonError();
                        jsonError.setErrorMessage("Event Handler Not Implemented");
                        sendMessage(toJson(jsonError), subscriptionSocket);
                }
            }
            catch (JsonMappingException e) {
                JsonError jsonError = new JsonError();
                jsonError.setErrorMessage(e.getMessage());
               sendMessage(toJson(jsonError), subscriptionSocket);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    public static void sendMessage(String message, SubscriptionSocket subscriptionSocket) {
        try {
            subscriptionSocket.session.getRemote().sendString(message);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public static void sendMessage(UpdateNotification updateNotification) {
        try {
            ServerEvent serverEvent = new ServerEvent();
            serverEvent.setServerEventType(ServerEventType.jobUpdate);
            serverEvent.getJobStatus().add(updateNotification.getJobStatus());
            String message = toJson(serverEvent);
            updateNotification.getSubscriptionSocket().session.getRemote().sendString(message);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public static void sendMessage(Subscription subscription) {
        try {
            ServerEvent serverEvent = new ServerEvent();
            serverEvent.setServerEventType(ServerEventType.subscriptionData);
            serverEvent.setJobStatus(subscription.getJobs());
            String message = toJson(serverEvent);
            subscription.getSubscriptionSocket().session.getRemote().sendString(message);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public static String toJson(Object object){
        String jsonString = null;
        try {
            jsonString = new ObjectMapper().writeValueAsString(object);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    public static UserEvent fromJson(String json) throws IOException {

        return new ObjectMapper().readValue(json, UserEvent.class);
    }

}
