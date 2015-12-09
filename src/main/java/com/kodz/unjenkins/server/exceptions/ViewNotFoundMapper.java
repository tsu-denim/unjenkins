package com.kodz.unjenkins.server.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by Kurt on 12/9/15.
 */
@Provider
public class ViewNotFoundMapper implements
        ExceptionMapper<ViewNotFound> {
    @Override
    public Response toResponse(ViewNotFound ex) {
        return Response.status(404).entity(ex.getViewMessage()).build();
    }
}