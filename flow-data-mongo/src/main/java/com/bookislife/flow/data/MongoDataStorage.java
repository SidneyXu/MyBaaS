package com.bookislife.flow.data;

import com.bookislife.flow.data.utils.JacksonDecoder;

import javax.inject.Inject;

/**
 * Created by SidneyXu on 2016/05/19.
 */
public class MongoDataStorage implements DataStorage {

    @Inject
    private MongoDao mongoDao;

    @Override
    public BaseEntity findById(String database, String tableName, String id) {
        return mongoDao.findById(database, tableName, id);
    }

    @Override
    public BaseEntity insert(String database, String tableName, String data) {
        MongoDocument document = JacksonDecoder.decode(data, MongoDocument.class);
        long current = System.currentTimeMillis();
        document.setCreatedAt(current);
        document.setUpdatedAt(current);
        String id = mongoDao.insert(database, tableName, document);
        document.setId(id);
        return document;
    }
}
