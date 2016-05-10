package com.bookislife.flow;

import com.bookislife.flow.data.MongoContext;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Test;

/**
 * Created by SidneyXu on 2016/05/05.
 */
public class MongoContextTest {

    private Injector injector;
    private MongoContext mongoContext;

    @Test
    public void setUp() {
        injector = Guice.createInjector(new ServerModule(null, null));
        mongoContext = injector.getInstance(MongoContext.class);
        assert mongoContext != null;
    }

    @Test
    public void testGetClient() throws Exception {

    }
}