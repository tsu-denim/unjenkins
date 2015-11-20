package unjenkins.proxy;

import unjenkins.dto.Job;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.net.URLEncoder;

import static java.net.URLEncoder.encode;

/**
 * Created by Kurt on 11/20/15.
 */

@Path("/job/{jobName}/api/json")
public interface IBuild {

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    Job getJob(@PathParam("jobName") String jobName, @QueryParam("tree") String json);

}
