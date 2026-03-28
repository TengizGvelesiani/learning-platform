package functions;

import materials.Material;

@FunctionalInterface
public interface LearningOutcomeReporter {

    void report(Material material, String outcomeDetail);
}
