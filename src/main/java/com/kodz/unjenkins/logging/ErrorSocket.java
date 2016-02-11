package com.kodz.unjenkins.logging;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

/**
 * Created by Kurt on 1/26/16.
 */
@WebSocket
public class ErrorSocket {
    public Session session;

    @OnWebSocketConnect
    public void onConnect(Session session) {
        this.session = session;
        ErrorRoom.getInstance().join(this);
    }

    @OnWebSocketMessage
    public void onText(String message) {
        ErrorRoom.getInstance().writeAllMembers(message);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        ErrorRoom.getInstance().removeMember(this);
    }
}