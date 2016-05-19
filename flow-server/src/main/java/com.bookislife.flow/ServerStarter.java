package com.bookislife.flow;

import com.bookislife.flow.web.ResourceDescriptor;
import com.bookislife.flow.web.ResourceLoader;
import com.bookislife.flow.web.ResourceResolver;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.ext.web.Route;
import io.vertx.rxjava.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

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
        registerResourceHandler(router);

        HttpServerOptions options = new HttpServerOptions();
        vertx.createHttpServer(options)
                .requestHandler(router::accept)
                // TODO: 16/5/16
                .listen(10086);
    }

    private void registerGlobalHandler(Router router) {
        Middleware middleware = injector.getInstance(Middleware.class);
        router.route().failureHandler(middleware.getExceptionHandler());
        router.route().handler(middleware.getResponseTimeHandler());
        router.route().handler(middleware.getCookieHandler());
        router.route().handler(middleware.getBodyHandler());
    }

    private void applyRoute(Route route, ResourceDescriptor cd, ResourceDescriptor md) {
        if (null == md.path) {
            route.path(cd.path);
        } else {
            route.path("/".equals(cd.path) ? md.path + md.path : cd.path + "/" + md.path);
        }
        if (null != md.httpMethod) {
            route.method(HttpMethod.valueOf(md.httpMethod));
        }
        if (null != md.consumeType) {
            md.consumeType.forEach(route::consumes);
        } else if (null != cd.consumeType) {
            cd.consumeType.forEach(route::consumes);
        }
        if (null != md.produceType) {
            md.produceType.forEach(route::produces);
        } else if (null != cd.produceType) {
            cd.produceType.forEach(route::produces);
        }
    }

    private void registerResourceHandler(Router rootRouter) {
        ResourceLoader resourceLoader = new ResourceLoader(ServerStarter.class.getClassLoader());
        Set<Class<?>> classSet = resourceLoader.scanPackage("com.bookislife.flow.resource");
        classSet.stream()
                .map(ResourceResolver::resolveResource)
                .forEach(resource -> {
                    ResourceDescriptor clazzDescriptor = resource.getClassDescriptor();
                    resource.getMethodDescriptorList().forEach(methodDescriptor -> {
                        Object singleton = injector.getInstance(resource.clazz);
                        Route route = rootRouter.route();
                        applyRoute(route, clazzDescriptor, methodDescriptor);
                        
                        route.handler(ctx -> {
                            System.out.println(Thread.currentThread().getName());
                            Method method = methodDescriptor.method;
                            // TODO: 5/19/16 interceptor
                            try {
                                assert method != null;
                                method.invoke(singleton, ctx);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                // TODO: 5/19/16
                                e.printStackTrace();
                            }
                        });
                    });
                });
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
