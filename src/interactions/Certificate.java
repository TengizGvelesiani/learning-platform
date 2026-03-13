package interactions;

import java.time.LocalDate;

import materials.Course;
import users.Student;

public class Certificate {

    private LocalDate issueDate;
    private Student student;
    private Course course;

    public Certificate(LocalDate issueDate, Student student, Course course) {
        this.issueDate = issueDate;
        this.student = student;
        this.course = course;
    }

    public LocalDate getIssueDate() { return issueDate; }
    public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
}
