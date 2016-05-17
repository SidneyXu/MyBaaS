package com.bookislife.flow.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runners.model.TestClass;

import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by SidneyXu on 2016/05/16.
 */
public class ResourceLoaderTest {

    private ResourceLoader loader;
    private String packageName = "com.bookislife.flow.resource";
    private String jarPackageName="org.slf4j";

    @Before
    public void setup() {
        ClassLoader classLoader = ResourceLoader.class.getClassLoader();
        loader = new ResourceLoader(classLoader);
    }

    @Test
    public void testScanURL() throws Exception {
        Set<Class<?>> classSet = loader.scanPackage(packageName);
        classSet.forEach(System.out::println);
    }

    @Test
    public void testScanJar() throws Exception{
        Set<Class<?>> classSet = loader.scanPackage(jarPackageName);
        classSet.forEach(System.out::println);
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

    @Test
    public void test01() throws IOException {
        String packageName = "org.slf4j";
        String ppp = packageName.replace('.', '/');
        ClassLoader classLoader = ResourceLoader.class.getClassLoader();

        Enumeration<URL> urlEnumeration = classLoader.getResources(ppp);
        Set<Class<?>> classSet = new HashSet<>();
        while (urlEnumeration.hasMoreElements()) {
            URL url = urlEnumeration.nextElement();
//            System.out.println(url);

            String externalFrom = url.toExternalForm();
            String jarPath = externalFrom.substring(externalFrom.indexOf("file:") + 5);
            jarPath = jarPath.substring(0, jarPath.indexOf('!'));

//            System.out.println(jarPath);
//            System.out.println(URLDecoder.decode(jarPath,"utf-8"));

            Set<String> set=loader.
                    scanURL(jarPackageName, url);
            System.out.println(set);
        }

    }
}