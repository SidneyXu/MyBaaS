package com.bookislife.flow.data;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by SidneyXu on 2016/05/13.
 */
public class BaseQueryTest {

    @Test
    public void test01(){
        Condition condition=new Condition.Builder()
                .addCondition("$eq", "x", 1)
                .addCondition("$in", "color", Arrays.asList("yellow","blue"))
                .addCondition("$exists", "name", true)
                .create();
        /*
            {
                x: {
                    "$eq":1
                },
                color: {
                    "$in":[yellow, blue]
                },
                name: {
                    exists: true
                }
            }
         */
    }

    @Test
    public void test02(){
        BaseQuery blogQuery=BaseQuery.from("t_blog");
        BaseQuery commentQuery=BaseQuery.from("t_comment");

        Condition condition=new Condition.Builder()
                .addCondition("$link", "blog_id", "t_blog.id")
                .create();

    }
}