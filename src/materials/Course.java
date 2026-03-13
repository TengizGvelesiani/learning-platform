package materials;

import java.math.BigDecimal;

import users.Instructor;
import interactions.Enrollment;

public class Course {

    private String title;
    private BigDecimal price;
    private Instructor instructor;
    private int limit;
    private Module[] modules;
    private Enrollment[] enrollments;

    public Course(String title, BigDecimal price, Instructor instructor, int limit, Module[] modules) {
        this.title = title;
        this.price = price;
        this.instructor = instructor;
        this.limit = limit;
        this.modules = modules;
        this.enrollments = new Enrollment[limit];
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Instructor getInstructor(){ return instructor; }
    public void setInstructor(Instructor instructor) { this.instructor = instructor; }

    public int getLimit() { return limit; }
    public void setLimit(int limit) { this.limit = limit; }

    public Module[] getModules() { return modules; }
    public void setModules(Module[] modules) { this.modules = modules; }

    public Enrollment[] getEnrollments() { return enrollments; }
    public void setEnrollments(Enrollment[] enrollments) { this.enrollments = enrollments; }

    public int getEnrolledStudentsCount() {
        int count = 0;
        for (Enrollment enrollment : enrollments) {
            if (enrollment != null) {
                count++;
            }
        }
        return count;
    }

    public boolean hasFreeSeat() {
        return getEnrolledStudentsCount() < limit;
    }

    public boolean addEnrollment(Enrollment enrollment) {
        for (int i = 0; i < enrollments.length; i++) {
            if (enrollments[i] == null) {
                enrollments[i] = enrollment;
                return true;
            }
        }
        return false;
    }
}
