package com.kodz.unjenkins.client.proxy;

import com.kodz.unjenkins.client.dto.ConnectResponse;
import com.kodz.unjenkins.client.dto.DisconnectResponse;
import com.kodz.unjenkins.client.dto.HealthCheck;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by master on 1/21/2016.
 */
public interface DeploymentBuddyResource {

    @GET
    @Path("/healthCheck")
    @Consumes(MediaType.APPLICATION_JSON)
    HealthCheck getHealthCheck();

    @GET
    @Path("/connect")
    @Consumes(MediaType.APPLICATION_JSON)
    ConnectResponse getConnectResponse();

    @GET
    @Path("/disconnect")
    @Consumes(MediaType.APPLICATION_JSON)
    DisconnectResponse getDisconnectResponse();
}
