package service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import interactions.Certificate;
import interactions.Enrollment;
import interactions.Payment;
import materials.Course;
import users.Student;

public class PurchaseService {

    public Enrollment buyCourse(Student student, Course course, Payment payment) {
        System.out.println("\n--- Starting Transaction ---");
        if (!course.hasFreeSeat()) {
            System.out.println("Limit Reached.");
            return null;
        }
        if (!hasSufficientFunds(payment.getAmount(), course.getPrice())) {
            System.out.println("Transaction Declined: Insufficient Funds.");
            return null;
        }

        payment.setProcessed(true);
        payment.setProcessedAt(LocalDateTime.now());

        Enrollment enrollment = new Enrollment("ACTIVE", course, LocalDateTime.now(), payment);
        boolean addedToCourse = course.addEnrollment(enrollment);
        boolean addedToStudent = student.addEnrollment(enrollment);

        if (!addedToCourse || !addedToStudent) {
            System.out.println("Transaction Declined: Enrollment storage is full.");
            return null;
        }

        PlatformRegistry.incrementEnrollment();

        System.out.println("Payment Approved for: " + student.getEmail());
        System.out.println("Course: " + course.getTitle() + " has been added to dashboard.");

        Certificate certificate = new Certificate(LocalDate.now(), student, course);
        enrollment.setCertificate(certificate);

        return enrollment;
    }

    public void previewPurchase(Student student, Course course, Payment payment) {
        System.out.println("\n--- Purchase Preview ---");
        System.out.println("Student: " + student.getUsername());
        System.out.println("Course: " + course.getTitle());
        System.out.println("Price: " + course.getPrice());
        System.out.println("Payment amount: " + payment.getAmount());
        if (hasSufficientFunds(payment.getAmount(), course.getPrice())) {
            BigDecimal change = payment.getAmount().subtract(course.getPrice());
            System.out.println("Status: This purchase would be APPROVED. Change: " + change);
        } else {
            System.out.println("Status: This purchase would be DECLINED (insufficient funds).");
        }
    }

    public boolean hasSufficientFunds(BigDecimal paymentAmount, BigDecimal coursePrice) {
        return paymentAmount != null && coursePrice != null && paymentAmount.compareTo(coursePrice) >= 0;
    }

}
