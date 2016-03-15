package com.kodz.unjenkins.server.endpoints.websocket.servlets;

import com.kodz.unjenkins.server.endpoints.websocket.sockets.InfoSocket;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

@SuppressWarnings("serial")
public class InfoServlet extends WebSocketServlet
        {
@Override
public void configure(WebSocketServletFactory factory)
        {
        factory.register(InfoSocket.class);
        }
        }
