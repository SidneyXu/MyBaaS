package com.bookislife.flow.resource;

import io.vertx.rxjava.ext.web.RoutingContext;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * Created by SidneyXu on 2016/05/12.
 */
@Singleton
@Path("/classes")
public class DataResource {

    @POST
    @Path(":className")
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(RoutingContext context) {

    }

    @GET
    @Path(":className/:objectId")
    public void get(RoutingContext context) {

    }
}
