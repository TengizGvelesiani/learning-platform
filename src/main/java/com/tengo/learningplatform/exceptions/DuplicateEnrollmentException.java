package com.tengo.learningplatform.exceptions;

import com.tengo.learningplatform.materials.Course;
import com.tengo.learningplatform.users.Student;


public class DuplicateEnrollmentException extends RuntimeException {

    public DuplicateEnrollmentException(Student student, Course course) {
        super(String.format("Student %s is already enrolled in %s",
                student != null ? student.getUsername() : "?",
                course != null ? course.getTitle() : "?"));
    }
}
