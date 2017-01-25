package com.kodz.unjenkins.server.endpoints.http;

/**
 * Created by Kurt on 11/30/15.
 */
import com.kodz.unjenkins.server.dto.Metric;
import com.kodz.unjenkins.server.dto.ViewQuery;
import com.kodz.unjenkins.server.responsefilters.CORS;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

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


}
