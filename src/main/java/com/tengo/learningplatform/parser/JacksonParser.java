package com.tengo.learningplatform.parser;

import java.io.InputStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.tengo.learningplatform.materials.Course;

public class JacksonParser implements Parser {

    @Override
    public Course parse(String resourcePath) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try (InputStream jsonStream = JacksonParser.class.getResourceAsStream(resourcePath)) {
            if (jsonStream == null) {
                throw new IllegalArgumentException("Resource not found: " + resourcePath);
            }
            return mapper.readValue(jsonStream, Course.class);
        }
    }
}
