package functions;

import materials.Course;
import users.Student;

@FunctionalInterface
public interface CourseEligibilityChecker {

    boolean eligible(Student student, Course course);
}
