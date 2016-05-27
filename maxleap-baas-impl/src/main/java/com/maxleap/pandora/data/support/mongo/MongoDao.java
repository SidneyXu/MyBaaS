package com.maxleap.pandora.data.support.mongo;

import com.maxleap.domain.base.LASObject;
import com.maxleap.domain.base.ObjectId;
import com.maxleap.domain.mongo.BaseEntity;
import com.maxleap.pandora.config.DataSourceStatus;
import com.maxleap.pandora.config.DatabaseVisitor;
import com.maxleap.pandora.config.mgo.MgoDatabase;
import com.maxleap.pandora.core.mongo.MongoQuery;
import com.maxleap.pandora.core.mongo.MongoQueryOptions;
import com.maxleap.pandora.core.mongo.MongoUpdate;
import com.maxleap.pandora.core.mongo.exception.MongoDataAccessException;
import com.maxleap.pandora.core.utils.Assertions;
import com.maxleap.pandora.core.utils.DateUtils;
import com.maxleap.pandora.data.support.LasSunObjectIdMapper;
import com.maxleap.pandora.data.support.MongoEntityManager;
import com.maxleap.pandora.data.support.MongoJsons;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CountOptions;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author sneaky
 * @since 3.0.0
 */
@Singleton
public class MongoDao {
  private static final Logger LOGGER = LoggerFactory.getLogger(MongoDao.class);
  /**
   * If query has not limit parameter.
   */
  private static final int DEFAULT_LIST_SIZE = 100;
  private static final int MAX_LIST_SIZE = 2000;

  private DatabaseVisitor<MgoDatabase> mgoDatabaseVisitor;
  private MongoClientFactory mongoClientFactory;

  @Inject
  public MongoDao(DatabaseVisitor mgoDatabaseVisitor,
                  MongoClientFactory mongoClientFactory) {
    this.mgoDatabaseVisitor = mgoDatabaseVisitor;
    this.mongoClientFactory = mongoClientFactory;
  }

  public <T extends BaseEntity<ID>, ID> void create(String db, String table, List<T> entities) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("[create, size: " + entities.size() + "][db: " + db + "][table: " + table + "]");
    }
    Assertions.notBlank("db", db);
    Assertions.notBlank("table", table);
    Assertions.notNull("entities", entities);
    List<Document> documents = toDocument(entities);
    getMongoCollection(db, table).insertMany(documents);
  }

  public <ID, Result> Result update(String db, String table, ID id, MongoUpdate update) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("[updateById: " + id + "][db: " + db + "][table: " + table + "]");
    }
    MongoQuery mongoQuery = new MongoQuery().equalTo("_id", id);
    return update(db, table, mongoQuery, update);
  }

  public <Entity extends BaseEntity<ID>, ID> Iterator<Entity> findIterator(String db, String table, MongoQuery mongoQuery, Class<Entity> clazz) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("[findIterator: " + mongoQuery + "][db: " + db + "][table: " + table + "]");
    }
    MongoEntityManager.LasDataIterator lasDataIterator = new MongoEntityManager.LasDataIterator(build(db, table, mongoQuery, true).iterator(), clazz);
    return lasDataIterator;
  }

  public MongoDatabase getMongoDatabase(String db) {
    return mongoClientFactory.get(getMgoDatabase(db)).getDatabase(db);
  }

  public MongoCollection getMongoCollection(String db, String table) {
    return getMongoDatabase(db).getCollection(table);
  }

  MgoDatabase getMgoDatabase(String db) {
    MgoDatabase mgoDatabase = mgoDatabaseVisitor.get(db);
    if (mgoDatabase.getStatus() != DataSourceStatus.ENABLE && mgoDatabase.getMgoCluster().getStatus() != DataSourceStatus.ENABLE) {
      throw new IllegalArgumentException("DataSource is not available, status: " + mgoDatabase.getStatus() + ", db: " + db);
    }
    return mgoDatabase;
  }

  public Object adjust(Document document, Class entityClass) {
    if (entityClass == LASObject.class) {
      LasSunObjectIdMapper.toLasSunObjectId(document);
      return new LASObject(document);
    } else if (Map.class.isAssignableFrom(entityClass)) {
      LasSunObjectIdMapper.toLasSunObjectId(document);
      try {
        return entityClass.getConstructor(Map.class).newInstance(document);
      } catch (Exception e) {
        throw new IllegalArgumentException(e);
      }
    } else {
      LasSunObjectIdMapper.toObjectId(document);
      Object createdAt = document.get("createdAt");
      if (createdAt instanceof Long) {
        document.put("createdAt", DateUtils.encodeDate(new Date((Long) createdAt)));
      }
      Object updatedAt = document.get("updatedAt");
      if (updatedAt instanceof Long) {
        document.put("updatedAt", DateUtils.encodeDate(new Date((Long) updatedAt)));
      }
      return MongoJsons.deserialize(MongoJsons.serialize(document), entityClass);
    }
  }

  Document toDocument(Map doc) {
    return new Document(doc);
  }
}