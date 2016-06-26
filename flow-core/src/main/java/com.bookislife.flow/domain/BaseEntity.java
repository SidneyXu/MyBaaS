package com.bookislife.flow.domain;

/**
 * Created by SidneyXu on 2016/04/29.
 */
public class BaseEntity {

    public static final String FIELD_CREATED_AT = "createdAt";
    public static final String FIELD_UPDATED_AT = "updatedAt";
    public static final String FIELD_ID = "id";
    public static final String FIELD_DATA = "data";
    public static final String FIELD_META_DATA = "metaData";
    public static final String FIELD_ENABLE_FLAG = "enable_flag";
    public static final String FIELD_DELETE_FLAG = "delete_flag";

    private long createdAt;
    private long updatedAt;
    private String id;
    private boolean enableFlg;
    private boolean deleteFlg;

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

    public boolean isEnableFlg() {
        return enableFlg;
    }

    public void setEnableFlg(boolean enableFlg) {
        this.enableFlg = enableFlg;
    }

    public boolean isDeleteFlg() {
        return deleteFlg;
    }

    public void setDeleteFlg(boolean deleteFlg) {
        this.deleteFlg = deleteFlg;
    }
}