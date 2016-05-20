package com.bookislife.flow.data;

/**
 * Created by SidneyXu on 2016/05/19.
 */
public interface DataStorage {

    BaseEntity findById(String database,
             String tableName,
             String id);
}
