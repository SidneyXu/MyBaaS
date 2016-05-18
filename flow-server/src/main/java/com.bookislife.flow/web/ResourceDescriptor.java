package com.bookislife.flow.web;


import com.google.common.collect.ImmutableList;

import javax.ws.rs.Consumes;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by SidneyXu on 2016/05/17.
 */
public class ResourceDescriptor {

    private Class<?> clazz;
    public final String path;
    public final ImmutableList<String> consumeType;
    public final ImmutableList<String> produceType;
    public final ImmutableList<Method> methods;

    public ResourceDescriptor(Class<?> clazz) {
        this.clazz = clazz;
        path = clazz.getAnnotation(Path.class).value();
        String[] consumes = clazz.getAnnotation(Consumes.class).value();
        String[] produces = clazz.getAnnotation(Produces.class).value();
        consumeType = ImmutableList.copyOf(consumes);
        produceType = ImmutableList.copyOf(produces);
        methods = ImmutableList.copyOf(collectMethods());
    }

    private List<Method> collectMethods() {
        PrivilegedAction<Method[]> action = () -> clazz.getMethods();
        Method[] methods = AccessController.doPrivileged(action);
        return Stream.of(methods)
                .filter(this::checkMethod)
                .collect(Collectors.toList());
    }

    private boolean checkMethod(Method method) {
        if (method.getAnnotation(Path.class) != null
                || method.getAnnotation(Consumes.class) != null
                || method.getAnnotation(Produces.class) != null)
            return true;

        return Stream.of(method.getAnnotations())
                .filter(annotation ->
                        annotation
                                .annotationType()
                                .getAnnotation(HttpMethod.class) != null)
                .findFirst()
                .isPresent();
    }
}
