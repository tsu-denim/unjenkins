package com.kodz.unjenkins.client;

import com.kodz.unjenkins.client.helper.Configuration;
import com.kodz.unjenkins.client.proxy.DeploymentBuddyResource;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.client.proxy.WebResourceFactory;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

/**
 * Created by Kurt on 11/23/15.
 */
public class DeploymentBuddyConsumer {

    //Create Jersey client
    private static Client restClient = ClientBuilder.newClient();
    public static DeploymentBuddyResource deploymentBuddyResource;

    public static void initializeClient() throws Exception {
        //Tell Jersey to use superior Jackson json mapping instead of default Moxy
        restClient.register(JacksonFeature.class);
        //Enable http logging to the console
        //restClient.register(new LoggingFilter());

       deploymentBuddyResource = WebResourceFactory.newResource(DeploymentBuddyResource.class,
                restClient.target("http://" + Configuration.Setting.getRemoteHostDomainForHealthCheck() + ":"
                        + Configuration.Setting.getRemoteHostPortForHealthCheck()));

    }
}
