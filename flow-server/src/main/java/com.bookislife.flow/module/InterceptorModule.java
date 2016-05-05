package com.bookislife.flow.module;

import com.bookislife.flow.Runner;
import com.bookislife.flow.annotation.Logging;
import com.bookislife.flow.interceptor.LoggingInterceptor;
import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matcher;
import com.google.inject.matcher.Matchers;

/**
 * Created by SidneyXu on 2016/05/05.
 */
public class InterceptorModule extends AbstractModule {

    @Override
    protected void configure() {
//        Matchers.annotatedWith()
//        bindInterceptor(Matchers.inPackage(Runner.class.getPackage()),
//                Matchers.annotatedWith(Logging.class),
//                );
    }

    class ServerClassMatcher implements Matcher {

        @Override
        public boolean matches(Object o) {
            return false;
        }

        @Override
        public Matcher and(Matcher other) {
            return null;
        }

        @Override
        public Matcher or(Matcher other) {
            return null;
        }
    }

    class ServerMethodMatcher implements Matcher{

        @Override
        public boolean matches(Object o) {
            return false;
        }

        @Override
        public Matcher and(Matcher other) {
            return null;
        }

        @Override
        public Matcher or(Matcher other) {
            return null;
        }
    }
}
