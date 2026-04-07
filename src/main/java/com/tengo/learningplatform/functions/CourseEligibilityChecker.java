package com.tengo.learningplatform.functions;

import com.tengo.learningplatform.materials.Course;
import com.tengo.learningplatform.users.Student;


@FunctionalInterface
public interface CourseEligibilityChecker {

    boolean eligible(Student student, Course course);
}
