package com.bookislife.flow.data;

import javax.inject.Inject;

/**
 * Created by SidneyXu on 2016/05/30.
 */
public class MongoSchemaService implements  DBSchemaService{

    @Inject
    private MongoDao mongoDao;

    @Override
    public void insert(BaseSchema schema) {

    }

    @Override
    public BaseSchema get(String id) {
        // TODO: 16/5/30  
        MongoDocument document= (MongoDocument) mongoDao.findById(SCHEMA_DATABASE_NAME,SCHEMA_TABLE_NAME,id); 
        return null;
    }
}
