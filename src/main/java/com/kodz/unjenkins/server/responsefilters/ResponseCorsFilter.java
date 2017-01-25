package com.kodz.unjenkins.server.responsefilters;

/**
 * Created by Kurt on 12/4/15.
 */
import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class ResponseCorsFilter implements ContainerResponseFilter{

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException{
        responseContext.getHeaders().putSingle("Access-Control-Allow-Origin", "*");
        responseContext.getHeaders().putSingle("Access-Control-Allow-Credentials", "true");
        responseContext.getHeaders().putSingle("Access-Control-Allow-Methods", "GET, OPTIONS, POST, DELETE, PUT");
        responseContext.getHeaders().putSingle("Access-Control-Allow-Headers", "Content-Type, Accept");
    }

}