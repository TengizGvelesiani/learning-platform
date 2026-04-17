package com.tengo.learningplatform.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlatformRegistry {

    private static final Logger LOGGER = LogManager.getLogger(PlatformRegistry.class);
    private static String platformName;
    private static int totalEnrollments = 0;

    static {
        platformName = "Global Learning Platform";
        LOGGER.info("{} initialized.", platformName);
    }

    public static void incrementEnrollment() {
        totalEnrollments++;
    }

    public static int getTotalEnrollments() {
        return totalEnrollments;
    }
}
