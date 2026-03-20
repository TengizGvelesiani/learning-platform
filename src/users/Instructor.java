package users;

import contracts.ProfileSummarizable;
import materials.Course;

public class Instructor extends Staff implements ProfileSummarizable {

    private double rating;
    private Course[] coursesTaught;

    public Instructor(String email, String name, String surname, double rating, Course[] coursesTaught) {
        super(name, surname, email);
        this.rating = rating;
        this.coursesTaught = coursesTaught;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Course[] getCoursesTaught() {
        return coursesTaught;
    }

    public void setCoursesTaught(Course[] coursesTaught) {
        this.coursesTaught = coursesTaught;
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
