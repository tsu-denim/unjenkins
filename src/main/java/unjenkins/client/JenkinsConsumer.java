package unjenkins.client;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.client.proxy.WebResourceFactory;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import unjenkins.client.proxy.JenkinsResource;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.io.IOException;

/**
 * Created by Kurt on 11/23/15.
 */
public class JenkinsConsumer {

    //Create Jersey client
    private static Client restClient = ClientBuilder.newClient();
    public static JenkinsResource jenkinsResource;

    public static void initializeClient() throws IOException {

        //Required for accessing https connection using self signed ssl cert
        //Prereq to avoid runtime error: Use cmd line program 'keytool' to configure local machine
        System.setProperty("javax.net.ssl.trustStore", "/repos/javakeys");
        System.setProperty("javax.net.ssl.trustStorePassword", "javakeys");

        //Set authentication to basic, provide Jenkins username and api token
        restClient.register(HttpAuthenticationFeature.basic("kurt.waechter@inin.com", "55079fb68d9018d7a57d0633161a415a"));
        //Tell Jersey to use superior Jackson json mapping instead of default Moxy
        restClient.register(JacksonFeature.class);
        //Enable http logging to the console
        restClient.register(new LoggingFilter());

        jenkinsResource = WebResourceFactory.newResource(JenkinsResource.class,
                restClient.target("https://jenkins.inintca.com:8443"));


    }

    //public static get view information

    //public static get job information

    //public static get build info




}
