package com.bookislife.flow.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by SidneyXu on 2016/05/12.
 */
public class Constraint {

    private int limit;
    private int skip;
    private List<String> includes;
    private String sort;

    Constraint(int limit, int skip, List<String> includes, String sort) {
        this.limit = limit;
        this.skip = skip;
        this.includes = includes;
        this.sort = sort;
    }

    public int getLimit() {
        return limit;
    }

    public int getSkip() {
        return skip;
    }

    public List<String> getIncludes() {
        return Collections.unmodifiableList(includes);
    }

    public String getSort() {
        return sort;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private int limit;
        private int skip;
        private List<String> includes;
        private String sort;

        public Builder limit(int limit) {
            this.limit = limit;
            return this;
        }

        public Builder skip(int skip) {
            this.skip = skip;
            return this;
        }

        public Builder include(String include) {
            if (includes == null) {
                includes = new ArrayList<>();
            }
            includes.add(include);
            return this;
        }

        public Builder includes(List<String> includes) {
            this.includes = includes;
            return this;
        }

        public Builder sort(String sort) {
            this.sort = sort;
            return this;
        }

        public Constraint createConstraint() {
            return new Constraint(limit, skip, includes, sort);
        }
    }
}
