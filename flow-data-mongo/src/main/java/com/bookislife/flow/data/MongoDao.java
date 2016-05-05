package com.bookislife.flow.data;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.CountOptions;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import javax.inject.Singleton;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
        MongoDocument mongoDocument = (MongoDocument) entity;
        Document document=toDocument(mongoDocument);
        getCollection(database, tableName)
            .insertOne(document);
        return document.getObjectId("_id").toHexString();
    }

    private Document toDocument(MongoDocument mongoDocument) {
        return null;
    }

    private List<Document> toDocuments(List<BaseEntity> mongoDocuments){
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
        List<Document> documents=toDocuments(entities);
        getCollection(database, tableName)
                .insertMany(documents);
    }

    @Override
    public int deleteById(String database, String tableName, String id) {
        return 0;
    }

    @Override
    public BaseEntity findById(String database, String tableName, String id) {
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
    public long count(String database, String tableName, BaseQuery query) {
        return 0;
    }

}
