package com.tengo.learningplatform.materials;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

import com.tengo.learningplatform.domain.MaterialKind;


@XmlAccessorType(XmlAccessType.NONE)
public class Lesson extends Material {

    private boolean practical;

    public Lesson() {
        super("");
        this.practical = false;
    }

    public Lesson(String content) {
        super(content);
        this.practical = false;
    }

    @XmlElement(name = "content")
    public String getContent() {
        return name;
    }
    @XmlElement(name = "practical")
    public boolean isPractical() {
        return practical;
    }

    public void setPractical(boolean practical) {
        this.practical = practical;
    }


    public void setContent(String content) {
        this.name = content;
    }

    @Override
    public String getDisplayName() {
        return "Lesson: " + name;
    }

    @Override
    public MaterialKind getMaterialKind() {
        return MaterialKind.LESSON;
    }
}
