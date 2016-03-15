package com.kodz.unjenkins.server.endpoints.websocket.rooms;

import com.kodz.unjenkins.server.endpoints.websocket.sockets.DebugSocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kurt on 1/26/16.
 */
public class DebugRoom {
    private static final DebugRoom INSTANCE = new DebugRoom();

    public static DebugRoom getInstance() {
        return INSTANCE;
    }

    private List<DebugSocket> members = new ArrayList<>();

    public void join(DebugSocket socket) {
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

    public void removeMember(DebugSocket debugSocket){
        this.members.remove(debugSocket);
    }
}