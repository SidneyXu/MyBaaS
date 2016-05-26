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
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

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

    @DELETE
    @Path(":className/:objectId")
    public void delete(RoutingContext context){
        HttpServerRequest request = context.request();
        String tableName = request.getParam("className");
        String objectId = request.getParam("objectId");
        String databaseName = request.getHeader(Env.Header.APPLICATION_ID);
        int n = dataStorage.delete(databaseName,tableName,objectId);

        // TODO: 16/5/26
    }

    @POST
    @Path(":className/batch")
    public void batch(RoutingContext context){

    }

    @PUT
    @Path(":className/:objectId")
    public void update(RoutingContext context) {
        HttpServerRequest request = context.request();
        String tableName = request.getParam("className");
        String objectId = request.getParam("objectId");

        // TODO: 5/25/16
    }

    @POST
    @Path(":className/query")
    @Consumes(MediaType.APPLICATION_JSON)
    public void findAll(RoutingContext context) {
        HttpServerRequest request = context.request();
        String databaseName = request.getHeader(Env.Header.APPLICATION_ID);
        String tableName = request.getParam("className");
        String query = context.getBodyAsString();
        List<BaseEntity> entities = dataStorage.findAll(databaseName, tableName, query);

        // TODO: 5/25/16
    }

}
