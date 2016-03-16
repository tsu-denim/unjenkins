package com.kodz.unjenkins.server.endpoints.websocket.servlets;

import com.kodz.unjenkins.server.endpoints.websocket.sockets.InfoSocket;
import com.kodz.unjenkins.server.endpoints.websocket.sockets.SubscriptionSocket;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

/**
 * Created by Kurt on 3/11/16.
 */
public class SubscriptionServlet extends WebSocketServlet
{
    @Override
    public void configure(WebSocketServletFactory factory)
    {
        factory.getPolicy().setIdleTimeout(5184000000L);
        factory.register(SubscriptionSocket.class);
    }
}
