package com.bookislife.flow;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by SidneyXu on 2016/05/01.
 */
public class ServerStarter extends AbstractVerticle {

    public static final Logger logger = LoggerFactory.getLogger(ServerStarter.class);

    private JsonObject config;
    private Injector injector;

    public static void main(String[] args) {
        Runner.runExample(ServerStarter.class);
    }

    @Override
    public void start() throws Exception {
        initConfig();

        Router router=Router.router(vertx);

    }

    private void initConfig() {
        // TODO: 16/5/4
        config = new JsonObject()
                .put("rest", new JsonObject()
                        .put("host", "127.0.0.1")
                        .put("port", 8080));
        injector= Guice.createInjector(new ServerModule(vertx,config));

    }
}
