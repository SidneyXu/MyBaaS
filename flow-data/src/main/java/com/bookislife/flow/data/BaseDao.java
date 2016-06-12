package com.bookislife.flow.data;

import com.bookislife.flow.domain.BaseEntity;
import com.bookislife.flow.exception.FlowException;

import java.util.List;

/**
 * Created by SidneyXu on 2016/04/29.
 */
public interface BaseDao {

    String insert(String database,
                  String tableName,
                  BaseEntity entity);

    int update(String database,
               String tableName,
               BaseQuery query,
               BaseModifier modifier) throws FlowException;

    BaseEntity insertOrUpdate();

    void batchInsert(String database, String tableName, List<? extends BaseEntity> entities);

    int deleteById(String database,
                   String tableName,
                   String id);

    int deleteAll(String database,
                  String tableName,
                  BaseQuery query) throws FlowException;

    BaseEntity findById(String database,
                        String tableName,
                        String id);

    BaseEntity findOne(String database, String tableName, BaseQuery query) throws FlowException;

    List<BaseEntity> findAll(String database, String tableName, BaseQuery query) throws FlowException;

    long count(String database,
               String tableName,
               BaseQuery query);
}
