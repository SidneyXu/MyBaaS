package com.bookislife.flow.data;

import com.bookislife.flow.data.utils.JacksonDecoder;
import org.junit.Test;

import java.util.Date;

/**
 * Created by SidneyXu on 2016/05/23.
 */
public class JacksonDecoderTest {

    @Test
    public void test01() {
        String data = "{\n" +
                "    \"data\": {\n" +
                "        \"x\": 1,\n" +
                "        \"name\": \"Jane\"\n" +
                "    },\n" +
                "    \"createdAt\": 1463994317832,\n" +
                "    \"updatedAt\": 1463994317860,\n" +
                "    \"id\": \"1232\"\n" +
                "}";

        MongoDocument document = JacksonDecoder.decode(data, MongoDocument.class);
        System.out.println(document);

        System.out.println(document.getData());
        System.out.println(document.getCreatedAt());
        System.out.println(document.getUpdatedAt());
        System.out.println(document.getId());
    }
}
