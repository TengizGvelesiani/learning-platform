package interactions;

import java.time.LocalDateTime;
import java.util.Objects;

import materials.Course;

public class Enrollment extends Record {

    private Course course;
    private LocalDateTime enrolledAt;
    private Payment payment;
    private Certificate certificate;

    public Enrollment(String status, Course course, LocalDateTime enrolledAt, Payment payment) {
        super(status);
        this.course = course;
        this.enrolledAt = enrolledAt;
        this.payment = payment;
        this.certificate = null;
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
