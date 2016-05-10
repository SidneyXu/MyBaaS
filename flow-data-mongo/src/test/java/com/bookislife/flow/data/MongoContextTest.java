package com.bookislife.flow.data;

import com.bookislife.flow.Env;
import com.mongodb.MongoClient;
import junit.runner.BaseTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.assertNotNull;


/**
 * Created by SidneyXu on 2016/05/10.
 */
public class MongoContextTest {

    private MongoContext mongoContext;

    @Before
    public void setUp() {
        System.setProperty(Env.Config.DB_CLEANER_INTERVAL, "" + 1000 * 60 * 60);
        mongoContext = new MongoContext();
    }

    @Test
    public void test01() {
        MongoClientOptions options = MongoClientOptions.newBuilder()
                .url("localhost")
                .create();
        MongoClient client = mongoContext.getClient(options);
        assertNotNull(client);
    }
}