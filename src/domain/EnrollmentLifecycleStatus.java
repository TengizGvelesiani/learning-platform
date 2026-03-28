package domain;

import materials.Course;
import users.Student;

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

    static {
        System.out.println("[domain] EnrollmentLifecycleStatus loaded.");
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
        for (EnrollmentLifecycleStatus s : values()) {
            if (s.wireValue.equalsIgnoreCase(value)) {
                return s;
            }
        }
        return ACTIVE;
    }

    public abstract boolean studentMayAccessContent(Student student, Course course);
}
