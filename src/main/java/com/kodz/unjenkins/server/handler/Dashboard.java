package com.kodz.unjenkins.server.handler;

/**
 * Created by Kurt on 11/30/15.
 */
import com.kodz.unjenkins.server.JenkinsProducer;
import com.kodz.unjenkins.server.dto.Metric;
import com.kodz.unjenkins.server.dto.ViewQuery;
import com.kodz.unjenkins.server.responseFilters.CORS;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("api")
@CORS
public class Dashboard {

    @GET
    @Path("/view/{viewName}/view/{folderName}")
    @Produces(MediaType.APPLICATION_JSON)
    @CORS
    public Metric getSubView(@PathParam("viewName") String viewName, @PathParam("folderName") String folderName) throws Exception {

        ViewQuery viewQuery = new ViewQuery(viewName, folderName, "^((.*test)(?!.*prod)(?!.*common)(?!.*multiple)).*$");

        return JenkinsProducer.getMetric(viewQuery);
        
    }

    @GET
    @Path("/view/{viewName}")
    @Produces(MediaType.APPLICATION_JSON)
    @CORS
    public Metric getView(@PathParam("viewName") String viewName) throws Exception {

        ViewQuery viewQuery = new ViewQuery(viewName, "^((.*test)(?!.*prod)(?!.*common)(?!.*multiple)).*$");
        return JenkinsProducer.getMetric(viewQuery);

    }
}
