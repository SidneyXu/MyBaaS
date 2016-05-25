package com.bookislife.flow.data;

import com.bookislife.flow.Validator;
import com.bookislife.flow.exception.FlowException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by SidneyXu on 2016/05/03.
 */
@Singleton
public class MongoDao implements BaseDao {

    private Logger logger = LoggerFactory.getLogger(MongoDao.class);

    private final MongoContext mongoContext;
    private final MongoClientOptions options;

    @Inject
    public MongoDao(MongoContext mongoContext) {
        this.mongoContext = mongoContext;
        this.options = MongoClientOptions.newBuilder()
                .url("127.0.0.1")
                .create();
    }

    private MongoCollection<Document> getCollection(String database, String tableName) {
        return mongoContext.getClient(options)
                .getDatabase(database)
                .getCollection(tableName);
    }

    @Override
    public String insert(String database, String tableName, BaseEntity entity) {
        MongoDocument mongoDocument = (MongoDocument) entity;
        Document document = toDocument(mongoDocument);
        getCollection(database, tableName)
                .insertOne(document);
        return document.getObjectId("_id").toHexString();
    }

    private Document toDocument(MongoDocument mongoDocument) {
        return mongoDocument.document;
    }

    private List<Document> toDocuments(List<? extends BaseEntity> mongoDocuments) {
        return mongoDocuments.stream()
                .map(baseEntity -> toDocument((MongoDocument) baseEntity))
                .collect(Collectors.toList());
    }

    private Document toDocument(BaseQuery query) {
        if (null == query) return new Document();
        if (query instanceof MongoQuery) {
            return new Document(((MongoQuery) query).getQuery());
        }
        throw new IllegalArgumentException("MongoQuery is expected, actual is " + query.getClass().getName());
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
    public void batchInsert(String database, String tableName, List<? extends BaseEntity> entities) {
        List<Document> documents = toDocuments(entities);
        getCollection(database, tableName)
                .insertMany(documents);
    }

    @Override
    public int deleteById(String database, String tableName, String id) {
        return 0;
    }

    @Override
    public BaseEntity findById(String database, String tableName, String id) {
        Document document = getCollection(database, tableName)
                .find(Filters.eq("_id", new ObjectId(id)))
                .first();
        return new MongoDocument(document);
    }

    @Override
    public BaseEntity findOne(String database, String tableName, BaseQuery query) throws FlowException {
        MongoCollection<Document> collection = getCollection(database, tableName);
        FindIterable<Document> iterable = collection.find(toDocument(query));
        Iterator<Document> iterator = iterable.iterator();

        Validator.assertHasNext(iterator, "Object not found.");

        Document document = iterator.next();
        return new MongoDocument(document);
    }

    @Override
    public List<BaseEntity> findAll(String database, String tableName, BaseQuery query) {
        return null;
    }

    @Override
    public long count(String database, String tableName, BaseQuery query) {
        return getCollection(database, tableName).count();
    }

}
