package com.bookislife.flow;

import com.bookislife.flow.handler.ExceptionHandler;
import com.bookislife.flow.handler.ResponseTimeHandler;

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
}
