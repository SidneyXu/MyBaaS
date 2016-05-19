package com.bookislife.flow.resource;

import com.bookislife.flow.data.DataStorage;
import io.vertx.rxjava.ext.web.RoutingContext;

import javax.inject.Inject;
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

    private DataStorage dataStorage;

    @Inject
    public DataResource(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    @POST
    @Path(":className")
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(RoutingContext context) {
        System.out.println("111233");
    }

    @GET
    @Path(":className/:objectId")
    public void get(RoutingContext context) {
        System.out.println("2252525");
    }
}
