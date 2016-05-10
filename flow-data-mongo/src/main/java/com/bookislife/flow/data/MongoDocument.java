package com.bookislife.flow.data;

import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SidneyXu on 2016/05/04.
 */
public class MongoDocument extends BaseEntity {

    private Map<String, Object> map = new HashMap<>();
    public final Document document;

    public MongoDocument(Document document) {
        this.document = document;
    }

    public MongoDocument(Map<String, Object> map) {
        this.document = new Document(map);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MongoDocument{");
        sb.append("document=").append(document);
        sb.append('}');
        return sb.toString();
    }
}
