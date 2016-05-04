package com.bookislife.flow;

import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by SidneyXu on 2016/05/04.
 */
public class IOUtils {

    public static final Logger logger = LoggerFactory.getLogger(IOUtils.class);

    public static String loadResource(Class<?> clazz, String path) {
        URL url = Resources.getResource(clazz, path);
        try {
            return Resources.toString(url, Charset.forName("UTF-8"));
        } catch (IOException e) {
            logger.warn("Unable to load {}", path);
            return null;
        }
    }
}
