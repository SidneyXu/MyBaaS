package com.bookislife.flow.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by SidneyXu on 2016/05/16.
 */
public class ResourceLoader {

    public static final Logger logger = LoggerFactory.getLogger(ResourceLoader.class);


    private ClassLoader classLoader;

    public ResourceLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public void scanPackage(String packageName) {
        String path = packageName.replace('.', '\\');
        try {
            Enumeration<URL> urlEnumeration = classLoader.getResources(path);
            List<Class<?>> list = new ArrayList<>();
            while (urlEnumeration.hasMoreElements()) {
                URL url = urlEnumeration.nextElement();


            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void scanURL(URL url) {
        if ("jar".equals(url.getProtocol())) {

        } else {

        }
    }
}
