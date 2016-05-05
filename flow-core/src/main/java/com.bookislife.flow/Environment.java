package com.bookislife.flow;

/**
 * Created by SidneyXu on 2016/05/04.
 */
public class Environment {

    public interface Config {
        String MONGO_CONFIG_FILE_NAME = "mongo-config.json";
        String MONGO_CONFIG_PROP_NAME="mongo.config";
    }

    public interface Header{
        String RESPONSE_TIME="flow-response-time";
    }

}
