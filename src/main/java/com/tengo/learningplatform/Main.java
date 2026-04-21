package com.tengo.learningplatform;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tengo.learningplatform.collections.SimpleLinkedList;
import com.tengo.learningplatform.domain.CourseDifficulty;
import com.tengo.learningplatform.domain.MaterialKind;
import com.tengo.learningplatform.domain.PaymentChannel;
import com.tengo.learningplatform.dto.CourseSummary;
import com.tengo.learningplatform.exceptions.EnrollmentCapacityException;
import com.tengo.learningplatform.exceptions.InsufficientFundsException;
import com.tengo.learningplatform.functions.CourseEligibilityChecker;
import com.tengo.learningplatform.functions.LearningOutcomeReporter;
import com.tengo.learningplatform.functions.PricingAdjuster;
import com.tengo.learningplatform.interactions.Enrollment;
import com.tengo.learningplatform.interactions.Payment;
import com.tengo.learningplatform.materials.Course;
import com.tengo.learningplatform.materials.Lesson;
import com.tengo.learningplatform.materials.Module;
import com.tengo.learningplatform.materials.Question;
import com.tengo.learningplatform.materials.Quiz;
import com.tengo.learningplatform.service.DisplayService;
import com.tengo.learningplatform.service.BookWordStatsService;
import com.tengo.learningplatform.service.ConcurrencyHomeworkService;
import com.tengo.learningplatform.service.PlatformAnalytics;
import com.tengo.learningplatform.service.PlatformRegistry;
import com.tengo.learningplatform.service.PurchaseService;
import com.tengo.learningplatform.service.ReflectionShowcase;
import com.tengo.learningplatform.users.Admin;
import com.tengo.learningplatform.users.Instructor;
import com.tengo.learningplatform.users.Student;
import com.tengo.learningplatform.util.MaterialCatalog;
import com.tengo.learningplatform.util.Pair;


public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        Admin admin = new Admin("Marta", "Ivanova", "admin@example.com", 5);

        List<Course> instructorInitialCourses = new ArrayList<>();
        Instructor instructor = new Instructor(
                "instructor@example.com",
                "Alice",
                "Smith",
                4.8,
                instructorInitialCourses
        );

        Lesson lesson1 = new Lesson("Variables and types");
        Lesson lesson2 = new Lesson("If/else and loops");

        Question question1 = new Question("What is the size of int in Java?");
        Question question2 = new Question("What does JVM stand for?");
        List<Question> quizQuestions = new ArrayList<>();
        quizQuestions.add(question1);
        quizQuestions.add(question2);
        Quiz quiz = new Quiz(15, quizQuestions);

        List<Lesson> moduleLessons = new ArrayList<>();
        moduleLessons.add(lesson1);
        moduleLessons.add(lesson2);
        Module module = new Module("Java Basics", moduleLessons, quiz);

        List<Module> courseModules = new ArrayList<>();
        courseModules.add(module);
        Course course = new Course(
                "Intro to Java",
                new BigDecimal("99.00"),
                instructor,
                2,
                courseModules,
                CourseDifficulty.BEGINNER
        );

        instructor.getCoursesTaught().add(course);

        Student student = new Student("Ana", "Johnson", "student@example.com", "anaJ", 3);
        Payment payment = new Payment(new BigDecimal("120.00"), PaymentChannel.CARD);

        List<Course> platformCourses = new ArrayList<>();
        platformCourses.add(course);
        List<Student> platformStudents = new ArrayList<>();
        platformStudents.add(student);
        List<Instructor> platformInstructors = new ArrayList<>();
        platformInstructors.add(instructor);
        List<Admin> platformAdmins = new ArrayList<>();
        platformAdmins.add(admin);

        LearningPlatform platform = new LearningPlatform(
                "Global Learning Platform",
                platformCourses,
                platformStudents,
                platformInstructors,
                platformAdmins
        );

        PurchaseService service = new PurchaseService();

        DisplayService displayService = new DisplayService();
        displayService.setFeaturedMaterial(course);
        displayService.printMaterialSummary(displayService.getFeaturedMaterial());
        displayService.setFeaturedMaterial(module);
        displayService.printMaterialSummary(displayService.getFeaturedMaterial());
        displayService.printMaterialSummary(lesson1);
        displayService.printMaterialSummary(quiz);

        service.previewPurchase(student, course, payment);

        Student lowBalanceStudent = new Student("Pat", "Poor", "poor@example.com", "patP", 3);
        try {
            service.buyCourse(lowBalanceStudent, course, new Payment(new BigDecimal("10.00")));
        } catch (EnrollmentCapacityException e) {
            LOGGER.error("[Handled checked exception] " + e.getMessage());
        } catch (InsufficientFundsException e) {
            LOGGER.info("[Handled runtime exception] " + e.getMessage());
        }

        Enrollment enrollment = null;
        try {
            enrollment = service.buyCourse(student, course, payment);
        } catch (EnrollmentCapacityException e) {
            LOGGER.error("[Handled checked exception] " + e.getMessage());
        }

        displayService.printRole(admin);
        displayService.printRole(instructor);
        displayService.printProfile(admin);
        displayService.printProfile(instructor);
        displayService.printAmount(payment);
        displayService.printEnrollmentCount(student);
        if (enrollment != null) {
            displayService.printStatus(enrollment);
            LOGGER.info("Enrollment lifecycle enum: " + enrollment.getLifecycleStatus()
                    + ", content access: "
                    + enrollment.getLifecycleStatus().studentMayAccessContent(student, course));
            if (enrollment.getCertificate() != null) {
                displayService.printStatus(enrollment.getCertificate());
            }
        }

        LOGGER.info("Total enrollments: " + PlatformRegistry.getTotalEnrollments());
        LOGGER.info("Platform: " + platform.getName());

        Pair<Student, Course> purchaseAnchor = new Pair<>(student, course);
        MaterialCatalog<Course> materialCatalog = new MaterialCatalog<>();
        materialCatalog.add(course);
        SimpleLinkedList<String> eventLog = new SimpleLinkedList<>();
        eventLog.add("boot");
        eventLog.add("purchase-flow");

        demonstrateCollections(platform, student, lowBalanceStudent, course, purchaseAnchor, materialCatalog, eventLog);
        demonstrateLambdasEnumsAndSummary(platform, student, lowBalanceStudent, course, payment, module);
        ReflectionShowcase.run(student, course, payment);
        new BookWordStatsService().writeUniqueWordCount("to_kill_a_mockingbird.txt", "logs/unique-words.txt");
        new ConcurrencyHomeworkService().runShowcase();
    }

    private static void demonstrateLambdasEnumsAndSummary(LearningPlatform platform, Student student,
                                                          Student prospectStudent, Course course,
                                                          Payment payment, Module module) {
        CourseSummary summary = CourseSummary.from(course);
        LOGGER.info("\n--- CourseSummary (immutable DTO) ---");
        LOGGER.info(summary);
        LOGGER.info("CourseSummary title: " + summary.getTitle() + ", open seats: " + summary.getOpenSeats());

        CourseEligibilityChecker eligibility = (s, c) -> s.hasFreeEnrollmentSlot() && c.hasFreeSeat()
                && c.getDifficulty().instructorCanTeach(c.getInstructor());
        LOGGER.info("Custom CourseEligibilityChecker: " + eligibility.eligible(student, course));

        LearningOutcomeReporter outcomeReporter = (material, detail) -> LOGGER.info(
                "[OUTCOME] " + material.getMaterialKind().formatItemName(material.getName()) + " -> " + detail);
        outcomeReporter.report(module, "module progress checkpoint");

        PricingAdjuster introDiscount = (base, c) ->
                c.getDifficulty() == CourseDifficulty.BEGINNER
                        ? base.multiply(new BigDecimal("0.95"))
                        : base;
        LOGGER.info("Custom PricingAdjuster (5% off beginner): "
                + introDiscount.adjust(course.getPrice(), course));

        LOGGER.info("\n--- java.util.function via PlatformAnalytics ---");
        PlatformAnalytics.printSpotlightDigest(
                platform.getCourses(),
                c -> c.getDifficulty().getRank() <= CourseDifficulty.INTERMEDIATE.getRank(),
                c -> c.getTitle() + " @ " + c.getPrice(),
                line -> " * " + line,
                LOGGER::info,
                () -> "[Analytics] Spotlight catalog lines");

        BigDecimal foldedPrices = PlatformAnalytics.reduceCoursePrices(
                platform.getCourses(),
                Course::getPrice,
                BigDecimal::add,
                () -> BigDecimal.ZERO);
        LOGGER.info("[Analytics] Folded list prices (BinaryOperator sum): " + foldedPrices);

        List<Course> suggested = PlatformAnalytics.recommendCourses(
                platform.getCourses(),
                prospectStudent,
                (s, c) -> !s.isAlreadyEnrolledIn(c) && c.getMaterialKind() == MaterialKind.COURSE);
        LOGGER.info("[Analytics] BiPredicate recommendations for prospect (not enrolled): " + suggested.size());

        List<String> suggestionLines = PlatformAnalytics.describeCourseMatches(
                platform.getCourses(),
                prospectStudent,
                (s, c) -> !s.isAlreadyEnrolledIn(c) && c.getMaterialKind() == MaterialKind.COURSE,
                (s, c) -> s.getUsername() + " can enroll in " + c.getTitle() + " (" + c.getDifficulty().name() + ")",
                (s, c) -> LOGGER.info("[Analytics] BiConsumer matched " + s.getUsername()
                        + " with " + c.getTitle()));
        for (String line : suggestionLines) {
            LOGGER.info("[Analytics] BiFunction detail: " + line);
        }

        PlatformAnalytics.runNamedTask(
                "Runnable purchase readiness check",
                () -> LOGGER.info("[Analytics] Prospect slots available: " + prospectStudent.hasFreeEnrollmentSlot()),
                LOGGER::info);

        LOGGER.info("Admin tier: " + adminTierLabel(platform));
        LOGGER.info("Payment channel " + payment.getChannel().receiptTag()
                + ", est. fee: " + payment.getChannel().estimatedProcessingFee(payment.getAmount()));
    }

    private static String adminTierLabel(LearningPlatform platform) {
        if (platform.getAdmins().isEmpty()) {
            return "n/a";
        }
        return platform.getAdmins().get(0).getPermissionTier().getDisplayName();
    }

    private static void demonstrateCollections(LearningPlatform platform, Student student, Student otherStudent,
                                               Course course, Pair<Student, Course> pair,
                                               MaterialCatalog<Course> catalog,
                                               SimpleLinkedList<String> linkedLog) {
        List<Course> listDemo = new ArrayList<>(platform.getCourses());
        if (!listDemo.isEmpty()) {
            listDemo.add(course);
            LOGGER.info("List size after add: " + listDemo.size());
            Course firstListCourse = listDemo.get(0);
            LOGGER.info("First list course: " + firstListCourse.getTitle());
            for (Course courseItem : listDemo) {
                LOGGER.info("List iteration: " + courseItem.getDisplayName());
            }
            listDemo.remove(course);
            LOGGER.info("List size after remove: " + listDemo.size());
        }

        Set<Course> featuredCourses = new LinkedHashSet<>();
        featuredCourses.add(course);
        if (!featuredCourses.isEmpty()) {
            Course firstSetCourse = featuredCourses.iterator().next();
            LOGGER.info("First set course: " + firstSetCourse.getTitle());
            LOGGER.info("Set size: " + featuredCourses.size());
            for (Course courseItem : featuredCourses) {
                LOGGER.info("Set iteration: " + courseItem.getTitle());
            }
        }

        Map<Student, String> studentNotes = new HashMap<>();
        studentNotes.put(student, "Active learner");
        studentNotes.put(otherStudent, "Low-balance preview user");
        LOGGER.info("Pair demo: " + pair.getFirst().getUsername() + " -> " + pair.getSecond().getTitle());
        if (!studentNotes.isEmpty()) {
            Map.Entry<Student, String> firstEntry = studentNotes.entrySet().iterator().next();
            LOGGER.info("First map entry key email: " + firstEntry.getKey().getEmail());
            LOGGER.info("Map get: " + studentNotes.get(student));
            LOGGER.info("Map size: " + studentNotes.size());
            for (Map.Entry<Student, String> entry : studentNotes.entrySet()) {
                LOGGER.info("Map iteration: " + entry.getKey().getUsername() + " -> " + entry.getValue());
            }
            studentNotes.remove(otherStudent);
            LOGGER.info("Map size after remove: " + studentNotes.size());
        }

        Map<Course, Integer> seatsUsed = new LinkedHashMap<>();
        seatsUsed.put(course, course.getEnrolledStudentsCount());
        if (!seatsUsed.isEmpty()) {
            Course firstKey = seatsUsed.keySet().iterator().next();
            LOGGER.info("First map (course key) title: " + firstKey.getTitle());
            LOGGER.info("Map put/get seats: " + seatsUsed.get(course));
            for (Map.Entry<Course, Integer> entry : seatsUsed.entrySet()) {
                LOGGER.info("Map iteration (course keys): " + entry.getKey().getTitle() + " -> " + entry.getValue());
            }
        }

        if (!catalog.isEmpty()) {
            LOGGER.info("Generic catalog first: " + catalog.get(0).getTitle());
            for (Course courseItem : catalog.getAll()) {
                LOGGER.info("Catalog iteration: " + courseItem.getDisplayName());
            }
        }

        LOGGER.info("Linked list first: " + linkedLog.getFirst());
        for (String logEntry : linkedLog) {
            LOGGER.info("Linked list iteration: " + logEntry);
        }
    }
}
