package exceptions;

import materials.Course;
import users.Student;

public class DuplicateEnrollmentException extends RuntimeException {

    private final Student student;
    private final Course course;

    public DuplicateEnrollmentException(Student student, Course course) {
        super(String.format("Student %s is already enrolled in %s",
                student != null ? student.getUsername() : "?",
                course != null ? course.getTitle() : "?"));
        this.student = student;
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }
}
