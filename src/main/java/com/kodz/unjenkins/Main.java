package com.kodz.unjenkins;

import com.kodz.unjenkins.client.JenkinsConsumer;
import com.kodz.unjenkins.client.helper.Configuration;
import com.kodz.unjenkins.client.helper.DaemonMonitor;
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

    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();
        int serverPort = Configuration.Setting.getServicePort();
        startServer(serverPort);

    }

    private static void startServer(int serverPort) throws Exception {
        JenkinsConsumer.initializeClient();
        DaemonMonitor daemonMonitor = new DaemonMonitor();
        Main.serverPort = serverPort;
        Main.server = configureServer();
        server.setStopTimeout(3000L);
        server.start();
        server.join();
    }

    private static Server configureServer() {
        //Create config to tell Jetty to consume servlets in the right package
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.packages("com.kodz.unjenkins");
        //Enable Jackson pojo mapping instead of default Moxy library
        resourceConfig.register(JacksonFeature.class);

        //Initialize server objects and set the port
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(serverPort);
        server.addConnector(connector);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        //Tell Jetty to set the root path to domain only, i.e. example.com/ instead of example.com/somethingElse
        context.setContextPath("/");

        //Initialize REST servlets via the package set in the resourceConfig, Jetty will look for classes that extend Servlet
        //These child classes are actually created by proxy using a factory method
        /**
         * @see JenkinsConsumer
         * @see com.kodz.unjenkins.client.proxy.JenkinsResource
         */
        ServletContainer servletContainer = new ServletContainer(resourceConfig);
        ServletHolder restServlet = new ServletHolder(servletContainer);

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
}