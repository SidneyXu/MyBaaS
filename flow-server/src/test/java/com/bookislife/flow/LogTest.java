package com.bookislife.flow;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by SidneyXu on 2016/05/01.
 */
public class LogTest {

    @Test
    public void testCore() {
        Logger logger = LoggerFactory.getLogger(Foobar.class);
        logger.debug("debugging...");
        logger.info("info...");
        logger.error("error...");
        logger.warn("warning...");
        logger.info("Temperature set to {}. Old temperature was {}.", "the first arg", "the second arg");
    }

    class Foobar {

    }
}