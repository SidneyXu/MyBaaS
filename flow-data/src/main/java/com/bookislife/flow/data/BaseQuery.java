package com.bookislife.flow.data;

/**
 * Created by SidneyXu on 2016/05/05.
 */
public class BaseQuery {

    private final String tableName;

    public BaseQuery(String tableName) {
        this.tableName = tableName;
    }

    public static BaseQuery from(String tableName) {
        return new BaseQuery(tableName);
    }
}
