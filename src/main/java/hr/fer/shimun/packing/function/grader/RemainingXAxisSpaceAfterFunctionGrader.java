package hr.fer.shimun.packing.function.grader;

import hr.fer.shimun.packing.implementation.model.ContainerHolder;
import hr.fer.shimun.packing.model.Point;
import hr.fer.shimun.packing.util.Vector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@SingleGrader
public class RemainingXAxisSpaceAfterFunctionGrader implements FunctionGrader {
    private Double coefficient;

    public RemainingXAxisSpaceAfterFunctionGrader(
            @Value("${heuristic.function.grader.coefficient.method.axis.x}") Double coefficient) {
        this.coefficient = coefficient;
    }

    @Override
    public Double grade(Vector<Integer, Integer, Integer> object, Point point, ContainerHolder environment) {
        int spaceLeftOnXAxis = environment.getWidth() - point.getPositionX() - object.getX();
        return spaceLeftOnXAxis < environment.getMinimumEdge() ? -1. : 1;
    }

    @Override
    public Double getCoefficient() {
        return coefficient;
    }
}
