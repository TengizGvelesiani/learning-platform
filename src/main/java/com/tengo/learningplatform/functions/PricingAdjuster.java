package com.tengo.learningplatform.functions;

import java.math.BigDecimal;

import com.tengo.learningplatform.materials.Course;


@FunctionalInterface
public interface PricingAdjuster {

    BigDecimal adjust(BigDecimal basePrice, Course course);
}
