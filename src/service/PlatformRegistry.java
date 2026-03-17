package service;

public class PlatformRegistry {

    private static String platformName;
    private static int totalEnrollments = 0;

    static {
        platformName = "Global Learning Platform";
        System.out.println(platformName + " initialized.");
    }

    public static void incrementEnrollment() {
        totalEnrollments++;
    }

    public static int getTotalEnrollments() {
        return totalEnrollments;
    }
}
