package com.bookislife.flow.data;

import javax.inject.Inject;

/**
 * Created by SidneyXu on 2016/05/19.
 */
public class MongoDataStorage implements DataStorage{

    @Inject
    private MongoDao mongoDao;

    @Override
    public BaseEntity findById(String database, String tableName, String id) {
        return null;
    }

    @Override
    public String insert(String database, String tableName, BaseEntity entity) {
        return mongoDao.insert(database, tableName, entity);
    }
}
