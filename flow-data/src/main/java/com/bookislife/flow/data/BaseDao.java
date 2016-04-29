package com.bookislife.flow.data;

import java.util.List;

/**
 * Created by SidneyXu on 2016/04/29.
 */
public interface BaseDao {

    BaseEntity insert();

    BaseEntity update();

    BaseEntity insertOrUpdate();

    int delete();

    BaseEntity findById();

    BaseEntity findOne();

    List<BaseEntity> findAll();

    long count();
}
