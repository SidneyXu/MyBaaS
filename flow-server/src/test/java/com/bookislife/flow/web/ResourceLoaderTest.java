package com.bookislife.flow.web;

import com.bookislife.flow.ServerModule;
import com.bookislife.flow.ServerStarter;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.ext.web.Route;
import io.vertx.rxjava.ext.web.Router;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.model.TestClass;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by SidneyXu on 2016/05/16.
 */
public class ResourceLoaderTest {

    private ResourceLoader loader;
    private String packageName = "com.bookislife.flow.resource";
    private String jarPackageName = "org.slf4j";

    @Before
    public void setup() {
        ClassLoader classLoader = ResourceLoader.class.getClassLoader();
        loader = new ResourceLoader(classLoader);
    }

    @Test
    public void testScanURL() throws Exception {
        Set<Class<?>> classSet = loader.scanPackage(packageName);
        classSet.forEach(System.out::println);
    }

    @Test
    public void testScanJar() throws Exception {
        Set<Class<?>> classSet = loader.scanPackage(jarPackageName);
        classSet.forEach(System.out::println);
    }

    @Test
    public void testCore() throws Exception {
        Set<Class<?>> classSet = loader.scanPackage(packageName);

    }

    @Test
    public void testPackage() throws Exception {
        String packageName = "com.bookislife.flow.resource";
        String path = packageName.replace('.', '/');
//        System.out.println(path);

        ClassLoader classLoader = ResourceLoader.class.getClassLoader();
        ResourceLoader resourceLoader = new ResourceLoader(classLoader);

        classLoader = TestClass.class.getClassLoader();

        Enumeration<URL> urlEnumeration = classLoader.getResources(path);
        Set<Class<?>> classSet = new HashSet<>();
        while (urlEnumeration.hasMoreElements()) {
            URL url = urlEnumeration.nextElement();
            Set<String> strings = resourceLoader.scanURL(packageName, url);
            System.out.println(strings);
        }

        System.out.println(System.getProperties());

    }

    @Test
    public void test01() throws IOException {
        String packageName = "org.slf4j";
        String ppp = packageName.replace('.', '/');
        ClassLoader classLoader = ResourceLoader.class.getClassLoader();

        Enumeration<URL> urlEnumeration = classLoader.getResources(ppp);
        Set<Class<?>> classSet = new HashSet<>();
        while (urlEnumeration.hasMoreElements()) {
            URL url = urlEnumeration.nextElement();
//            System.out.println(url);

            String externalFrom = url.toExternalForm();
            String jarPath = externalFrom.substring(externalFrom.indexOf("file:") + 5);
            jarPath = jarPath.substring(0, jarPath.indexOf('!'));

//            System.out.println(jarPath);
//            System.out.println(URLDecoder.decode(jarPath,"utf-8"));

            Set<String> set = loader.
                    scanURL(jarPackageName, url);
            System.out.println(set);
        }

    }

    @Test
    public void test02() throws IOException, InterruptedException {
        //http://localhost:10086/classes/:className/:objectId
        Vertx vertx=Vertx.vertx();
        JsonObject config = new JsonObject()
                .put("rest", new JsonObject()
                        .put("host", "127.0.0.1")
                        .put("port", 8080)
                        .put("dialect", "mongo"));
        Injector injector = Guice.createInjector(new ServerModule(vertx, config));
        Router rootRouter = Router.router(vertx);

        ResourceLoader resourceLoader = new ResourceLoader(ServerStarter.class.getClassLoader());
        Set<Class<?>> classSet = resourceLoader.scanPackage("com.bookislife.flow.resource");
        classSet.stream()
                .map(ResourceResolver::resolveResource)
                .forEach(resource -> {
                    ResourceDescriptor clazzDescriptor = resource.getClassDescriptor();
                    resource.getMethodDescriptorList().forEach(md->{
                        Object singleton = injector.getInstance(resource.clazz);
                        System.out.println(singleton);

                        Route route = rootRouter.route();
                        String routePath;
                        if (null == md.path) {
                            routePath=clazzDescriptor.path;
                        } else {
                            routePath= "/".equals(clazzDescriptor.path) ? clazzDescriptor.path + md.path : clazzDescriptor.path + "/" + md.path;
                        }
                        System.out.println(routePath);
                        route.path(routePath);

                        if (null != md.httpMethod) {
                            route.method(HttpMethod.valueOf(md.httpMethod));
                        }
                        if (null != md.consumeType) {
                            md.consumeType.forEach(route::consumes);
                        } else if (null != clazzDescriptor.consumeType) {
                            clazzDescriptor.consumeType.forEach(route::consumes);
                        }
                        if (null != md.produceType) {
                            md.produceType.forEach(route::produces);
                        } else if (null != clazzDescriptor.produceType) {
                            clazzDescriptor.produceType.forEach(route::produces);
                        }

                        route.handler(ctx -> {
                            System.out.println(Thread.currentThread().getName());
                            Method method = md.method;
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

        HttpServerOptions options = new HttpServerOptions();
        vertx.createHttpServer(options)
                .requestHandler(rootRouter::accept)
                // TODO: 16/5/16
                .listen(10086);

        Thread.sleep(100_1000);
    }
}