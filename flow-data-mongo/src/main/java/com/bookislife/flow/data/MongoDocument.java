package com.bookislife.flow.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.bson.Document;

import java.util.Map;

/**
 * Created by SidneyXu on 2016/05/04.
 */
public class MongoDocument extends BaseEntity {

    private Map<String, Object> data;
    public Document document;

    public MongoDocument(Document document) {
        this.document = document;
    }

    @JsonCreator
    public MongoDocument(Map<String, Object> map) {
        this.document = new Document(map);
        parseData(map);
    }

//    @JsonCreator
//    public MongoDocument() {
//        System.out.println(data);
//    }

//    @JsonSetter
//    public void put(String key, Object value) {
//        System.out.println(key);
//    }

    @SuppressWarnings("unchecked")
    private void parseData(Map<String, Object> map) {
        data = (Map<String, Object>) map.getOrDefault(FIELD_DATA, null);
        setCreatedAt((Long) map.getOrDefault(FIELD_CREATED_AT, 0));
        setUpdatedAt((Long) map.getOrDefault(FIELD_UPDATED_AT, getCreatedAt()));
        setId((String) map.getOrDefault(FIELD_ID, null));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MongoDocument{");
        sb.append("document=").append(document);
        sb.append('}');
        return sb.toString();
    }

    public Map<String, Object> getData() {
        return data;
    }

}
