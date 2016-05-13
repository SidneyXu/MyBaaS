package com.bookislife.flow.data;

/**
 * Created by SidneyXu on 2016/05/05.
 */
public class BaseQuery {

    private final String tableName;
    private Condition condition;
    private Constraint constraint;

    public BaseQuery(String tableName) {
        this.tableName = tableName;
    }

    public static BaseQuery from(String tableName) {
        return new BaseQuery(tableName);
    }

    public Condition.Builder newCondition() {
        return Condition.newBuilder();
    }

    public Constraint.Builder newConstraint() {
        return Constraint.newBuilder();
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public void setConstraint(Constraint constraint) {
        this.constraint = constraint;
    }

    public Condition getCondition() {
        return condition;
    }

    public Constraint getConstraint() {
        return constraint;
    }

    public String getTableName() {
        return tableName;
    }
}
