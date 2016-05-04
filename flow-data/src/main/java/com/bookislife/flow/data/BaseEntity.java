package com.bookislife.flow.data;

/**
 * Created by SidneyXu on 2016/04/29.
 */
public class BaseEntity {

    private long createdAt;
    private long updatedAt;
    private String id;

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
