import java.math.BigDecimal;

import interactions.Enrollment;
import interactions.Payment;
import materials.Course;
import materials.Lesson;
import materials.Module;
import materials.Question;
import materials.Quiz;
import service.DisplayService;
import service.PurchaseService;
import service.PlatformRegistry;
import users.Admin;
import users.Instructor;
import users.Student;

public class Main {

    public static void main(String[] args) {
        Admin admin = new Admin("Marta", "Ivanova", "admin@example.com", 5);

        Instructor instructor = new Instructor(
                "instructor@example.com",
                "Alice",
                "Smith",
                4.8,
                new Course[1]
        );

        Lesson lesson1 = new Lesson("Variables and types");
        Lesson lesson2 = new Lesson("If/else and loops");

        Question question1 = new Question("What is the size of int in Java?");
        Question question2 = new Question("What does JVM stand for?");
        Quiz quiz = new Quiz(15, new Question[]{question1, question2});

        Module module = new Module("Java Basics", new Lesson[]{lesson1, lesson2}, quiz);
        Course course = new Course(
                "Intro to Java",
                new BigDecimal("99.00"),
                instructor,
                2,
                new Module[]{module}
        );

        instructor.setCoursesTaught(new Course[]{course});

        Student student = new Student("Ana", "Johnson", "student@example.com", "anaJ", 3);
        Payment payment = new Payment(new BigDecimal("120.00"));

        LearningPlatform platform = new LearningPlatform(
                "Global Learning Platform",
                new Course[]{course},
                new Student[]{student},
                new Instructor[]{instructor},
                new Admin[]{admin}
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
        Enrollment enrollment = service.buyCourse(student, course, payment);

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


    }
}

