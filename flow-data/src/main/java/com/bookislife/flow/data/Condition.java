package com.bookislife.flow.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SidneyXu on 2016/05/12.
 */
public class Condition {

    public static final String EQ = "$eq";
    public static final String IN = "$in";
    public static final String NOT_IN = "$nin";
    public static final String EXISTS = "$exists";
    public static final String OR = "$or";
    public static final String LIKE = "$like";
    public static final String LINK = "$link";

    public static abstract class Op {

    }

    public static class OpEq extends Op {

    }

    public static class Builder {

        // column {op, value}
        private Map<String, Map<String, Object>> where = new HashMap<>();

        public Builder eq(String column, Object value) {
            addCondition(EQ, column, value);
            return this;
        }

        public Builder in(String column, Collection<?> value) {
            addCondition(IN, column, value);
            return this;
        }

        public Builder exists(String column, boolean value) {
            addCondition(EXISTS, column, value);
            return this;
        }

        public Builder addCondition(String op, String column, Object value) {
            Map<String, Object> cond;
            if (where.containsKey(column)) {
                cond = where.get(column);
            } else {
                cond = new HashMap<>();
            }
            cond.put(op, value);
            where.put(column, cond);
            return this;
        }

        public Condition create() {
            return null;
        }
    }
}
