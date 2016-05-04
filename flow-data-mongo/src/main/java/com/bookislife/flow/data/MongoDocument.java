package com.bookislife.flow.data;

import io.vertx.core.json.JsonObject;

/**
 * Created by SidneyXu on 2016/05/04.
 */
public class MongoDocument extends BaseEntity {

    private JsonObject jsonObject;

    public JsonObject toJsonObject() {
        return jsonObject.copy();
    }
}
