package com.kodz.unjenkins.server.endpoints.websocket.sockets;

import com.kodz.unjenkins.server.endpoints.websocket.rooms.DebugRoom;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

/**
 * Created by Kurt on 1/26/16.
 */
@WebSocket
public class DebugSocket {
    public Session session;

    @OnWebSocketConnect
    public void onConnect(Session session) {
        this.session = session;
        DebugRoom.getInstance().join(this);
    }

    @OnWebSocketMessage
    public void onText(String message) {
        DebugRoom.getInstance().writeAllMembers(message);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        DebugRoom.getInstance().removeMember(this);
    }
}