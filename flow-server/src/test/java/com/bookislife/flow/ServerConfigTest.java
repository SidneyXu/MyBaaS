package com.bookislife.flow;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by SidneyXu on 2016/05/25.
 */
public class ServerConfigTest {

    @Test
    public void test01() throws IOException {
        InputStream inputStream=ServerConfig.class.getClassLoader().getResourceAsStream("flow.properties");
        System.out.println(inputStream.available());
        ServerConfig serverConfig=new ServerConfig(inputStream);
        System.out.println(serverConfig);
    }
}