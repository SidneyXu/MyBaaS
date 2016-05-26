package com.bookislife.flow.data;

import com.bookislife.flow.data.utils.JacksonDecoder;
import com.bookislife.flow.exception.FlowException;

import javax.inject.Inject;
import java.util.List;

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
    public List<BaseEntity> findAll(String database, String tableName, String query) throws FlowException{
        MongoQuery mongoQuery=JacksonDecoder.decode(query, MongoQuery.class);




//        MongoQuery mongoQuery
        return mongoDao.findAll(database, tableName, mongoQuery);
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

    @Override
    public int delete(String database, String tableName, String id) {
        return mongoDao.deleteById(database, tableName, id);
    }

    @Override
    public long count(String database, String tableName) {
        return mongoDao.count(database, tableName, null);
    }
}
