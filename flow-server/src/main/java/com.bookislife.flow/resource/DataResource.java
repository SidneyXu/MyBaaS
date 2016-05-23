package com.bookislife.flow.resource;

import com.bookislife.flow.Env;
import com.bookislife.flow.data.BaseEntity;
import com.bookislife.flow.data.DataStorage;
import com.bookislife.flow.data.MongoDocument;
import com.bookislife.flow.data.MongoEntity;
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
        System.out.println("111233");


        HttpServerRequest request= context.request();
        String tableName = request.getParam("className");
        String databaseName=request.getHeader(Env.Header.APPLICATION_ID);

//        dataStorage.findById();
        Document doc = new Document("name", "MongoDB")
                .append("type", "database")
                .append("count", 1)
                .append("info", new Document("x", 203).append("y", 102));
        MongoDocument mongoDocument = new MongoDocument(doc);

        String objectId=dataStorage.insert(databaseName, tableName,mongoDocument );

        context.response().end(objectId);
    }

    @GET
    @Path(":className/:objectId")
    public void get(RoutingContext context) {
        System.out.println("2252525");

    }
}
