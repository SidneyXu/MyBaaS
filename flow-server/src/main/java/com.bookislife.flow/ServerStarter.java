package com.bookislife.flow;

import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.AbstractVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by SidneyXu on 2016/05/01.
 */
public class ServerStarter extends AbstractVerticle {

    public static final Logger logger= LoggerFactory.getLogger(ServerStarter.class);

    private JsonObject config;

    public static void main(String[] args) {
        Runner.runExample(ServerStarter.class);
    }

    @Override
    public void start() throws Exception {
        initConfig();
    }

    private void initConfig() {
        config=new JsonObject();

    }
}
