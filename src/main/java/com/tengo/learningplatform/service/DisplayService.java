package com.tengo.learningplatform.service;

import java.util.List;
import java.util.Objects;

import com.tengo.learningplatform.contracts.Displayable;
import com.tengo.learningplatform.contracts.Enrollable;
import com.tengo.learningplatform.contracts.MoneyMovable;
import com.tengo.learningplatform.contracts.ProfileSummarizable;
import com.tengo.learningplatform.contracts.RoleAssignable;
import com.tengo.learningplatform.contracts.Statused;
import com.tengo.learningplatform.interactions.Enrollment;


public final class DisplayService {

    private static final String PREFIX = "[DISPLAY]";

    public static void printHeader() {
        System.out.println(PREFIX + " Header");
    }

    static {
        System.out.println(PREFIX + " DisplayService class loaded.");
    }

    private Displayable featuredMaterial;

    {
        System.out.println(PREFIX + " DisplayService instance created.");
    }

    public Displayable getFeaturedMaterial() {
        return featuredMaterial;
    }

    public void setFeaturedMaterial(Displayable featuredMaterial) {
        this.featuredMaterial = featuredMaterial;
    }

    public void printMaterialSummary(Displayable material) {
        System.out.println(PREFIX + " " + material.getDisplayName());
    }

    public void printRole(RoleAssignable staff) {
        System.out.println(PREFIX + " Role: " + staff.getRoleLabel());
    }

    public void printProfile(ProfileSummarizable profile) {
        System.out.println(PREFIX + " " + profile.getProfileSummary());
    }

    public void printStatus(Statused record) {
        System.out.println(PREFIX + " Status: " + record.getStatus());
    }

    public void printAmount(MoneyMovable money) {
        System.out.println(PREFIX + " Amount: " + money.getAmount());
    }

    public void printEnrollmentCount(Enrollable enrollable) {
        List<Enrollment> enrollments = enrollable.getEnrollments();
        long count = enrollments.stream().filter(Objects::nonNull).count();
        System.out.println(PREFIX + " Enrollment count: " + count);
    }
}
