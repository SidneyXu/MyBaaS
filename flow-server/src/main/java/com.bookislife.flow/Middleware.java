package com.bookislife.flow;

import com.bookislife.flow.handler.ExceptionHandler;
import com.bookislife.flow.handler.ResponseTimeHandler;
import io.vertx.rxjava.ext.web.handler.BodyHandler;
import io.vertx.rxjava.ext.web.handler.CookieHandler;

import javax.inject.Inject;

/**
 * Created by SidneyXu on 2016/05/05.
 */
public class Middleware {

    @Inject
    private ExceptionHandler exceptionHandler;

    @Inject
    private ResponseTimeHandler responseTimeHandler;

    public ExceptionHandler getExceptionHandler() {
        return exceptionHandler;
    }

    public ResponseTimeHandler getResponseTimeHandler() {
        return responseTimeHandler;
    }

    public CookieHandler getCookieHandler() {
        return CookieHandler.create();
    }

    public BodyHandler getBodyHandler() {
        return BodyHandler.create();
    }
}
