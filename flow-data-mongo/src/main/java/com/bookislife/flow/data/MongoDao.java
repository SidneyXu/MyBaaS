package com.bookislife.flow.data;

import com.mongodb.client.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import javax.inject.Singleton;
import java.util.List;

/**
 * Created by SidneyXu on 2016/05/03.
 */
@Singleton
public class MongoDao implements BaseDao {

    private Logger logger = LoggerFactory.getLogger(MongoDao.class);

    private final MongoContext mongoContext;

    public MongoDao(MongoContext mongoContext) {
        this.mongoContext = mongoContext;
    }

    private MongoCollection getCollection(String database, String tableName) {
        return mongoContext.getClient(database)
                .getDatabase(database)
                .getCollection(tableName);
    }

    @Override
    public String insert(String database, String tableName, BaseEntity entity) {
        MongoDocument document = (MongoDocument) entity;
        getCollection(database, tableName)
            .insertOne(null);
        return null;
    }

    @Override
    public BaseEntity update() {
        return null;
    }

    @Override
    public BaseEntity insertOrUpdate() {
        return null;
    }

    @Override
    public void batchInsert(String database, String tableName, List<BaseEntity> entities) {
    }

    @Override
    public int delete() {
        return 0;
    }

    @Override
    public BaseEntity findById() {
        return null;
    }

    @Override
    public BaseEntity findOne() {
        return null;
    }

    @Override
    public List<BaseEntity> findAll() {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }
}
