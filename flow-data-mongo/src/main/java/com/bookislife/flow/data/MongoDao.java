package com.bookislife.flow.data;

import com.bookislife.flow.Validator;
import com.bookislife.flow.exception.FlowException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.sun.tools.internal.jxc.ap.Const;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by SidneyXu on 2016/05/03.
 */
@Singleton
public class MongoDao implements BaseDao {

    public static final int TIMEOUT = 1000;
    public static final int BATCH_TIMEOUT = 5000;
    public static final int LIMIT = 1000;

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
    public String update() {
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
        return (int) getCollection(database, tableName)
                .deleteOne(Filters.eq("_id", new ObjectId(id)))
                .getDeletedCount();
    }

    @Override
    public BaseEntity findById(String database, String tableName, String id) {
        Document document = getCollection(database, tableName)
                .find(Filters.eq("_id", new ObjectId(id)))
                .maxTime(TIMEOUT, TimeUnit.MILLISECONDS)
                .first();
        return new MongoDocument(document);
    }

    @Override
    public BaseEntity findOne(String database, String tableName, BaseQuery query) throws FlowException {
        MongoCollection<Document> collection = getCollection(database, tableName);
        FindIterable<Document> iterable = collection.find(toDocument(query))
                .maxTime(TIMEOUT, TimeUnit.MILLISECONDS);
        Iterator<Document> iterator = iterable.iterator();

        Validator.assertHasNext(iterator, "Object not found.");

        Document document = iterator.next();
        return new MongoDocument(document);
    }

    @Override
    public List<BaseEntity> findAll(String database, String tableName, BaseQuery query) throws FlowException {
        MongoCollection<Document> collection = getCollection(database, tableName);

        int limit = LIMIT;
        int skip = 0;
        Map<String, Object> sortMap = null;
        if (query.getConstraint() != null) {
            Constraint constraint = query.getConstraint();
            if (constraint.getLimit() > 0) {
                limit = constraint.getLimit();
            }
            if (constraint.getSkip() > 0) {
                skip = constraint.getSkip();
            }
            if (constraint.getSort() != null) {
                sortMap = new LinkedHashMap<>();
                String[] sorts = constraint.getSort().split(",", -1);
                for (String sort : sorts) {
                    sortMap.put(sort.replaceFirst("[+-]", ""), sort.startsWith("+") ? 1 : -1);
                }
            }
        }

        FindIterable<Document> iterable = collection.find(toDocument(query))
                .limit(limit)
                .skip(skip)
                .maxTime(BATCH_TIMEOUT, TimeUnit.MILLISECONDS);
        if(sortMap!=null){
            iterable.sort(new Document(sortMap));
        }
        Iterator<Document> iterator = iterable.iterator();

        Validator.assertHasNext(iterator, "Object not found.");

        List<BaseEntity> entities = new ArrayList<>();

        iterator.forEachRemaining(document ->
                entities.add(new MongoDocument(document)));
        return entities;
    }

    @Override
    public long count(String database, String tableName, BaseQuery query) {
        if (query == null) {
            return getCollection(database, tableName).count();
        } else {
            // TODO: 5/26/16
            return 0;
        }
    }

}
