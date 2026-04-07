package com.tengo.learningplatform.materials;

import java.util.ArrayList;
import java.util.List;

import com.tengo.learningplatform.domain.MaterialKind;


public class Module extends Material {

    private final List<Lesson> lessons;
    private Quiz quiz;

    public Module(String name, List<Lesson> lessons, Quiz quiz) {
        super(name);
        this.lessons = lessons != null ? new ArrayList<>(lessons) : new ArrayList<>();
        this.quiz = quiz;
    }

    public String getModuleName() {
        return name;
    }

    public void setModuleName(String name) {
        this.name = name;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    @Override
    public String getDisplayName() {
        return "Module: " + name;
    }

    @Override
    public MaterialKind getMaterialKind() {
        return MaterialKind.MODULE;
    }
}
