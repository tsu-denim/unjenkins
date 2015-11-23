package unjenkins.proxy;

import unjenkins.dto.BuildDetail;
import unjenkins.dto.JobStats;
import unjenkins.dto.View;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


/**
 * Created by Kurt on 11/20/15.
 */


public interface JenkinsResource {

    @GET
    @Path("/job/{jobName}/api/json")
    @Consumes(MediaType.APPLICATION_JSON)
    JobStats getJob(@PathParam("jobName") String jobName, @QueryParam("tree") String json);

    @GET
    @Path("/job/{jobName}/{jobId}/api/json")
    @Consumes(MediaType.APPLICATION_JSON)
    BuildDetail getBuildDetail(@PathParam("jobName") String jobName, @PathParam("jobId") Integer jobId, @QueryParam("tree") String json);

    @GET
    @Path("/view/{viewName}/api/json")
    @Consumes(MediaType.APPLICATION_JSON)
    View getView(@PathParam("viewName") String viewName);

    @GET
    @Path("/view/{viewName}/view/{folderName}/api/json")
    @Consumes(MediaType.APPLICATION_JSON)
    View getSubView(@PathParam("viewName") String viewName, @PathParam("folderName") String folderName);

}
