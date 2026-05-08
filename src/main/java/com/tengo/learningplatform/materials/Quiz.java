package com.tengo.learningplatform.materials;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;

import com.tengo.learningplatform.domain.MaterialKind;


@XmlAccessorType(XmlAccessType.NONE)
public class Quiz extends Material {

    private int timeLimitMinutes;
    private final List<Question> questions;

    public Quiz() {
        super("Quiz");
        this.timeLimitMinutes = 0;
        this.questions = new ArrayList<>();
    }

    public Quiz(int limit, List<Question> questions) {
        super("Quiz");
        this.timeLimitMinutes = limit;
        this.questions = questions != null ? new ArrayList<>(questions) : new ArrayList<>();
    }

    @XmlElement(name = "timeLimitMinutes")
    public int getTimeLimit() {
        return timeLimitMinutes;
    }

    public void setTimeLimit(int limit) {
        this.timeLimitMinutes = limit;
    }

    @XmlElementWrapper(name = "questions")
    @XmlElement(name = "question")
    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions.clear();
        if (questions != null) {
            this.questions.addAll(questions);
        }
    }

    public int getNumberOfQuestions() {
        return questions.size();
    }

    @Override
    public String getDisplayName() {
        return "Quiz (" + getNumberOfQuestions() + " questions, " + timeLimitMinutes + " min)";
    }

    @Override
    public MaterialKind getMaterialKind() {
        return MaterialKind.QUIZ;
    }
}
