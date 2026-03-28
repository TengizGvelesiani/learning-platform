package service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import materials.Course;
import users.Student;

public final class PlatformAnalytics {

    private PlatformAnalytics() {
    }

    public static void printSpotlightDigest(
            List<Course> courses,
            Predicate<Course> include,
            Function<Course, String> lineForCourse,
            UnaryOperator<String> decorateLine,
            Consumer<String> sink,
            Supplier<String> bannerSupplier) {
        if (courses == null || include == null || lineForCourse == null
                || decorateLine == null || sink == null || bannerSupplier == null) {
            return;
        }
        sink.accept(bannerSupplier.get());
        for (Course course : courses) {
            if (include.test(course)) {
                String raw = lineForCourse.apply(course);
                sink.accept(decorateLine.apply(raw));
            }
        }
    }

    public static BigDecimal reduceCoursePrices(
            List<Course> courses,
            Function<Course, BigDecimal> priceExtractor,
            BinaryOperator<BigDecimal> combiner,
            Supplier<BigDecimal> identitySupplier) {
        if (courses == null || priceExtractor == null || combiner == null || identitySupplier == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal acc = identitySupplier.get();
        for (Course c : courses) {
            BigDecimal p = priceExtractor.apply(c);
            if (p != null) {
                acc = combiner.apply(acc, p);
            }
        }
        return acc;
    }

    public static List<Course> recommendCourses(
            List<Course> catalog,
            Student student,
            BiPredicate<Student, Course> match) {
        List<Course> out = new ArrayList<>();
        if (catalog == null || student == null || match == null) {
            return out;
        }
        for (Course c : catalog) {
            if (match.test(student, c)) {
                out.add(c);
            }
        }
        return out;
    }
}
