package com.tengo.learningplatform.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tengo.learningplatform.users.Instructor;


public enum CourseDifficulty {

    BEGINNER(1, "Beginner", 20) {
        {
            assert getRank() > 0 && getSuggestedStudyHours() > 0;
        }

        @Override
        public boolean instructorCanTeach(Instructor instructor) {
            return instructor != null && instructor.getRating() >= 3.5;
        }
    },
    INTERMEDIATE(2, "Intermediate", 40) {
        {
            assert getRank() == 2 && getSuggestedStudyHours() == 40;
        }

        @Override
        public boolean instructorCanTeach(Instructor instructor) {
            return instructor != null && instructor.getRating() >= 4.0;
        }
    },
    ADVANCED(3, "Advanced", 80) {
        {
            assert getRank() == 3 && getCatalogLabel().equals("Advanced");
        }

        @Override
        public boolean instructorCanTeach(Instructor instructor) {
            return instructor != null && instructor.getRating() >= 4.5;
        }
    };

    private static final Logger LOGGER = LogManager.getLogger(CourseDifficulty.class);

    static {
        LOGGER.info("[domain] CourseDifficulty enum type ready.");
    }

    private final int rank;
    private final String catalogLabel;
    private final int suggestedStudyHours;

    CourseDifficulty(int rank, String catalogLabel, int suggestedStudyHours) {
        this.rank = rank;
        this.catalogLabel = catalogLabel;
        this.suggestedStudyHours = suggestedStudyHours;
    }

    public int getRank() {
        return rank;
    }

    public String getCatalogLabel() {
        return catalogLabel;
    }

    public int getSuggestedStudyHours() {
        return suggestedStudyHours;
    }

    public boolean isHarderThan(CourseDifficulty other) {
        return other != null && this.rank > other.rank;
    }

    public abstract boolean instructorCanTeach(Instructor instructor);
}
