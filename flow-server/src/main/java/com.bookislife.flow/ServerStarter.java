package com.bookislife.flow;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.ext.web.Route;
import io.vertx.rxjava.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by SidneyXu on 2016/05/01.
 */
public class ServerStarter extends AbstractVerticle {

    public static final Logger logger = LoggerFactory.getLogger(ServerStarter.class);

    private JsonObject config;
    private Injector injector;

    public static void main(String[] args) {
        Runner.runExample(ServerStarter.class);
    }

    @Override
    public void start() throws Exception {
        initConfig();

        Router router = Router.router(vertx);
        registerGlobalHandler(router);

        HttpServerOptions options=new HttpServerOptions();
        vertx.createHttpServer(options)
                .requestHandler(router::accept)
                // TODO: 16/5/16
                .listen(10086);
    }

    private void registerGlobalHandler(Router router) {
        Route route = router.route();
        Middleware middleware = injector.getInstance(Middleware.class);
        route
                .failureHandler(middleware.getExceptionHandler())
                .handler(middleware.getExceptionHandler())
                .handler(middleware.getCookieHandler())
                .handler(middleware.getBodyHandler());
    }

    private void registerResourceHandler(Router router){

    }


    private void initConfig() {
        // TODO: 16/5/4
        config = new JsonObject()
                .put("rest", new JsonObject()
                        .put("host", "127.0.0.1")
                        .put("port", 8080)
                        .put("dialect", "mongo"));

        // ioc
        injector = Guice.createInjector(new ServerModule(vertx, config));


    }

}
