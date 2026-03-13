package interactions;

import java.time.LocalDateTime;

import materials.Course;

public class Enrollment {

    private String status;
    private Course course;
    private LocalDateTime enrolledAt;
    private Payment payment;
    private Certificate certificate;

    public Enrollment(String status, Course course, LocalDateTime enrolledAt, Payment payment) {
        this.status = status;
        this.course = course;
        this.enrolledAt = enrolledAt;
        this.payment = payment;
        this.certificate = null;
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public LocalDateTime getEnrolledAt() { return enrolledAt; }
    public void setEnrolledAt(LocalDateTime enrolledAt) { this.enrolledAt = enrolledAt; }

    public Payment getPayment() { return payment; }
    public void setPayment(Payment payment) { this.payment = payment; }

    public Certificate getCertificate() { return certificate; }
    public void setCertificate(Certificate certificate) { this.certificate = certificate; }
}
