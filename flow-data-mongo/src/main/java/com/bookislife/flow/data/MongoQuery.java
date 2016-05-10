package com.bookislife.flow.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SidneyXu on 2016/05/05.
 */
public class MongoQuery implements BaseQuery{

    private Map<String,Object> query = new HashMap<>();

    public Map<String, Object> getQuery() {
        return query;
    }
}
