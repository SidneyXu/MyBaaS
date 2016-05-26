package com.bookislife.flow.data;

import com.bookislife.flow.exception.FlowException;

import java.util.List;

/**
 * Created by SidneyXu on 2016/05/19.
 */
public interface DataStorage {

    BaseEntity findById(String database,
                        String tableName,
                        String id) throws FlowException;

    List<BaseEntity> findAll(String database, String tableName, String query) throws FlowException;

    BaseEntity insert(String database, String tableName, String data);

    int delete(String database, String tableName, String id);

    long count(String database, String tableName);
}
