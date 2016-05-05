package com.bookislife.flow.handler;

import com.google.inject.ImplementedBy;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

/**
 * Created by SidneyXu on 2016/05/05.
 */
@ImplementedBy(ResponseTimeHandler.class)
public interface ResponseTimeHandler extends Handler<RoutingContext> {
}
