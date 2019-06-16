package hr.fer.shimun.packing.function.grader;

import hr.fer.shimun.packing.implementation.model.ContainerHolder;
import hr.fer.shimun.packing.model.Point;
import hr.fer.shimun.packing.util.Vector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@SingleGrader
public class RemainingYAxisSpaceAfterFunctionGrader implements FunctionGrader {
    private Double coefficient;

    public RemainingYAxisSpaceAfterFunctionGrader(
            @Value("${heuristic.function.grader.coefficient.method.axis.y}") Double coefficient) {
        this.coefficient = coefficient;
    }

    @Override
    public Double grade(Vector<Integer, Integer, Integer> object, Point point, ContainerHolder environment) {
        int spaceLeftOnYAxis = environment.getLength() - point.getPositionY() - object.getY();
        return spaceLeftOnYAxis < environment.getMinimumEdge() ? -1. : 1;
    }

    @Override
    public Double getCoefficient() {
        return coefficient;
    }
}
