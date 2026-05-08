package com.tengo.learningplatform.parser;

import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

public final class XmlSchemaValidator {

    private XmlSchemaValidator() {
    }

    public static void validate(String xmlResourcePath, String xsdResourcePath) throws Exception {
        try (InputStream xmlStream = resourceStream(xmlResourcePath);
             InputStream xsdStream = resourceStream(xsdResourcePath)) {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(xsdStream));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlStream));
        }
    }

    private static InputStream resourceStream(String resourcePath) {
        InputStream stream = XmlSchemaValidator.class.getResourceAsStream(resourcePath);
        if (stream == null) {
            throw new IllegalArgumentException("Resource not found: " + resourcePath);
        }
        return stream;
    }
}
