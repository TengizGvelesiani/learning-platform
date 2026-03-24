package materials;

import java.math.BigDecimal;
import java.util.Objects;

import users.Instructor;
import interactions.Enrollment;

public class Course extends Material {

    private BigDecimal price;
    private Instructor instructor;
    private int limit;
    private Module[] modules;
    private Enrollment[] enrollments;

    public Course(String title, BigDecimal price, Instructor instructor, int limit, Module[] modules) {
        super(title);
        this.price = price;
        this.instructor = instructor;
        this.limit = limit;
        this.modules = modules;
        this.enrollments = new Enrollment[limit];
    }

    public String getTitle() {
        return name;
    }

    public void setTitle(String title) {
        this.name = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public Module[] getModules() {
        return modules;
    }

    public void setModules(Module[] modules) {
        this.modules = modules;
    }

    public Enrollment[] getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(Enrollment[] enrollments) {
        this.enrollments = enrollments;
    }

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

    public boolean removeEnrollment(Enrollment enrollment) {
        if (enrollment == null) {
            return false;
        }
        for (int i = 0; i < enrollments.length; i++) {
            if (enrollments[i] == enrollment) {
                enrollments[i] = null;
                return true;
            }
        }
        return false;
    }

    @Override
    public String getDisplayName() {
        return "Course: " + name;
    }

    @Override
    public String toString() {
        return "Course{title='" + name + "', price=" + price +
                ", instructorEmail='" + (instructor == null ? null : instructor.getEmail()) + "'" +
                ", limit=" + limit +
                ", enrolled=" + getEnrolledStudentsCount() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(name, course.name) &&
                Objects.equals(instructor == null ? null : instructor.getEmail(),
                        course.instructor == null ? null : course.instructor.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, instructor == null ? null : instructor.getEmail());
    }
}
