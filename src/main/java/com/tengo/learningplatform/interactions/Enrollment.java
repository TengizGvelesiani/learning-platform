package com.tengo.learningplatform.interactions;

import java.time.LocalDateTime;
import java.util.Objects;

import com.tengo.learningplatform.domain.EnrollmentLifecycleStatus;
import com.tengo.learningplatform.materials.Course;


public class Enrollment extends Record {

    private EnrollmentLifecycleStatus lifecycleStatus;
    private Course course;
    private LocalDateTime enrolledAt;
    private Payment payment;
    private Certificate certificate;

    public Enrollment(EnrollmentLifecycleStatus lifecycleStatus, Course course, LocalDateTime enrolledAt,
                      Payment payment) {
        super(lifecycleStatus.getWireValue());
        this.lifecycleStatus = lifecycleStatus;
        this.course = course;
        this.enrolledAt = enrolledAt;
        this.payment = payment;
        this.certificate = null;
    }

    public EnrollmentLifecycleStatus getLifecycleStatus() {
        return lifecycleStatus;
    }

    public void setLifecycleStatus(EnrollmentLifecycleStatus lifecycleStatus) {
        if (lifecycleStatus != null) {
            this.lifecycleStatus = lifecycleStatus;
            super.setStatus(lifecycleStatus.getWireValue());
        }
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LocalDateTime getEnrolledAt() {
        return enrolledAt;
    }

    public void setEnrolledAt(LocalDateTime enrolledAt) {
        this.enrolledAt = enrolledAt;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    @Override
    public void setStatus(String status) {
        super.setStatus(status);
        this.lifecycleStatus = EnrollmentLifecycleStatus.fromWire(status);
    }

    @Override
    public String getRecordType() {
        return "ENROLLMENT";
    }

    @Override
    public String toString() {
        return "Enrollment{status='" + status + '\'' +
                ", courseTitle='" + (course == null ? null : course.getTitle()) + '\'' +
                ", enrolledAt=" + enrolledAt +
                ", paymentAmount=" + (payment == null ? null : payment.getAmount()) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enrollment that = (Enrollment) o;
        return Objects.equals(course, that.course) &&
                Objects.equals(enrolledAt, that.enrolledAt) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course, enrolledAt, status);
    }
}
