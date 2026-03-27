package users;

import java.util.ArrayList;
import java.util.List;

import contracts.Enrollable;
import interactions.Enrollment;
import materials.Course;

public class Student extends User implements Enrollable {

    private String username;
    private final List<Enrollment> enrollments;
    private final int maxEnrollments;

    public Student(String name, String surname, String email, String username, int maxEnrollments) {
        super(name, surname, email);
        this.username = username;
        this.maxEnrollments = maxEnrollments;
        this.enrollments = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public boolean addEnrollment(Enrollment enrollment) {
        if (enrollment == null || enrollments.size() >= maxEnrollments) {
            return false;
        }
        enrollments.add(enrollment);
        return true;
    }

    public boolean isAlreadyEnrolledIn(Course course) {
        if (course == null) {
            return false;
        }
        for (Enrollment e : enrollments) {
            if (course.equals(e.getCourse())) {
                return true;
            }
        }
        return false;
    }

    public boolean hasFreeEnrollmentSlot() {
        return enrollments.size() < maxEnrollments;
    }
}
