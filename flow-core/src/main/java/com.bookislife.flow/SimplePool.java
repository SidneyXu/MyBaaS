package com.bookislife.flow;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by SidneyXu on 2016/05/05.
 */
public class SimplePool<T> {

    public interface SimplePoolFactory<T> {
        T create(String key);
    }

    private final int maxSize;
    private final long idleMs;
    private final SimplePoolFactory<T> factory;
    private ConcurrentHashMap<String, T> freeObjects = new ConcurrentHashMap<>();

    public SimplePool(SimplePoolFactory<T> factory, int maxSize, long idleMs) {
        this.maxSize = maxSize;
        this.idleMs = idleMs;
        this.factory = factory;
    }

    public void free(T t){

    }

    public T get(String key){
        return freeObjects.compute(key, new BiFunction<String, T, T>() {
            @Override
            public T apply(String s, T t) {
                // TODO: 5/5/16 
                return null;
            }
        });
    }
}
