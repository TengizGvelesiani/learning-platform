package com.tengo.learningplatform.service;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.stream.Collectors;

import com.tengo.learningplatform.annotations.ReflectiveTask;
import com.tengo.learningplatform.interactions.Payment;
import com.tengo.learningplatform.materials.Course;
import com.tengo.learningplatform.users.Student;


public final class ReflectionShowcase {

    private ReflectionShowcase() {
    }

    public static void run(Student student, Course course, Payment payment) {
        System.out.println("\n--- Reflection Showcase ---");
        printTypeMetadata(PurchaseService.class);
        printTypeMetadata(Course.class);
        invokeAnnotatedPurchaseCheck(student, course, payment);
    }

    private static void printTypeMetadata(Class<?> type) {
        System.out.println("[Reflect] Type: " + type.getName());
        System.out.println("[Reflect] Modifiers: " + Modifier.toString(type.getModifiers()));
        ReflectiveTask typeTask = type.getAnnotation(ReflectiveTask.class);
        if (typeTask != null) {
            System.out.println("[Reflect] Annotation: " + typeTask.value());
        }
        Arrays.stream(type.getDeclaredFields())
                .forEach(ReflectionShowcase::printField);
        Arrays.stream(type.getDeclaredConstructors())
                .forEach(ReflectionShowcase::printConstructor);
        Arrays.stream(type.getDeclaredMethods())
                .forEach(ReflectionShowcase::printMethod);
    }

    private static void printField(Field field) {
        System.out.println("[Reflect] FIELD " + Modifier.toString(field.getModifiers())
                + " " + field.getType().getSimpleName()
                + " " + field.getName());
    }

    private static void printConstructor(Constructor<?> constructor) {
        String params = Arrays.stream(constructor.getParameterTypes())
                .map(Class::getSimpleName)
                .collect(Collectors.joining(", "));
        System.out.println("[Reflect] CTOR " + Modifier.toString(constructor.getModifiers())
                + " " + constructor.getName()
                + "(" + params + ")");
    }

    private static void printMethod(Method method) {
        String params = Arrays.stream(method.getParameterTypes())
                .map(Class::getSimpleName)
                .collect(Collectors.joining(", "));
        ReflectiveTask marker = method.getAnnotation(ReflectiveTask.class);
        String annotationLabel = marker == null ? "-" : marker.value();
        System.out.println("[Reflect] METHOD " + Modifier.toString(method.getModifiers())
                + " " + method.getReturnType().getSimpleName()
                + " " + method.getName()
                + "(" + params + ")"
                + " annotation=" + annotationLabel);
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
            System.out.println("[Reflect] Invoked " + methodToInvoke.getName() + " result: " + result);
            Method preview = type.getDeclaredMethod("previewPurchase", Student.class, Course.class, Payment.class);
            preview.invoke(service, student, course, payment);
        } catch (Exception e) {
            throw new IllegalStateException("Reflection showcase failed", e);
        }
    }
}
