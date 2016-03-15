package com.kodz.unjenkins.server.endpoints.websocket.sockets;

import com.kodz.unjenkins.client.helper.Configuration;
import com.kodz.unjenkins.server.endpoints.websocket.rooms.InfoRoom;
import com.kodz.unjenkins.server.endpoints.websocket.rooms.SubscriptionRoom;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;

/**
 * Created by Kurt on 3/11/16.
 */
@WebSocket
public class SubscriptionSocket {
        public Session session;

        @OnWebSocketConnect
        public void onConnect(Session session) {
            this.session = session;
            SubscriptionRoom.getInstance().join(this);
            try {
                session.getRemote().sendString("Welcome to the Deployment Buddy Subscription Terminal!");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @OnWebSocketMessage
        public void onText(String message) {
            SubscriptionRoom.getInstance().processCommand(this, message);
        }

        @OnWebSocketClose
        public void onClose(int statusCode, String reason) {
            SubscriptionRoom.getInstance().removeMember(this);
        }
}
