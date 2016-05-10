package com.bookislife.flow;

import com.bookislife.flow.module.DataServiceModule;
import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.Module;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.Vertx;
import sun.security.pkcs11.Secmod;

/**
 * Created by SidneyXu on 2016/05/03.
 */
public class ServerModule extends AbstractModule {

    private final Vertx vertx;
    private final JsonObject config;

    public ServerModule(Vertx vertx, JsonObject config) {
        this.vertx = vertx;
        this.config = config;
    }

    @Override
    protected void configure() {
        bind(DataServiceModule.class);
    }
}
