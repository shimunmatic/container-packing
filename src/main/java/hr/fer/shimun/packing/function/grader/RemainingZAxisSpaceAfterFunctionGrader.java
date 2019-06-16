package hr.fer.shimun.packing.function.grader;

import hr.fer.shimun.packing.implementation.model.ContainerHolder;
import hr.fer.shimun.packing.model.Point;
import hr.fer.shimun.packing.util.Vector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@SingleGrader
public class RemainingZAxisSpaceAfterFunctionGrader implements FunctionGrader {
    private Double coefficient;

    public RemainingZAxisSpaceAfterFunctionGrader(
            @Value("${heuristic.function.grader.coefficient.method.axis.y}") Double coefficient) {
        this.coefficient = coefficient;
    }

    @Override
    public Double grade(Vector<Integer, Integer, Integer> object, Point point, ContainerHolder environment) {
        int spaceLeftOnZAxis = environment.getHeight() - point.getPositionZ() - object.getZ();
        return spaceLeftOnZAxis < environment.getMinimumEdge() ? -1. : 1;
    }

    @Override
    public Double getCoefficient() {
        return coefficient;
    }
}
