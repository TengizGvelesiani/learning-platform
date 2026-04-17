package com.tengo.learningplatform.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tengo.learningplatform.annotations.ReflectiveTask;
import com.tengo.learningplatform.domain.EnrollmentLifecycleStatus;
import com.tengo.learningplatform.exceptions.DuplicateEnrollmentException;
import com.tengo.learningplatform.exceptions.EnrollmentCapacityException;
import com.tengo.learningplatform.exceptions.InsufficientFundsException;
import com.tengo.learningplatform.exceptions.InvalidPurchaseArgumentException;
import com.tengo.learningplatform.exceptions.PaymentAlreadyProcessedException;
import com.tengo.learningplatform.interactions.Certificate;
import com.tengo.learningplatform.interactions.Enrollment;
import com.tengo.learningplatform.interactions.Payment;
import com.tengo.learningplatform.interactions.Transaction;
import com.tengo.learningplatform.materials.Course;
import com.tengo.learningplatform.users.Student;


@ReflectiveTask("Purchase flow orchestration")
public class PurchaseService {

    private static final Logger LOGGER = LogManager.getLogger(PurchaseService.class);

    public Enrollment buyCourse(Student student, Course course, Payment payment)
            throws EnrollmentCapacityException {
        LOGGER.info("--- Starting Transaction ---");
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
                    + " | amount=" + payment.getAmount()
                    + " | channel=" + payment.getChannel().receiptTag());

            Enrollment enrollment = new Enrollment(EnrollmentLifecycleStatus.ACTIVE, course,
                    LocalDateTime.now(), payment);

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

            LOGGER.info("Payment Approved for: {}", student.getContactLabel());
            LOGGER.info("Course: {} has been added to dashboard.", course.getTitle());

            Certificate certificate = new Certificate(LocalDate.now(), student, course);
            enrollment.setCertificate(certificate);

            return enrollment;
        } catch (IOException e) {
            throw new IllegalStateException("Purchase audit log failed; enrollment may have completed.", e);
        } finally {
            LOGGER.info("Transaction attempt finished.");
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
        LOGGER.info("--- Purchase Preview ---");
        validatePurchaseArguments(student, course, payment);
        LOGGER.info("Student: {}", student.getUsername());
        LOGGER.info("Course: {} ({})", course.getTitle(), course.getDifficulty().getCatalogLabel());
        LOGGER.info("Price: {}", course.getPrice());
        LOGGER.info("Payment amount: {}", payment.getAmount());
        LOGGER.info("Payment channel: {}, est. processing fee: {}",
                payment.getChannel().receiptTag(), payment.getChannel().estimatedProcessingFee(payment.getAmount()));
        if (hasSufficientFunds(payment.getAmount(), course.getPrice())) {
            BigDecimal change = payment.getAmount().subtract(course.getPrice());
            LOGGER.info("Status: This purchase would be APPROVED. Change: {}", change);
        } else {
            LOGGER.info("Status: This purchase would be DECLINED (insufficient funds).");
        }
    }

    @ReflectiveTask(value = "Funds validation entrypoint", invoke = true)
    public boolean hasSufficientFunds(BigDecimal paymentAmount, BigDecimal coursePrice) {
        return paymentAmount != null && coursePrice != null && paymentAmount.compareTo(coursePrice) >= 0;
    }
}
