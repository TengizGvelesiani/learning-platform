package com.tengo.learningplatform.parser;

import java.io.InputStream;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

import com.tengo.learningplatform.materials.Course;

public class JaxbParser implements Parser {

    private static final String XSD_PATH = "/course-hierarchy.xsd";

    @Override
    public Course parse(String resourcePath) throws Exception {
        XmlSchemaValidator.validate(resourcePath, XSD_PATH);
        JAXBContext context = JAXBContext.newInstance(Course.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        try (InputStream xmlStream = JaxbParser.class.getResourceAsStream(resourcePath)) {
            if (xmlStream == null) {
                throw new IllegalArgumentException("Resource not found: " + resourcePath);
            }
            return (Course) unmarshaller.unmarshal(xmlStream);
        }
    }
}
