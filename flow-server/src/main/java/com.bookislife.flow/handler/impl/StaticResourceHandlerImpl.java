package com.bookislife.flow.handler.impl;

import com.bookislife.flow.handler.StaticResourceHandler;
import io.vertx.rxjava.core.http.HttpServerRequest;
import io.vertx.rxjava.ext.web.RoutingContext;

/**
 * Created by SidneyXu on 2016/06/02.
 */
public class StaticResourceHandlerImpl implements StaticResourceHandler {

    @Override
    public void handle(RoutingContext context) {
        HttpServerRequest request=context.request();
        // TODO: 16/6/2
    }
}
