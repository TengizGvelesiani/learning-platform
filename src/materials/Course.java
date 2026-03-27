package materials;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import users.Instructor;
import interactions.Enrollment;

public class Course extends Material {

    private BigDecimal price;
    private Instructor instructor;
    private int limit;
    private final List<Module> modules;
    private final List<Enrollment> enrollments;

    public Course(String title, BigDecimal price, Instructor instructor, int limit, List<Module> modules) {
        super(title);
        this.price = price;
        this.instructor = instructor;
        this.limit = limit;
        this.modules = modules != null ? new ArrayList<>(modules) : new ArrayList<>();
        this.enrollments = new ArrayList<>();
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

    public List<Module> getModules() {
        return modules;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public int getEnrolledStudentsCount() {
        return enrollments.size();
    }

    public boolean hasFreeSeat() {
        return enrollments.size() < limit;
    }

    public boolean addEnrollment(Enrollment enrollment) {
        if (enrollment == null || enrollments.size() >= limit) {
            return false;
        }
        enrollments.add(enrollment);
        return true;
    }

    public boolean removeEnrollment(Enrollment enrollment) {
        return enrollments.remove(enrollment);
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
