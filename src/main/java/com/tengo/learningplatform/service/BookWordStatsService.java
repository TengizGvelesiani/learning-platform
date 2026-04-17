package com.tengo.learningplatform.service;

import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class BookWordStatsService {

    private static final Logger LOGGER = LogManager.getLogger(BookWordStatsService.class);

    public void writeUniqueWordCount(String resourceFileName, String outputFilePath) {
        try {
            URL resource = Thread.currentThread().getContextClassLoader().getResource(resourceFileName);
            if (resource == null) {
                throw new IllegalArgumentException("Resource not found: " + resourceFileName);
            }
            String text = FileUtils.readFileToString(FileUtils.toFile(resource), StandardCharsets.UTF_8);
            String normalized = StringUtils.lowerCase(text).replaceAll("[^\\p{L}]+", " ");
            long uniqueWords = Arrays.stream(StringUtils.split(normalized, ' '))
                    .filter(StringUtils::isNotBlank)
                    .distinct()
                    .count();
            File output = new File(outputFilePath);
            FileUtils.forceMkdirParent(output);
            String result = "File: " + resourceFileName + System.lineSeparator() + "Unique words: " + uniqueWords + System.lineSeparator();
            FileUtils.writeStringToFile(output, result, StandardCharsets.UTF_8, false);
            LOGGER.info("Unique words count for {} is {} and saved to {}", resourceFileName, uniqueWords, output.getPath());
        } catch (Exception e) {
            throw new IllegalStateException("Failed to calculate unique words from resource " + resourceFileName, e);
        }
    }
}
