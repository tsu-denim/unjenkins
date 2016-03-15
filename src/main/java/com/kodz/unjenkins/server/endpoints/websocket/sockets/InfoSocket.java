package com.kodz.unjenkins.server.endpoints.websocket.sockets;

import com.kodz.unjenkins.client.helper.Configuration;
import com.kodz.unjenkins.server.endpoints.websocket.rooms.InfoRoom;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;

/**
 * Created by Kurt on 1/26/16.
 */
@WebSocket
public class InfoSocket {
    public Session session;

    @OnWebSocketConnect
    public void onConnect(Session session) {
        this.session = session;
        InfoRoom.getInstance().join(this);
        try {
            session.getRemote().sendString("Welcome to the Deployment Buddy Info Terminal!");
            session.getRemote().sendString("This server is currently configured with the following settings:");
            Configuration.logSettings();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @OnWebSocketMessage
    public void onText(String message) {
        InfoRoom.getInstance().writeAllMembers(message);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        InfoRoom.getInstance().removeMember(this);
    }
}