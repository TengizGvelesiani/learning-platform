package functions;

import java.math.BigDecimal;

import materials.Course;

@FunctionalInterface
public interface PricingAdjuster {

    BigDecimal adjust(BigDecimal basePrice, Course course);
}
