package service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import exceptions.DuplicateEnrollmentException;
import exceptions.EnrollmentCapacityException;
import exceptions.InsufficientFundsException;
import exceptions.InvalidPurchaseArgumentException;
import exceptions.PaymentAlreadyProcessedException;
import interactions.Certificate;
import interactions.Enrollment;
import interactions.Payment;
import materials.Course;
import users.Student;

public class PurchaseService {

    public Enrollment buyCourse(Student student, Course course, Payment payment)
            throws EnrollmentCapacityException {
        System.out.println("\n--- Starting Transaction ---");
        validatePurchaseArguments(student, course, payment);

        if (payment.isProcessed()) {
            throw new PaymentAlreadyProcessedException(
                    "This payment has already been processed and cannot be reused.");
        }

        if (student.isAlreadyEnrolledIn(course)) {
            throw new DuplicateEnrollmentException(student, course);
        }

        if (!course.hasFreeSeat()) {
            throw new EnrollmentCapacityException("Course is full: " + course.getTitle());
        }
        if (!student.hasFreeEnrollmentSlot()) {
            throw new EnrollmentCapacityException(
                    "Student has reached the maximum number of enrollments: " + student.getUsername());
        }

        if (!hasSufficientFunds(payment.getAmount(), course.getPrice())) {
            throw new InsufficientFundsException(course.getPrice(), payment.getAmount());
        }

        try (PurchaseAuditTrail audit = PurchaseAuditTrail.openDefault()) {
            audit.appendEvent("START | student=" + student.getUsername()
                    + " | course=" + course.getTitle()
                    + " | amount=" + payment.getAmount());

            Enrollment enrollment = new Enrollment("ACTIVE", course, LocalDateTime.now(), payment);

            boolean addedToCourse = course.addEnrollment(enrollment);
            boolean addedToStudent = student.addEnrollment(enrollment);

            if (!addedToCourse || !addedToStudent) {
                if (addedToCourse) {
                    course.removeEnrollment(enrollment);
                }
                throw new EnrollmentCapacityException(
                        "Enrollment storage failed after partial update; transaction rolled back on course.");
            }

            payment.setProcessed(true);
            payment.setProcessedAt(LocalDateTime.now());

            PlatformRegistry.incrementEnrollment();

            audit.appendEvent("SUCCESS | student=" + student.getUsername()
                    + " | course=" + course.getTitle());

            System.out.println("Payment Approved for: " + student.getContactLabel());
            System.out.println("Course: " + course.getTitle() + " has been added to dashboard.");

            Certificate certificate = new Certificate(LocalDate.now(), student, course);
            enrollment.setCertificate(certificate);

            return enrollment;
        } catch (IOException e) {
            throw new IllegalStateException("Purchase audit log failed; enrollment may have completed.", e);
        } finally {
            System.out.println("Transaction attempt finished.");
        }
    }

    private static void validatePurchaseArguments(Student student, Course course, Payment payment) {
        if (student == null) {
            throw new InvalidPurchaseArgumentException("Student is required for a purchase.");
        }
        if (course == null) {
            throw new InvalidPurchaseArgumentException("Course is required for a purchase.");
        }
        if (payment == null) {
            throw new InvalidPurchaseArgumentException("Payment is required for a purchase.");
        }
    }

    public void previewPurchase(Student student, Course course, Payment payment) {
        System.out.println("\n--- Purchase Preview ---");
        validatePurchaseArguments(student, course, payment);
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
