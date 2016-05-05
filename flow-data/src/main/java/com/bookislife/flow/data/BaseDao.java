package com.bookislife.flow.data;

import java.util.List;

/**
 * Created by SidneyXu on 2016/04/29.
 */
public interface BaseDao {

    String insert(String database,
                  String tableName,
                  BaseEntity entity);

    BaseEntity update();

    BaseEntity insertOrUpdate();

    void batchInsert(String database, String tableName, List<BaseEntity> entities);

    int deleteById(String database,
                   String tableName,
                   String id);

    BaseEntity findById(String database,
                        String tableName,
                        String id);

    BaseEntity findOne();

    List<BaseEntity> findAll();

    long count(String database,
               String tableName,
               BaseQuery query);
}
