import materials.Course;
import users.Admin;
import users.Instructor;
import users.Student;

public class LearningPlatform {

    private String name;
    private Course[] courses;
    private Student[] students;
    private Instructor[] instructors;
    private Admin[] admins;

    public LearningPlatform(String name, Course[] courses, Student[] students, Instructor[] instructors, Admin[] admins) {
        this.name = name;
        this.courses = courses;
        this.students = students;
        this.instructors = instructors;
        this.admins = admins;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Course[] getCourses() {
        return courses;
    }

    public void setCourses(Course[] courses) {
        this.courses = courses;
    }

    public Student[] getStudents() {
        return students;
    }

    public void setStudents(Student[] students) {
        this.students = students;
    }

    public Instructor[] getInstructors() {
        return instructors;
    }

    public void setInstructors(Instructor[] instructors) {
        this.instructors = instructors;
    }

    public Admin[] getAdmins() {
        return admins;
    }

    public void setAdmins(Admin[] admins) {
        this.admins = admins;
    }
}

