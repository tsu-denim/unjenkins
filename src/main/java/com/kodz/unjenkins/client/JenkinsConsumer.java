package com.kodz.unjenkins.client;

import com.kodz.unjenkins.client.helper.Configuration;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.client.proxy.WebResourceFactory;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import com.kodz.unjenkins.client.proxy.JenkinsResource;

import javax.net.ssl.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;

/**
 * Created by Kurt on 11/23/15.
 */
public class JenkinsConsumer {

    //Create Jersey client
    private static Client restClient = ClientBuilder.newClient();
    public static JenkinsResource jenkinsResource;

    public static void initializeClient() throws Exception {
        //Set authentication to basic, provide Jenkins username and api token
        restClient.register(HttpAuthenticationFeature.basic(Configuration.Setting.getJenkinsUserName(), Configuration.Setting.getJenkinsApiToken()));
        //Tell Jersey to use superior Jackson json mapping instead of default Moxy
        restClient.register(JacksonFeature.class);

        //Enable http logging to the console
        //TODO: This is deprecated, change to restClient.register(LoggingFeature.class)
        //TODO: Come up with way to toggle this logging based on system setting at both BUILD and/or RUNTIME if possible
        //restClient.register(new LoggingFilter());

        jenkinsResource = WebResourceFactory.newResource(JenkinsResource.class,
                restClient.target("http://" + Configuration.Setting.getRemoteJenkinsHostDomain() + ":" + Configuration.Setting.getRemoteJenkinsHostPort()));
    }

    public static String getResourceFilePath(String relativeFilePath) {

        //Get file from resources folder
        ClassLoader classLoader = JenkinsConsumer.class.getClassLoader();
        String file = classLoader.getResource(relativeFilePath).getPath();
        System.out.println(file);
        return file;

    }



}
