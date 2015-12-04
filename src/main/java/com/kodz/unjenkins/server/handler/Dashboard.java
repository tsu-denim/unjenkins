package com.kodz.unjenkins.server.handler;

/**
 * Created by Kurt on 11/30/15.
 */
import com.kodz.unjenkins.server.JenkinsDataProvider;
import com.kodz.unjenkins.server.dto.Metric;
import com.kodz.unjenkins.server.dto.ViewQuery;
import com.kodz.unjenkins.server.responseFilters.CORS;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("api")
public class Dashboard {

    @GET
    @Path("/dashboard")
    @Produces(MediaType.APPLICATION_JSON)
    @CORS
    public Metric getDashboard() throws Exception {

        ViewQuery viewQuery = new ViewQuery("Content Management", "CM API and UI", "^((.*test)(?!.*prod)(?!.*common)(?!.*multiple)).*$");

        return JenkinsDataProvider.getMetric(viewQuery);


    }
}
