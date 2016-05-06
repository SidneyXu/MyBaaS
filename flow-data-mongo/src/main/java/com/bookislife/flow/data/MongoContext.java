package com.bookislife.flow.data;

import com.bookislife.flow.Environment;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.mongodb.MongoClient;

import javax.inject.Singleton;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by SidneyXu on 2016/05/03.
 */
@Singleton
public class MongoContext {

    private Cache<String, MongoClient> cache;
    private long cleanerInterval;

    public MongoContext() {
        long expires = Long.parseLong(System.getProperty(Environment.Config.DB_CONNECTION_EXPIRES,
                "" + Environment.Default.dbConnectionExpires));
        cleanerInterval = Long.parseLong(System.getProperty(Environment.Config.DB_CLEANER_INTERVAL,
                "" + Environment.Default.dbCleanerInterval));

        cache = CacheBuilder.newBuilder()
                .expireAfterAccess(expires, TimeUnit.MILLISECONDS)
                .build();
        new Cleaner().start();
    }

    public MongoClient getClient(MongoClientOptions options) {
        try {
            return cache.get(options.getConnectionUrl(), new Callable<MongoClient>() {
                @Override
                public MongoClient call() throws Exception {
                    return new MongoClient(options.getServerAddress());
                }
            });
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    class Cleaner extends Thread {
        @Override
        public void run() {
            while (true) {
                cache.cleanUp();
                try {
                    sleep(cleanerInterval);
                } catch (InterruptedException ignored) {
                }
            }
        }
    }
}
