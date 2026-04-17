package com.tengo.learningplatform.domain;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tengo.learningplatform.materials.Course;
import com.tengo.learningplatform.users.Student;


public enum EnrollmentLifecycleStatus {

    ACTIVE("ACTIVE") {
        {
            assert "ACTIVE".equals(getWireValue());
        }

        @Override
        public boolean studentMayAccessContent(Student student, Course course) {
            return student != null && course != null;
        }
    },
    COMPLETED("COMPLETED") {
        {
            assert "COMPLETED".equals(getWireValue());
        }

        @Override
        public boolean studentMayAccessContent(Student student, Course course) {
            return student != null && course != null;
        }
    },
    PAUSED("PAUSED") {
        {
            assert "PAUSED".equals(getWireValue());
        }

        @Override
        public boolean studentMayAccessContent(Student student, Course course) {
            return false;
        }
    },
    WITHDRAWN("WITHDRAWN") {
        {
            assert "WITHDRAWN".equals(getWireValue());
        }

        @Override
        public boolean studentMayAccessContent(Student student, Course course) {
            return false;
        }
    };

    private static final Logger LOGGER = LogManager.getLogger(EnrollmentLifecycleStatus.class);

    static {
        LOGGER.info("[domain] EnrollmentLifecycleStatus loaded.");
    }

    private final String wireValue;

    EnrollmentLifecycleStatus(String wireValue) {
        this.wireValue = wireValue;
    }

    public String getWireValue() {
        return wireValue;
    }

    public static EnrollmentLifecycleStatus fromWire(String value) {
        if (value == null) {
            return ACTIVE;
        }
        return Arrays.stream(values())
                .filter(s -> s.wireValue.equalsIgnoreCase(value))
                .findFirst()
                .orElse(ACTIVE);
    }

    public abstract boolean studentMayAccessContent(Student student, Course course);
}
