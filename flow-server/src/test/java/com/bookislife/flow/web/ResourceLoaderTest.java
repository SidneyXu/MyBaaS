package com.bookislife.flow.web;

import org.junit.Test;
import org.junit.runners.model.TestClass;

import java.io.File;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by SidneyXu on 2016/05/16.
 */
public class ResourceLoaderTest {

    @Test
    public void testScanURL() throws Exception {

    }

    @Test
    public void testPackage() throws Exception {
        String packageName = "com.bookislife.flow.resource";
        String path = packageName.replace('.', '/');
//        System.out.println(path);

        ClassLoader classLoader = ResourceLoader.class.getClassLoader();
        ResourceLoader resourceLoader = new ResourceLoader(classLoader);

        classLoader = TestClass.class.getClassLoader();

        Enumeration<URL> urlEnumeration = classLoader.getResources(path);
        Set<Class<?>> classSet = new HashSet<>();
        while (urlEnumeration.hasMoreElements()) {
            URL url = urlEnumeration.nextElement();
            Set<String> strings = resourceLoader.scanURL(packageName, url);
            System.out.println(strings);
        }

        System.out.println(System.getProperties());


    }
}