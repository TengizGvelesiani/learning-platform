package com.tengo.learningplatform.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.tengo.learningplatform.contracts.Enrollable;
import com.tengo.learningplatform.interactions.Enrollment;
import com.tengo.learningplatform.materials.Course;


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
        return enrollments.stream()
                .filter(Objects::nonNull)
                .anyMatch(e -> course.equals(e.getCourse()));
    }

    public boolean hasFreeEnrollmentSlot() {
        return enrollments.size() < maxEnrollments;
    }
}
