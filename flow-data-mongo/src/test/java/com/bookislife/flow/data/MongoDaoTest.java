package com.bookislife.flow.data;

import com.bookislife.flow.Env;
import com.bookislife.flow.exception.FlowException;
import com.google.common.collect.Maps;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.sun.javadoc.Doc;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertNotNull;

/**
 * Created by SidneyXu on 2016/05/10.
 */
public class MongoDaoTest {

    private MongoContext mongoContext;
    private MongoDao dao;
    private MongoClientOptions options;
    private String database = "test";
    private String tableName = "test";

    @Before
    public void setUp() {
        System.setProperty(Env.Config.DB_CLEANER_INTERVAL, "" + 1000 * 60 * 60);
        mongoContext = new MongoContext();
        options = MongoClientOptions.newBuilder()
                .url("localhost")
                .create();
        dao = new MongoDao(mongoContext);
//        assertNotNull(dao);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testInsert() throws Exception {
        Document doc = new Document("name", "MongoDB")
                .append("type", "database")
                .append("count", 1)
                .append("info", new Document("x", 203).append("y", 102));
        MongoDocument mongoDocument = new MongoDocument(doc);
        String id = dao.insert(database, tableName, mongoDocument);
        System.out.println(id);

        assertNotNull(id);
    }

    @Test
    public void testFind() throws FlowException {
        MongoQuery query = new MongoQuery(tableName);

        Condition condition = new Condition.Builder()
                .addCondition("$eq", "i", 7)
                .create();

        MongoCollection<Document> collection = mongoContext.getClient(options)
                .getDatabase(database)
                .getCollection(tableName);
        Map<String, Object> q = new HashMap<>();
        Map<String, Object> q1 = new HashMap<>();
        Map<String, Object> q2 = new HashMap<>();
        Map foo = new HashMap<>();
        Map foo1 = new HashMap<>();
        Map foo2 = new HashMap<>();

        foo1.put("$eq", 7);
        q1.put("i", foo1);

        foo2.put("$eq", 7);
        q2.put("i", foo2);

        foo.put("$or", Arrays.asList(1, 7));

        q.put("i", foo);

        Filters.or(Filters.eq("i", 1), Filters.eq("i", 7));


        Document document = new Document(q);
        FindIterable<Document> iterable = collection.find(document);
        Iterator<Document> iterator = iterable.iterator();
        System.out.println(iterator.hasNext());
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Test
    public void testUpdate() throws Exception {

    }

    @Test
    public void testInsertOrUpdate() throws Exception {

    }

    @Test
    public void testBatchInsert() throws Exception {
        List<MongoDocument> documents = new ArrayList<MongoDocument>();
        for (int i = 0; i < 10; i++) {
            Document document = new Document("i", i);
            documents.add(new MongoDocument(document));
        }

        dao.batchInsert(database, tableName, documents);
    }

    @Test
    public void testDelete() throws Exception {

    }

    @Test
    public void testFindById() throws Exception {
        MongoDocument document = (MongoDocument) dao.findById(database, tableName, "5744dcf5aed0769a8a344a68");
        System.out.println(document);
    }

    @Test
    public void testFindOne() throws Exception {
        MongoDocument document = (MongoDocument) dao.findOne(database, tableName, null);
        System.out.println(document);
    }

    @Test
    public void testFindAll() throws Exception {

    }

    @Test
    public void testCount() throws Exception {
        long count = dao.count(database, tableName, null);
        System.out.println(count);
    }
}