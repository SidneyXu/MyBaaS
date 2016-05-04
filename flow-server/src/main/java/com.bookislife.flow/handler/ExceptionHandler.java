package com.bookislife.flow.handler;

import com.bookislife.flow.handler.impl.ExceptionHandlerImpl;
import com.google.inject.ImplementedBy;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

/**
 * Created by SidneyXu on 2016/05/04.
 */
@ImplementedBy(ExceptionHandlerImpl.class)
public interface ExceptionHandler extends Handler<RoutingContext> {
}
