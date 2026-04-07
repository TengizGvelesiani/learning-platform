package com.tengo.learningplatform.dto;

import java.math.BigDecimal;
import java.util.Objects;

import com.tengo.learningplatform.domain.CourseDifficulty;
import com.tengo.learningplatform.domain.MaterialKind;
import com.tengo.learningplatform.materials.Course;


public final class CourseSummary {

    private final String title;
    private final BigDecimal listPrice;
    private final String instructorEmail;
    private final CourseDifficulty difficulty;
    private final int openSeats;
    private final MaterialKind materialKind;

    public CourseSummary(String title, BigDecimal listPrice, String instructorEmail,
                         CourseDifficulty difficulty, int openSeats, MaterialKind materialKind) {
        this.title = title;
        this.listPrice = listPrice;
        this.instructorEmail = instructorEmail;
        this.difficulty = difficulty;
        this.openSeats = openSeats;
        this.materialKind = materialKind;
    }

    public static CourseSummary from(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("course is required");
        }
        int open = Math.max(0, course.getLimit() - course.getEnrolledStudentsCount());
        String email = course.getInstructor() == null ? null : course.getInstructor().getEmail();
        return new CourseSummary(
                course.getTitle(),
                course.getPrice(),
                email,
                course.getDifficulty(),
                open,
                course.getMaterialKind());
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public CourseDifficulty getDifficulty() {
        return difficulty;
    }

    public int getOpenSeats() {
        return openSeats;
    }

    public MaterialKind getMaterialKind() {
        return materialKind;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CourseSummary that = (CourseSummary) o;
        return openSeats == that.openSeats
                && Objects.equals(title, that.title)
                && Objects.equals(listPrice, that.listPrice)
                && Objects.equals(instructorEmail, that.instructorEmail)
                && difficulty == that.difficulty
                && materialKind == that.materialKind;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, listPrice, instructorEmail, difficulty, openSeats, materialKind);
    }

    @Override
    public String toString() {
        return "CourseSummary[title=" + title
                + ", listPrice=" + listPrice
                + ", instructorEmail=" + instructorEmail
                + ", difficulty=" + difficulty
                + ", openSeats=" + openSeats
                + ", materialKind=" + materialKind
                + "]";
    }
}
