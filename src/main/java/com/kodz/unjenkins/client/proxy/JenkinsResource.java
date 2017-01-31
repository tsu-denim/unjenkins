package com.kodz.unjenkins.client.proxy;

import com.kodz.unjenkins.client.dto.View;
import com.kodz.unjenkins.client.dto.BuildDetail;
import com.kodz.unjenkins.client.dto.JobStats;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * Created by Kurt on 11/20/15.
 */


public interface JenkinsResource {

    @GET
    @Path("/job/{jobName}/api/json")
    @Consumes(MediaType.APPLICATION_JSON)
    JobStats getJob(@PathParam("jobName") String jobName, @QueryParam("tree") String json);


    @GET
    @Path("/job/{jobName}/build")
    @Consumes(MediaType.TEXT_PLAIN)
    Response getNewBuild(@PathParam("jobName") String jobName, @QueryParam("delay") String delay);

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
