package hr.fer.shimun.packing.function.grader;

import hr.fer.shimun.packing.implementation.model.ContainerHolder;
import hr.fer.shimun.packing.model.Point;
import hr.fer.shimun.packing.util.Vector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Returns 1 if levels of heights will remain the same otherwise -1
 */
@Component
@SingleGrader
public class DifferentHeightsInMatrixFunctionGrader implements FunctionGrader {
    private Double coefficient;

    public DifferentHeightsInMatrixFunctionGrader(
            @Value("${heuristic.function.grader.coefficient.method.heights}") Double coefficient) {
        this.coefficient = coefficient;
    }

    @Override
    public Double grade(Vector<Integer, Integer, Integer> object, Point point, ContainerHolder environment) {
        Set<Integer> heights = environment.getUniqueHeights();

        int newHeight = point.getPositionZ() + object.getZ();

        return heights.contains(newHeight) ? 1. : -1.;
    }

    @Override
    public Double getCoefficient() {
        return coefficient;
    }
}
