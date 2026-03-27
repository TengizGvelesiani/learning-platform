import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import collections.SimpleLinkedList;
import exceptions.EnrollmentCapacityException;
import exceptions.InsufficientFundsException;
import interactions.Enrollment;
import interactions.Payment;
import materials.Course;
import materials.Lesson;
import materials.Module;
import materials.Question;
import materials.Quiz;
import service.DisplayService;
import service.PlatformRegistry;
import service.PurchaseService;
import users.Admin;
import users.Instructor;
import users.Student;
import util.MaterialCatalog;
import util.Pair;

public class Main {

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
                courseModules
        );

        instructor.getCoursesTaught().add(course);

        Student student = new Student("Ana", "Johnson", "student@example.com", "anaJ", 3);
        Payment payment = new Payment(new BigDecimal("120.00"));

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
            System.err.println("[Handled checked exception] " + e.getMessage());
        } catch (InsufficientFundsException e) {
            System.out.println("[Handled runtime exception] " + e.getMessage());
        }

        Enrollment enrollment = null;
        try {
            enrollment = service.buyCourse(student, course, payment);
        } catch (EnrollmentCapacityException e) {
            System.err.println("[Handled checked exception] " + e.getMessage());
        }

        displayService.printRole(admin);
        displayService.printRole(instructor);
        displayService.printProfile(admin);
        displayService.printProfile(instructor);
        displayService.printAmount(payment);
        displayService.printEnrollmentCount(student);
        if (enrollment != null) {
            displayService.printStatus(enrollment);
            if (enrollment.getCertificate() != null) {
                displayService.printStatus(enrollment.getCertificate());
            }
        }

        System.out.println("Total enrollments: " + PlatformRegistry.getTotalEnrollments());
        System.out.println("Platform: " + platform.getName());

        Pair<Student, Course> purchaseAnchor = new Pair<>(student, course);
        MaterialCatalog<Course> materialCatalog = new MaterialCatalog<>();
        materialCatalog.add(course);
        SimpleLinkedList<String> eventLog = new SimpleLinkedList<>();
        eventLog.add("boot");
        eventLog.add("purchase-flow");

        demonstrateCollections(platform, student, lowBalanceStudent, course, purchaseAnchor, materialCatalog, eventLog);
    }

    private static void demonstrateCollections(LearningPlatform platform, Student student, Student otherStudent,
                                               Course course, Pair<Student, Course> pair,
                                               MaterialCatalog<Course> catalog,
                                               SimpleLinkedList<String> linkedLog) {
        List<Course> listDemo = new ArrayList<>(platform.getCourses());
        if (!listDemo.isEmpty()) {
            listDemo.add(course);
            System.out.println("List size after add: " + listDemo.size());
            Course firstListCourse = listDemo.get(0);
            System.out.println("First list course: " + firstListCourse.getTitle());
            for (Course c : listDemo) {
                System.out.println("List iteration: " + c.getDisplayName());
            }
            listDemo.remove(course);
            System.out.println("List size after remove: " + listDemo.size());
        }

        Set<Course> featuredCourses = new LinkedHashSet<>();
        featuredCourses.add(course);
        if (!featuredCourses.isEmpty()) {
            Course firstSetCourse = featuredCourses.iterator().next();
            System.out.println("First set course: " + firstSetCourse.getTitle());
            System.out.println("Set size: " + featuredCourses.size());
            for (Course c : featuredCourses) {
                System.out.println("Set iteration: " + c.getTitle());
            }
        }

        Map<Student, String> studentNotes = new HashMap<>();
        studentNotes.put(student, "Active learner");
        studentNotes.put(otherStudent, "Low-balance preview user");
        System.out.println("Pair demo: " + pair.getFirst().getUsername() + " -> " + pair.getSecond().getTitle());
        if (!studentNotes.isEmpty()) {
            Map.Entry<Student, String> firstEntry = studentNotes.entrySet().iterator().next();
            System.out.println("First map entry key email: " + firstEntry.getKey().getEmail());
            System.out.println("Map get: " + studentNotes.get(student));
            System.out.println("Map size: " + studentNotes.size());
            for (Map.Entry<Student, String> e : studentNotes.entrySet()) {
                System.out.println("Map iteration: " + e.getKey().getUsername() + " -> " + e.getValue());
            }
            studentNotes.remove(otherStudent);
            System.out.println("Map size after remove: " + studentNotes.size());
        }

        Map<Course, Integer> seatsUsed = new LinkedHashMap<>();
        seatsUsed.put(course, course.getEnrolledStudentsCount());
        if (!seatsUsed.isEmpty()) {
            Course firstKey = seatsUsed.keySet().iterator().next();
            System.out.println("First map (course key) title: " + firstKey.getTitle());
            System.out.println("Map put/get seats: " + seatsUsed.get(course));
            for (Map.Entry<Course, Integer> e : seatsUsed.entrySet()) {
                System.out.println("Map iteration (course keys): " + e.getKey().getTitle() + " -> " + e.getValue());
            }
        }

        if (!catalog.isEmpty()) {
            System.out.println("Generic catalog first: " + catalog.get(0).getTitle());
            for (Course c : catalog.getAll()) {
                System.out.println("Catalog iteration: " + c.getDisplayName());
            }
        }

        System.out.println("Linked list first: " + linkedLog.getFirst());
        for (String s : linkedLog) {
            System.out.println("Linked list iteration: " + s);
        }
    }
}
