package com.kodz.unjenkins;

import com.kodz.unjenkins.client.JenkinsConsumer;
import org.eclipse.jetty.server.Server;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 * Created by Kurt on 11/16/15.
 */
public class Main {

    private static final int DEFAULT_PORT = 8080;
    private static int serverPort;
    private static Server server ;

    private static void startServer(int serverPort) throws Exception {
        JenkinsConsumer.initializeClient();
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
        ServletHolder sh = new ServletHolder(servletContainer);
        Server server = new Server(serverPort);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.addServlet(sh, "/*");

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

        int serverPort = DEFAULT_PORT;

        if (args.length > 0) {

            if (args[0] == "stop") {
                Main.stopServer();
            } else if (args[0] != null) {
                try {
                    serverPort = Integer.parseInt(args[0]);
                    System.out.println("Server port is set to " + serverPort);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        startServer(serverPort);

    }

}