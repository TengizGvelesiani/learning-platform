package com.tengo.learningplatform.parser;

import com.tengo.learningplatform.materials.Course;

public interface Parser {

    Course parse(String resourcePath) throws Exception;
}
