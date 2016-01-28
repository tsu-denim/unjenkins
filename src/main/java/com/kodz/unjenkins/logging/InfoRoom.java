package com.kodz.unjenkins.logging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kurt on 1/26/16.
 */
public class InfoRoom {
    private static final InfoRoom INSTANCE = new InfoRoom();

    public static InfoRoom getInstance() {
        return INSTANCE;
    }

    private List<InfoSocket> members = new ArrayList<>();

    public void join(InfoSocket socket) {
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

    public void removeMember(InfoSocket infoSocket){
        this.members.remove(infoSocket);
    }
}