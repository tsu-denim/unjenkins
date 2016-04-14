package com.kodz.unjenkins.server.endpoints.http;

/**
 * Created by Kurt on 11/30/15.
 */
import com.kodz.unjenkins.server.dto.Metric;
import com.kodz.unjenkins.server.dto.ViewQuery;
import com.kodz.unjenkins.server.dto.gimpy.GimpyQuery;
import com.kodz.unjenkins.server.dto.gimpy.GimpyQueryResponse;
import com.kodz.unjenkins.server.dto.gimpy.Report;
import com.kodz.unjenkins.server.responseFilters.CORS;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("api")
@CORS
public class MetricProxy {

    @GET
    @Path("/view/{viewName}/view/{folderName}")
    @Produces(MediaType.APPLICATION_JSON  + "; charset=UTF-8")
    @CORS
    public Metric getSubView(@PathParam("viewName") String viewName, @PathParam("folderName") String folderName) throws Exception {

        ViewQuery viewQuery = new ViewQuery(viewName, folderName, "^((.*test)(?!.*prod)(?!.*common)(?!.*multiple)).*$");

        return MetricProvider.getMetric(viewQuery);

    }

    @GET
    @Path("/view/{viewName}")
    @Produces(MediaType.APPLICATION_JSON  + "; charset=UTF-8")
    @CORS
    public Metric getView(@PathParam("viewName") String viewName) throws Exception {
        ViewQuery viewQuery = new ViewQuery(viewName, "^((.*test)(?!.*prod)(?!.*common)(?!.*multiple)).*$");
        return MetricProvider.getMetric(viewQuery);

    }


    @POST
    @Path("/tcdb")
    @Produces(MediaType.APPLICATION_JSON  + "; charset=UTF-8")
    @Consumes(MediaType.APPLICATION_JSON  + "; charset=UTF-8")
    @CORS
    public Response postQuery(GimpyQuery gimpyQuery) throws Exception {
        GimpyQueryResponse gimpyQueryResponse = GimpyProvider.postQuery(gimpyQuery);
        return Response.accepted(gimpyQueryResponse).build();

    }

    @GET
    @Path("/tcdb/{reportId}")
    @Produces(MediaType.APPLICATION_JSON  + "; charset=UTF-8")
    @Consumes(MediaType.APPLICATION_JSON  + "; charset=UTF-8")
    @CORS
    public Report getReport(@PathParam("reportId") String reportId) throws Exception {

        Report report = GimpyProvider.getReport(reportId);
        return report;
    }

}
