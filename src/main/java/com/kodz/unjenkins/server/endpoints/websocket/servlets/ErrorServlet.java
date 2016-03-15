package com.kodz.unjenkins.server.endpoints.websocket.servlets;

import com.kodz.unjenkins.server.endpoints.websocket.sockets.ErrorSocket;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

@SuppressWarnings("serial")
public class ErrorServlet extends WebSocketServlet
{
    @Override
    public void configure(WebSocketServletFactory factory)
    {
        factory.register(ErrorSocket.class);
    }
}
