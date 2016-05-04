package com.bookislife.flow.data;

import com.bookislife.flow.Environment;
import com.bookislife.flow.IOUtils;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.ext.mongo.MongoClient;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by SidneyXu on 2016/05/03.
 */
public class MongoContext {

    private final Vertx vertx;
    private final JsonObject config;
    private ConcurrentHashMap<String, JsonObject> configMap = new ConcurrentHashMap<>();

    public MongoContext(Vertx vertx) {
        this.vertx = vertx;
        String json = System.getProperty(Environment.Config.MONGO_CONFIG_PROP_NAME);
        if (null == json) {
            json = IOUtils.loadResource(MongoContext.class, Environment.Config.MONGO_CONFIG_FILE_NAME);
        }
        this.config = new JsonObject(json);
    }

    private JsonObject loadConfig(String dataSource) {
        return configMap.computeIfAbsent(dataSource, s -> {
            String json = System.getProperty(Environment.Config.MONGO_CONFIG_PROP_NAME);
            if (null == json) {
                json = IOUtils.loadResource(MongoContext.class,
                        Environment.Config.MONGO_CONFIG_FILE_NAME);
            }
            return new JsonObject(json);
        });
    }

    public MongoClient getClient(String dataSource) {
        JsonObject config = loadConfig(dataSource);
        return MongoClient.createShared(vertx, config, dataSource);
    }
}
