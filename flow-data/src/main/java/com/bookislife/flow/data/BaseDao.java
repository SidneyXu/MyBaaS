package com.bookislife.flow.data;

import rx.Observable;

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

    int delete();

    BaseEntity findById();

    BaseEntity findOne();

    List<BaseEntity> findAll();

    long count();
}
