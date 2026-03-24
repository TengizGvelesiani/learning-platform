package users;

import contracts.Enrollable;
import interactions.Enrollment;
import materials.Course;

public class Student extends User implements Enrollable {

    private String username;
    private Enrollment[] enrollments;

    public Student(String name, String surname, String email, String username, int maxEnrollments) {
        super(name, surname, email);
        this.username = username;
        this.enrollments = new Enrollment[maxEnrollments];
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Enrollment[] getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(Enrollment[] enrollments) {
        this.enrollments = enrollments;
    }

    public boolean addEnrollment(Enrollment enrollment) {
        for (int i = 0; i < enrollments.length; i++) {
            if (enrollments[i] == null) {
                enrollments[i] = enrollment;
                return true;
            }
        }
        return false;
    }

    public boolean isAlreadyEnrolledIn(Course course) {
        if (course == null) {
            return false;
        }
        for (Enrollment e : enrollments) {
            if (e != null && course.equals(e.getCourse())) {
                return true;
            }
        }
        return false;
    }

    public boolean hasFreeEnrollmentSlot() {
        for (Enrollment e : enrollments) {
            if (e == null) {
                return true;
            }
        }
        return false;
    }
}
