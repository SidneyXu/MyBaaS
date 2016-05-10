package com.bookislife.flow.handler.impl;

import com.bookislife.flow.Env;
import com.bookislife.flow.handler.ResponseTimeHandler;
import io.vertx.rxjava.ext.web.RoutingContext;

/**
 * Created by SidneyXu on 2016/05/05.
 */
public class ResponseTimeHandlerImpl implements ResponseTimeHandler {
    @Override
    public void handle(RoutingContext context) {
        long start = System.currentTimeMillis();
        context.addHeadersEndHandler(event -> {
            long duration = System.currentTimeMillis() - start;
            context.response().putHeader(Env.Header.RESPONSE_TIME, duration + "ms");
        });
        context.next();
    }
}
