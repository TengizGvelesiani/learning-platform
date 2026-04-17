package com.tengo.learningplatform.service;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tengo.learningplatform.annotations.ReflectiveTask;
import com.tengo.learningplatform.interactions.Payment;
import com.tengo.learningplatform.materials.Course;
import com.tengo.learningplatform.users.Student;


public final class ReflectionShowcase {

    private static final Logger LOGGER = LogManager.getLogger(ReflectionShowcase.class);

    private ReflectionShowcase() {
    }

    public static void run(Student student, Course course, Payment payment) {
        LOGGER.info("--- Reflection Showcase ---");
        printTypeMetadata(PurchaseService.class);
        printTypeMetadata(Course.class);
        invokeAnnotatedPurchaseCheck(student, course, payment);
    }

    private static void printTypeMetadata(Class<?> type) {
        LOGGER.info("[Reflect] Type: {}", type.getName());
        LOGGER.info("[Reflect] Modifiers: {}", Modifier.toString(type.getModifiers()));
        ReflectiveTask typeTask = type.getAnnotation(ReflectiveTask.class);
        if (typeTask != null) {
            LOGGER.info("[Reflect] Annotation: {}", typeTask.value());
        }
        Arrays.stream(type.getDeclaredFields())
                .forEach(ReflectionShowcase::printField);
        Arrays.stream(type.getDeclaredConstructors())
                .forEach(ReflectionShowcase::printConstructor);
        Arrays.stream(type.getDeclaredMethods())
                .forEach(ReflectionShowcase::printMethod);
    }

    private static void printField(Field field) {
        LOGGER.info("[Reflect] FIELD {} {} {}", Modifier.toString(field.getModifiers()),
                field.getType().getSimpleName(), field.getName());
    }

    private static void printConstructor(Constructor<?> constructor) {
        String params = Arrays.stream(constructor.getParameterTypes())
                .map(Class::getSimpleName)
                .collect(Collectors.joining(", "));
        LOGGER.info("[Reflect] CTOR {} {}({})", Modifier.toString(constructor.getModifiers()),
                constructor.getName(), params);
    }

    private static void printMethod(Method method) {
        String params = Arrays.stream(method.getParameterTypes())
                .map(Class::getSimpleName)
                .collect(Collectors.joining(", "));
        ReflectiveTask marker = method.getAnnotation(ReflectiveTask.class);
        String annotationLabel = marker == null ? "-" : marker.value();
        LOGGER.info("[Reflect] METHOD {} {} {}({}) annotation={}",
                Modifier.toString(method.getModifiers()),
                method.getReturnType().getSimpleName(),
                method.getName(),
                params,
                annotationLabel);
    }

    private static void invokeAnnotatedPurchaseCheck(Student student, Course course, Payment payment) {
        try {
            Class<?> type = Class.forName(PurchaseService.class.getName());
            Constructor<?> ctor = type.getDeclaredConstructor();
            Object service = ctor.newInstance();
            Method methodToInvoke = Arrays.stream(type.getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(ReflectiveTask.class))
                    .filter(method -> method.getAnnotation(ReflectiveTask.class).invoke())
                    .findFirst()
                    .orElseThrow();
            Object result = methodToInvoke.invoke(service, payment.getAmount(), course.getPrice());
            LOGGER.info("[Reflect] Invoked {} result: {}", methodToInvoke.getName(), result);
            Method preview = type.getDeclaredMethod("previewPurchase", Student.class, Course.class, Payment.class);
            preview.invoke(service, student, course, payment);
        } catch (Exception e) {
            throw new IllegalStateException("Reflection showcase failed", e);
        }
    }
}
