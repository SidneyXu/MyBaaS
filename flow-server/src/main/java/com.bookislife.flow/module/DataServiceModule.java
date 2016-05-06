package com.bookislife.flow.module;

import com.bookislife.flow.data.MongoContext;
import com.bookislife.flow.data.MongoDao;
import com.google.inject.AbstractModule;

/**
 * Created by SidneyXu on 2016/05/05.
 */
public class DataServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        // TODO: 16/5/6 should by dialect
        bind(MongoContext.class);
        bind(MongoDao.class);

    }
}
