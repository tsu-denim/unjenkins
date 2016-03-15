package com.kodz.unjenkins.server.endpoints.websocket.rooms;

import com.kodz.unjenkins.server.endpoints.websocket.sockets.ErrorSocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kurt on 1/26/16.
 */
public class ErrorRoom {
    private static final ErrorRoom INSTANCE = new ErrorRoom();

    public static ErrorRoom getInstance() {
        return INSTANCE;
    }

    private List<ErrorSocket> members = new ArrayList<>();

    public void join(ErrorSocket socket) {
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

    public void removeMember(ErrorSocket errorSocket){
        this.members.remove(errorSocket);
    }
}