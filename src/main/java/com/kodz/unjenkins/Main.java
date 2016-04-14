package com.kodz.unjenkins;

import com.kodz.unjenkins.client.DeploymentBuddyConsumer;
import com.kodz.unjenkins.client.JenkinsConsumer;
import com.kodz.unjenkins.client.helper.Configuration;
import com.kodz.unjenkins.client.helper.ConnectionHealth;
import com.kodz.unjenkins.server.database.QueryHelper;
import com.kodz.unjenkins.server.dto.UserEvent;
import com.kodz.unjenkins.server.dto.UserEventType;
import com.kodz.unjenkins.server.endpoints.websocket.providers.JobSearch;
import com.kodz.unjenkins.server.endpoints.websocket.rooms.SubscriptionRoom;
import com.kodz.unjenkins.server.endpoints.websocket.servlets.DebugServlet;
import com.kodz.unjenkins.server.endpoints.websocket.servlets.ErrorServlet;
import com.kodz.unjenkins.server.endpoints.websocket.servlets.InfoServlet;
import com.kodz.unjenkins.server.endpoints.websocket.servlets.SubscriptionServlet;
import org.eclipse.jetty.server.Server;

import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 * Created by Kurt on 11/16/15.
 */
public class Main {

    private static int serverPort;
    private static Server server ;

    private static void startServer(int serverPort) throws Exception {
        JenkinsConsumer.initializeClient();
        DeploymentBuddyConsumer.initializeClient();
        ConnectionHealth connectionHealth = new ConnectionHealth();
        Main.serverPort = serverPort;
        Main.server = configureServer();
        server.setStopTimeout(3000L);
        server.start();
        server.join();
    }

    private static Server configureServer() {
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.packages("com.kodz.unjenkins");
        resourceConfig.register(JacksonFeature.class);
        ServletContainer servletContainer = new ServletContainer(resourceConfig);
        ServletHolder restServlet = new ServletHolder(servletContainer);
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(serverPort);
        server.addConnector(connector);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.setContextPath("/");

        ServletHolder logInfoServlet = new ServletHolder("ws-logInfo", InfoServlet.class);
        ServletHolder logDebugServlet = new ServletHolder("ws-logDebug", DebugServlet.class);
        ServletHolder logErrorServlet = new ServletHolder("ws-logError", ErrorServlet.class);
        ServletHolder subscriptionServlet = new ServletHolder("ws-subscribe", SubscriptionServlet.class);
        context.addServlet(logInfoServlet, "/log/info/*");
        context.addServlet(logDebugServlet, "/log/debug/*");
        context.addServlet(logErrorServlet, "/log/health/*");
        context.addServlet(subscriptionServlet, "/subscribe/job/*");
        context.addServlet(restServlet, "/*");

        server.setHandler(context);
        return server;
    }

    public static void stopServer() throws Exception {
        System.out.println("Stopping Jetty");
        try {
            // Stop the server.
            new Thread() {

                @Override
                public void run() {
                    try {
                        System.out.println("Shutting down Jetty...");
                        Main.server.stop();
                        System.out.println("Jetty has stopped.");
                    } catch (Exception ex) {
                        System.out.println("Error when stopping Jetty: " + ex.getMessage());
                    }
                }
            }.start();
        } catch (Exception ex) {
            System.out.println("Unable to stop Jetty: " + ex);

        }

    }

    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();

        int serverPort = Configuration.Setting.getServicePort();
        QueryHelper.init();
        startServer(serverPort);

    }

}