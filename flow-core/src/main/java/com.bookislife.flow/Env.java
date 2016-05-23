package com.bookislife.flow;

/**
 * Created by SidneyXu on 2016/05/04.
 */
public interface Env {

    public interface Config {
        String MONGO_CONFIG_FILE_NAME = "mongo-config.json";
        String MONGO_CONFIG_PROP_NAME = "mongo.config";
        String DB_CLEANER_INTERVAL = "flow.db.cleaner.interval";
        String DB_CONNECTION_EXPIRES = "flow.db.connection.expires";
    }

    public interface Header {
        String RESPONSE_TIME = "x-flow-response-time";
        String APPLICATION_ID= "x-flow-application-id";

    }

    public interface Default {
        long dbCleanerInterval = 600_000;
        long dbConnectionExpires = 18_000_000;
    }

}
