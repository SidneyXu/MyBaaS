package com.bookislife.flow;

import com.bookislife.flow.module.DataServiceModule;
import dagger.Module;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.Vertx;

import javax.inject.Inject;

/**
 * Created by SidneyXu on 2016/05/03.
 */
//public class ServerModule extends AbstractModule {
//
//    private final Vertx vertx;
//    private final JsonObject config;
//
//    public ServerModule(Vertx vertx, JsonObject config) {
//        this.vertx = vertx;
//        this.config = config;
//    }
//
//    @Override
//    protected void configure() {
//        bind(DataStorage.class).to(MongoDataStorage.class);
//        bind(DataServiceModule.class);
//
//    }
//}
@Module
public class ServerModule {
    private final Vertx vertx;
    private final JsonObject config;

    @Inject
    private DataServiceModule dataServiceModule;

    public ServerModule(Vertx vertx, JsonObject config) {
        this.vertx = vertx;
        this.config = config;
    }

}