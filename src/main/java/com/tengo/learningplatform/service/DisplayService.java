package com.tengo.learningplatform.service;

import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tengo.learningplatform.contracts.Displayable;
import com.tengo.learningplatform.contracts.Enrollable;
import com.tengo.learningplatform.contracts.MoneyMovable;
import com.tengo.learningplatform.contracts.ProfileSummarizable;
import com.tengo.learningplatform.contracts.RoleAssignable;
import com.tengo.learningplatform.contracts.Statused;
import com.tengo.learningplatform.interactions.Enrollment;


public final class DisplayService {

    private static final Logger LOGGER = LogManager.getLogger(DisplayService.class);
    private static final String PREFIX = "[DISPLAY]";

    public static void printHeader() {
        LOGGER.info("{} Header", PREFIX);
    }

    static {
        LOGGER.info("{} DisplayService class loaded.", PREFIX);
    }

    private Displayable featuredMaterial;

    {
        LOGGER.info("{} DisplayService instance created.", PREFIX);
    }

    public Displayable getFeaturedMaterial() {
        return featuredMaterial;
    }

    public void setFeaturedMaterial(Displayable featuredMaterial) {
        this.featuredMaterial = featuredMaterial;
    }

    public void printMaterialSummary(Displayable material) {
        LOGGER.info("{} {}", PREFIX, material.getDisplayName());
    }

    public void printRole(RoleAssignable staff) {
        LOGGER.info("{} Role: {}", PREFIX, staff.getRoleLabel());
    }

    public void printProfile(ProfileSummarizable profile) {
        LOGGER.info("{} {}", PREFIX, profile.getProfileSummary());
    }

    public void printStatus(Statused record) {
        LOGGER.info("{} Status: {}", PREFIX, record.getStatus());
    }

    public void printAmount(MoneyMovable money) {
        LOGGER.info("{} Amount: {}", PREFIX, money.getAmount());
    }

    public void printEnrollmentCount(Enrollable enrollable) {
        List<Enrollment> enrollments = enrollable.getEnrollments();
        long count = enrollments.stream().filter(Objects::nonNull).count();
        LOGGER.info("{} Enrollment count: {}", PREFIX, count);
    }
}
