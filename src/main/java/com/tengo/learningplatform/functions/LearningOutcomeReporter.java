package com.tengo.learningplatform.functions;

import com.tengo.learningplatform.materials.Material;


@FunctionalInterface
public interface LearningOutcomeReporter {

    void report(Material material, String outcomeDetail);
}
