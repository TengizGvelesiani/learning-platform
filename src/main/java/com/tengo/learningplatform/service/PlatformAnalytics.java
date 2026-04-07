package com.tengo.learningplatform.service;

import java.math.BigDecimal;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.tengo.learningplatform.materials.Course;
import com.tengo.learningplatform.users.Student;


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
        courses.stream()
                .filter(include)
                .map(lineForCourse)
                .map(decorateLine)
                .forEach(sink);
    }

    public static BigDecimal reduceCoursePrices(
            List<Course> courses,
            Function<Course, BigDecimal> priceExtractor,
            BinaryOperator<BigDecimal> combiner,
            Supplier<BigDecimal> identitySupplier) {
        if (courses == null || priceExtractor == null || combiner == null || identitySupplier == null) {
            return BigDecimal.ZERO;
        }
        return courses.stream()
                .map(priceExtractor)
                .filter(Objects::nonNull)
                .reduce(identitySupplier.get(), combiner);
    }

    public static List<Course> recommendCourses(
            List<Course> catalog,
            Student student,
            BiPredicate<Student, Course> match) {
        if (catalog == null || student == null || match == null) {
            return List.of();
        }
        return catalog.stream()
                .filter(c -> match.test(student, c))
                .collect(Collectors.toList());
    }

    public static List<String> describeCourseMatches(
            List<Course> catalog,
            Student student,
            BiPredicate<Student, Course> match,
            BiFunction<Student, Course, String> describer,
            BiConsumer<Student, Course> onMatch) {
        if (catalog == null || student == null || match == null || describer == null || onMatch == null) {
            return List.of();
        }
        return catalog.stream()
                .filter(c -> match.test(student, c))
                .peek(c -> onMatch.accept(student, c))
                .map(c -> describer.apply(student, c))
                .collect(Collectors.toList());
    }

    public static void runNamedTask(String label, Runnable task, Consumer<String> sink) {
        if (label == null || task == null || sink == null) {
            return;
        }
        sink.accept("[Analytics] Starting task: " + label);
        task.run();
        sink.accept("[Analytics] Finished task: " + label);
    }
}
