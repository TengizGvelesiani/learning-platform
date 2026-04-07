package com.tengo.learningplatform;

import java.util.ArrayList;
import java.util.List;

import com.tengo.learningplatform.materials.Course;
import com.tengo.learningplatform.users.Admin;
import com.tengo.learningplatform.users.Instructor;
import com.tengo.learningplatform.users.Student;


public class LearningPlatform {

    private String name;
    private final List<Course> courses;
    private final List<Student> students;
    private final List<Instructor> instructors;
    private final List<Admin> admins;

    public LearningPlatform(String name, List<Course> courses, List<Student> students,
                            List<Instructor> instructors, List<Admin> admins) {
        this.name = name;
        this.courses = courses != null ? new ArrayList<>(courses) : new ArrayList<>();
        this.students = students != null ? new ArrayList<>(students) : new ArrayList<>();
        this.instructors = instructors != null ? new ArrayList<>(instructors) : new ArrayList<>();
        this.admins = admins != null ? new ArrayList<>(admins) : new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Instructor> getInstructors() {
        return instructors;
    }

    public List<Admin> getAdmins() {
        return admins;
    }
}
