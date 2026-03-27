package users;

import java.util.ArrayList;
import java.util.List;

import contracts.ProfileSummarizable;
import materials.Course;

public class Instructor extends Staff implements ProfileSummarizable {

    private double rating;
    private final List<Course> coursesTaught;

    public Instructor(String email, String name, String surname, double rating, List<Course> coursesTaught) {
        super(name, surname, email);
        this.rating = rating;
        this.coursesTaught = coursesTaught != null ? new ArrayList<>(coursesTaught) : new ArrayList<>();
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<Course> getCoursesTaught() {
        return coursesTaught;
    }

    @Override
    public String getRoleLabel() {
        return "INSTRUCTOR";
    }

    @Override
    public String getProfileSummary() {
        return "Instructor " + getContactLabel() + ", rating " + rating;
    }

}
