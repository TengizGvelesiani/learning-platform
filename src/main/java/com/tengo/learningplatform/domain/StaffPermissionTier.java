package com.tengo.learningplatform.domain;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum StaffPermissionTier {

    SUPPORT(1, 2, "Support") {
        {
            assert coversAccessLevel(1) && !coversAccessLevel(3);
        }

        @Override
        public boolean canPublishCourses() {
            return false;
        }
    },
    OPERATOR(3, 4, "Operator") {
        {
            assert coversAccessLevel(3) && !coversAccessLevel(2);
        }

        @Override
        public boolean canPublishCourses() {
            return true;
        }
    },
    SUPER_ADMIN(5, Integer.MAX_VALUE, "Super admin") {
        {
            assert coversAccessLevel(5) && getMaxLevel() == Integer.MAX_VALUE;
        }

        @Override
        public boolean canPublishCourses() {
            return true;
        }
    };

    private static final Logger LOGGER = LogManager.getLogger(StaffPermissionTier.class);

    static {
        LOGGER.info("[domain] StaffPermissionTier tiers initialized.");
    }

    private final int minLevel;
    private final int maxLevel;
    private final String displayName;

    StaffPermissionTier(int minLevel, int maxLevel, String displayName) {
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.displayName = displayName;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean coversAccessLevel(int accessLevel) {
        return accessLevel >= minLevel && accessLevel <= maxLevel;
    }

    public abstract boolean canPublishCourses();

    public static StaffPermissionTier fromAccessLevel(int accessLevel) {
        return Arrays.stream(values())
                .filter(tier -> tier.coversAccessLevel(accessLevel))
                .findFirst()
                .orElse(SUPPORT);
    }
}
