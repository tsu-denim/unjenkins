package com.kodz.unjenkins.server.endpoints.http;

/**
 * Created by Kurt on 11/30/15.
 */
import com.kodz.unjenkins.Main;
import com.kodz.unjenkins.server.dto.Metric;
import com.kodz.unjenkins.server.dto.StopServerResponse;
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
import java.io.IOException;

@Path("api")
@CORS
public class StopServerProxy {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("stop/{name}")
    @CORS
    public Response repoPushed(@PathParam("name") String name) throws IOException, InterruptedException {
        StopServerResponse response = new StopServerResponse();
        if (name.equals("90210")){
            try {
                Main.stopServer();
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.setMessage("Shutdown initiated, server will stop in 5 seconds.");
        }
        else {
            response.setMessage("Invalid shutdown code, server will remain running.");
        }

        return Response.ok().entity(response).build();
    }


}