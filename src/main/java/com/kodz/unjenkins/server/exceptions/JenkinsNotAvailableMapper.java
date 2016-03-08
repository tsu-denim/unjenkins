package com.kodz.unjenkins.server.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by Kurt on 3/8/16.
 */
@Provider
public class JenkinsNotAvailableMapper implements
        ExceptionMapper<JenkinsNotAvailable> {

    @Override
    public Response toResponse(JenkinsNotAvailable jenkinsNotAvailable) {
        return Response.status(502).entity(jenkinsNotAvailable.getJenkinsMessage()).build();
    }
}

