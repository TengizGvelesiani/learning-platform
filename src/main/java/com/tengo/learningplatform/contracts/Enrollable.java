package com.tengo.learningplatform.contracts;

import java.util.List;

import com.tengo.learningplatform.interactions.Enrollment;


public interface Enrollable {

    List<Enrollment> getEnrollments();
}
