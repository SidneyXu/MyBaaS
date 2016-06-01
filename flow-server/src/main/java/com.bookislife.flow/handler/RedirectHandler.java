package com.bookislife.flow.handler;

import com.bookislife.flow.handler.impl.RedirectHandlerImpl;
import com.google.inject.ImplementedBy;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

/**
 * Created by SidneyXu on 2016/06/02.
 */
@ImplementedBy(RedirectHandlerImpl.class)
public interface RedirectHandler extends Handler<RoutingContext> {
}
