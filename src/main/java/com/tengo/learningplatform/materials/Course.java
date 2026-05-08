package com.tengo.learningplatform.materials;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.tengo.learningplatform.domain.CourseDifficulty;
import com.tengo.learningplatform.domain.MaterialKind;
import com.tengo.learningplatform.interactions.Enrollment;
import com.tengo.learningplatform.parser.LocalDateTimeXmlAdapter;
import com.tengo.learningplatform.users.Instructor;


@XmlRootElement(name = "course")
@XmlAccessorType(XmlAccessType.NONE)
public class Course extends Material {

    private BigDecimal price;
    private Instructor instructor;
    private int limit;
    private CourseDifficulty difficulty;
    private LocalDateTime createdAt;
    private final List<Module> modules;
    private final List<Enrollment> enrollments;

    public Course() {
        super("");
        this.price = BigDecimal.ZERO;
        this.limit = 0;
        this.difficulty = CourseDifficulty.BEGINNER;
        this.createdAt = LocalDateTime.now();
        this.modules = new ArrayList<>();
        this.enrollments = new ArrayList<>();
    }

    public Course(String title, BigDecimal price, Instructor instructor, int limit, List<Module> modules,
                  CourseDifficulty difficulty) {
        super(title);
        this.price = price;
        this.instructor = instructor;
        this.limit = limit;
        this.difficulty = difficulty != null ? difficulty : CourseDifficulty.BEGINNER;
        this.createdAt = LocalDateTime.now();
        this.modules = modules != null ? new ArrayList<>(modules) : new ArrayList<>();
        this.enrollments = new ArrayList<>();
    }

    @XmlElement(name = "title")
    public String getTitle() {
        return name;
    }

    public void setTitle(String title) {
        this.name = title;
    }

    @XmlElement(name = "price")
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

    @XmlElement(name = "limit")
    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @XmlElement(name = "difficulty")
    public CourseDifficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(CourseDifficulty difficulty) {
        this.difficulty = difficulty != null ? difficulty : CourseDifficulty.BEGINNER;
    }

    @XmlJavaTypeAdapter(LocalDateTimeXmlAdapter.class)
    @XmlElement(name = "createdAt")
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @XmlElementWrapper(name = "modules")
    @XmlElement(name = "module")
    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules.clear();
        if (modules != null) {
            this.modules.addAll(modules);
        }
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
    public MaterialKind getMaterialKind() {
        return MaterialKind.COURSE;
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
