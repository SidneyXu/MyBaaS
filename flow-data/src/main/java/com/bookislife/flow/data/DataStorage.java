package com.bookislife.flow.data;

import java.util.List;

/**
 * Created by SidneyXu on 2016/05/19.
 */
public interface DataStorage {

    BaseEntity findById(String database,
                        String tableName,
                        String id);

    List<BaseEntity> findAll(String database, String tableName, String query);

    BaseEntity insert(String database, String tableName, String data);
}
