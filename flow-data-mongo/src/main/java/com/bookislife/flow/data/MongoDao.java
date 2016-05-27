package com.bookislife.flow.data;

import com.bookislife.flow.Validator;
import com.bookislife.flow.exception.FlowException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.CountOptions;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.*;

/**
 * Created by SidneyXu on 2016/05/03.
 */
@Singleton
public class MongoDao implements BaseDao {

    public static final int TIMEOUT = 1000;
    public static final int BATCH_TIMEOUT = 5000;
    public static final int COUNT_TIMEOUT = 10000;
    public static final int DEFAULT_LIMIT = 1000;
    public static final int MAX_LIMIT = 2000;

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

    // TODO: 5/27/16
    //id createdAt updatedAt

    @Override
    public String insert(String database, String tableName, BaseEntity entity) {
        MongoDocument mongoDocument = (MongoDocument) entity;
        Document document = toDocument(mongoDocument);
        getCollection(database, tableName)
                .insertOne(document);
        Object id = document.get("_id");
        if (id instanceof ObjectId) {
            return ((ObjectId) id).toHexString();
        }
        return id.toString();
    }

    @Override
    public int update(String database, String tableName, BaseQuery query, BaseModifier modifier) throws FlowException {
        return (int) getCollection(database, tableName)
                .updateMany(
                        toDocument(query),
                        toDocument(modifier))
                .getModifiedCount();
    }

    private Document toDocument(MongoDocument mongoDocument) {
        return mongoDocument.document;
    }

    private List<Document> toDocuments(List<? extends BaseEntity> mongoDocuments) {
        return mongoDocuments.stream()
                .map(baseEntity -> toDocument((MongoDocument) baseEntity))
                .collect(Collectors.toList());
    }

    // TODO: 5/27/16
    private Document toDocument(BaseQuery query) {
        if (null == query) return new Document();
        if (query instanceof MongoQuery) {
            Document queryDocument= new Document(((MongoQuery) query).getQuery());


            return queryDocument;
        }
        throw new IllegalArgumentException("MongoQuery is expected, actual is " + query.getClass().getName());
    }

    private Document toDocument(BaseModifier modifier) {
        if (null == modifier) return new Document();
        return new Document(modifier.getModifiers());
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
                .deleteOne(eq("_id", ObjectId.isValid(id) ? new ObjectId(id) : id))
                .getDeletedCount();
    }

    @Override
    public int deleteAll(String database, String tableName, BaseQuery query) throws FlowException {
        return (int) getCollection(database, tableName)
                .deleteMany(toDocument(query))
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

        int limit = DEFAULT_LIMIT;
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
        if (sortMap != null) {
            iterable.sort(new Document(sortMap));
        }
        if (query.getProjection() != null) {
            Map<String, Object> projectMap = new HashMap<>();
            query.getProjection()
                    .getSelects()
                    .forEach(s -> projectMap.put(s, 1));
            iterable.projection(new Document(projectMap));
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
        MongoCollection<Document> collection = getCollection(database, tableName);
        CountOptions options = new CountOptions();
        options.maxTime(COUNT_TIMEOUT, TimeUnit.MILLISECONDS);
        return collection.count(toDocument(query), options);
    }

}
