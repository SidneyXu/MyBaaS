package com.bookislife.flow;

import com.bookislife.flow.handler.*;
import io.vertx.rxjava.ext.web.handler.BodyHandler;
import io.vertx.rxjava.ext.web.handler.CookieHandler;

import javax.inject.Inject;

/**
 * Created by SidneyXu on 2016/05/05.
 */
public class Middleware {

    private ExceptionHandler exceptionHandler;

    private ResponseTimeHandler responseTimeHandler;

    private StaticResourceHandler staticResourceHandler;

    private CrossDomainHandler crossDomainHandler;

    private RedirectHandler redirectHandler;

    @Inject
    public Middleware(ResponseTimeHandler responseTimeHandler) {
        this.responseTimeHandler = responseTimeHandler;
    }

    public ExceptionHandler getExceptionHandler() {
        return exceptionHandler;
    }

    public ResponseTimeHandler getResponseTimeHandler() {
        return responseTimeHandler;
    }

    public StaticResourceHandler getStaticResourceHandler() {
        return staticResourceHandler;
    }

    public CrossDomainHandler getCrossDomainHandler() {
        return crossDomainHandler;
    }

    public RedirectHandler getRedirectHandler() {
        return redirectHandler;
    }

    public CookieHandler getCookieHandler() {
        return CookieHandler.create();
    }

    public BodyHandler getBodyHandler() {
        return BodyHandler.create();
    }
}
