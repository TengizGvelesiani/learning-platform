package service;

import contracts.Enrollable;
import contracts.Displayable;
import contracts.MoneyMovable;
import contracts.RoleAssignable;
import contracts.Statused;
import interactions.Enrollment;

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

    public final void printMaterialSummary(Displayable material) {
        System.out.println(PREFIX + " " + material.getDisplayName());
    }

    public final void printRole(RoleAssignable staff) {
        System.out.println(PREFIX + " Role: " + staff.getRoleLabel());
    }

    public final void printStatus(Statused record) {
        System.out.println(PREFIX + " Status: " + record.getStatus());
    }

    public final void printAmount(MoneyMovable money) {
        System.out.println(PREFIX + " Amount: " + money.getAmount());
    }

    public final void printEnrollmentCount(Enrollable enrollable) {
        Enrollment[] enrollments = enrollable.getEnrollments();
        int count = 0;
        for (Enrollment e : enrollments) {
            if (e != null) {
                count++;
            }
        }
        System.out.println(PREFIX + " Enrollment count: " + count);
    }
}

