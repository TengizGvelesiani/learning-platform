package com.tengo.learningplatform.parser;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import com.tengo.learningplatform.domain.CourseDifficulty;
import com.tengo.learningplatform.materials.Course;
import com.tengo.learningplatform.materials.Lesson;
import com.tengo.learningplatform.materials.Module;
import com.tengo.learningplatform.materials.Question;
import com.tengo.learningplatform.materials.Quiz;

/**
 * SAX parser for course hierarchy XML.
 *
 * Useful XPath examples for this XML:
 * 1) /course
 * 2) /course/modules/module
 * 3) /course/modules/module[1]/moduleName
 * 4) //lesson[practical='true']/content
 * 5) //quiz/questions/question/text
 * 6) /course/createdAt
 */
public class SaxParser implements Parser {

    private static final String XSD_PATH = "/course-hierarchy.xsd";

    @Override
    public Course parse(String resourcePath) throws Exception {
        XmlSchemaValidator.validate(resourcePath, XSD_PATH);
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(false);
        CourseHandler handler = new CourseHandler();
        try (InputStream xmlStream = SaxParser.class.getResourceAsStream(resourcePath)) {
            if (xmlStream == null) {
                throw new IllegalArgumentException("Resource not found: " + resourcePath);
            }
            factory.newSAXParser().parse(xmlStream, handler);
            return handler.getCourse();
        }
    }

    private static final class CourseHandler extends DefaultHandler {

        private Course course;
        private Module currentModule;
        private Lesson currentLesson;
        private Quiz currentQuiz;
        private Question currentQuestion;
        private final StringBuilder text = new StringBuilder();

        Course getCourse() {
            return course;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            String element = elementName(localName, qName);
            text.setLength(0);
            switch (element) {
                case "course":
                    course = new Course();
                    break;
                case "module":
                    currentModule = new Module();
                    break;
                case "lesson":
                    currentLesson = new Lesson();
                    break;
                case "quiz":
                    currentQuiz = new Quiz();
                    break;
                case "question":
                    currentQuestion = new Question();
                    break;
                default:
                    break;
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            text.append(ch, start, length);
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            String element = elementName(localName, qName);
            String value = text.toString().trim();
            if (value.isEmpty()) {
                attachClosedElement(element);
                return;
            }

            switch (element) {
                case "title":
                    course.setTitle(value);
                    break;
                case "price":
                    course.setPrice(new BigDecimal(value));
                    break;
                case "limit":
                    course.setLimit(Integer.parseInt(value));
                    break;
                case "difficulty":
                    course.setDifficulty(CourseDifficulty.valueOf(value));
                    break;
                case "createdAt":
                    course.setCreatedAt(LocalDateTime.parse(value));
                    break;
                case "moduleName":
                    currentModule.setModuleName(value);
                    break;
                case "published":
                    currentModule.setPublished(Boolean.parseBoolean(value));
                    break;
                case "content":
                    currentLesson.setContent(value);
                    break;
                case "practical":
                    currentLesson.setPractical(Boolean.parseBoolean(value));
                    break;
                case "timeLimitMinutes":
                    currentQuiz.setTimeLimit(Integer.parseInt(value));
                    break;
                case "text":
                    currentQuestion.setText(value);
                    break;
                default:
                    break;
            }

            attachClosedElement(element);
        }

        private void attachClosedElement(String element) {
            switch (element) {
                case "question":
                    currentQuiz.getQuestions().add(currentQuestion);
                    currentQuestion = null;
                    break;
                case "lesson":
                    currentModule.getLessons().add(currentLesson);
                    currentLesson = null;
                    break;
                case "quiz":
                    currentModule.setQuiz(currentQuiz);
                    currentQuiz = null;
                    break;
                case "module":
                    course.getModules().add(currentModule);
                    currentModule = null;
                    break;
                default:
                    break;
            }
        }

        private static String elementName(String localName, String qName) {
            return localName == null || localName.isEmpty() ? qName : localName;
        }
    }
}
