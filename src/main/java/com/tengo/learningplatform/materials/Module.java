package com.tengo.learningplatform.materials;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;

import com.tengo.learningplatform.domain.MaterialKind;


@XmlAccessorType(XmlAccessType.NONE)
public class Module extends Material {

    private final List<Lesson> lessons;
    private Quiz quiz;
    private boolean published;

    public Module() {
        super("");
        this.lessons = new ArrayList<>();
        this.quiz = null;
        this.published = false;
    }

    public Module(String name, List<Lesson> lessons, Quiz quiz) {
        super(name);
        this.lessons = lessons != null ? new ArrayList<>(lessons) : new ArrayList<>();
        this.quiz = quiz;
        this.published = false;
    }

    @XmlElement(name = "moduleName")
    public String getModuleName() {
        return name;
    }

    public void setModuleName(String name) {
        this.name = name;
    }

    @XmlElementWrapper(name = "lessons")
    @XmlElement(name = "lesson")
    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons.clear();
        if (lessons != null) {
            this.lessons.addAll(lessons);
        }
    }

    @XmlElement(name = "quiz")
    public Quiz getQuiz() {
        return quiz;
    }
    @XmlElement(name = "published")
    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
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
