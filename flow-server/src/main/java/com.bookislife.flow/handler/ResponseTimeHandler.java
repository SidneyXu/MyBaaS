package com.bookislife.flow.handler;

import com.bookislife.flow.handler.impl.ResponseTimeHandlerImpl;
import com.google.inject.ImplementedBy;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

/**
 * Created by SidneyXu on 2016/05/05.
 */
@ImplementedBy(ResponseTimeHandlerImpl.class)
public interface ResponseTimeHandler extends Handler<RoutingContext> {
}
