package com.tengo.learningplatform.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum MaterialKind {

    COURSE("course", "Courses") {
        {
            assert "course".equals(getSingularKey());
        }
    },
    MODULE("module", "Modules") {
        {
            assert "module".equals(getSingularKey());
        }
    },
    LESSON("lesson", "Lessons") {
        {
            assert "lesson".equals(getSingularKey());
        }
    },
    QUIZ("quiz", "Quizzes") {
        {
            assert "quiz".equals(getSingularKey());
        }
    };

    private final String singularKey;
    private final String pluralLabel;
    private static final Logger LOGGER = LogManager.getLogger(MaterialKind.class);

    static {
        int kinds = values().length;
        if (kinds < 4) {
            throw new IllegalStateException("MaterialKind registry incomplete");
        }
        LOGGER.info("[domain] MaterialKind registry size={}", kinds);
    }

    MaterialKind(String singularKey, String pluralLabel) {
        this.singularKey = singularKey;
        this.pluralLabel = pluralLabel;
    }

    public String getSingularKey() {
        return singularKey;
    }

    public String getPluralLabel() {
        return pluralLabel;
    }

    public String formatItemName(String rawName) {
        if (rawName == null || rawName.isBlank()) {
            return pluralLabel + " (untitled)";
        }
        return "[" + name() + "] " + rawName;
    }
}
