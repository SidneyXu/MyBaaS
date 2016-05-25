package com.bookislife.flow.data.module;

import com.bookislife.flow.data.DriverManager;
import com.google.inject.Binder;
import com.google.inject.Module;

/**
 * Created by SidneyXu on 2016/05/23.
 */
public class DataServiceModule implements Module {

    @Override
    public void configure(Binder binder) {
        // TODO: 16/5/6 should by dialect
//        bind(MongoContext.class);
//        bind(MongoDao.class);

        DriverManager.setBinder(binder);

        try {
            Class.forName("com.bookislife.flow.data.MongoDriver");

            System.out.println(77777);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
