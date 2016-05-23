package com.bookislife.flow.resource;

import com.bookislife.flow.Env;
import com.bookislife.flow.data.BaseEntity;
import com.bookislife.flow.data.DataStorage;
import com.bookislife.flow.data.MongoDocument;
import com.bookislife.flow.data.MongoEntity;
import com.bookislife.flow.utils.JacksonJsonBuilder;
import com.bookislife.flow.utils.ResponseCreator;
import io.vertx.rxjava.core.http.HttpServerRequest;
import io.vertx.rxjava.ext.web.RoutingContext;
import org.bson.Document;

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
        HttpServerRequest request = context.request();
        String tableName = request.getParam("className");
        String databaseName = request.getHeader(Env.Header.APPLICATION_ID);
        String bodyString = context.getBodyAsString();
        BaseEntity entity = dataStorage.insert(databaseName, tableName, bodyString);
        context.response().end(ResponseCreator.newCreateResponse(entity));
    }

    @GET
    @Path(":className/:objectId")
    public void get(RoutingContext context) {
        HttpServerRequest request = context.request();
        String tableName = request.getParam("className");
        String objectId = request.getParam("objectId");
        String databaseName = request.getHeader(Env.Header.APPLICATION_ID);
        BaseEntity entity = dataStorage.findById(databaseName, tableName, objectId);
        context.response().end(ResponseCreator.newQueryResponse(entity));
    }
}
