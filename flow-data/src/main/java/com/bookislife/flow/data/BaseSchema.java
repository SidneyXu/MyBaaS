package com.bookislife.flow.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SidneyXu on 2016/05/27.
 */
public class BaseSchema {
    private String databaseName;
    private String tableName;
    private Map<String, ColumnType> columnInfos;

    public BaseSchema(String databaseName, String tableName) {
        this.databaseName = databaseName;
        this.tableName = tableName;
        columnInfos = new HashMap<>();
    }

    public BaseSchema addColumn(String name, ColumnType type) {
        columnInfos.put(name, type);
        return this;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getTableName() {
        return tableName;
    }

    public Map<String, ColumnType> getColumnInfos() {
        return columnInfos;
    }

    public void setColumnInfos(Map<String, ColumnType> columnInfos) {
        this.columnInfos = columnInfos;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BaseSchema{");
        sb.append("databaseName='").append(databaseName).append('\'');
        sb.append(", tableName='").append(tableName).append('\'');
        sb.append(", columnInfos=").append(columnInfos);
        sb.append('}');
        return sb.toString();
    }
}
