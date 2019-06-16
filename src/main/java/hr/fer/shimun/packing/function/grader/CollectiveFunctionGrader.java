package hr.fer.shimun.packing.function.grader;

import hr.fer.shimun.packing.implementation.model.ContainerHolder;
import hr.fer.shimun.packing.model.Point;
import hr.fer.shimun.packing.util.Vector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@CollectiveGrader
public class CollectiveFunctionGrader implements FunctionGrader {
    private List<FunctionGrader> functionGraders;
    private Double coefficient;

    public CollectiveFunctionGrader(@SingleGrader List<FunctionGrader> functionGraders,
                                    @Value("${heuristic.function.grader.coefficient.method.collective}") Double coefficient) {
        this.functionGraders = functionGraders;
        this.coefficient = coefficient;
    }

    @Override
    public Double grade(Vector<Integer, Integer, Integer> object, Point point, ContainerHolder environment) {
        double grade = 0.;

        for (FunctionGrader fg : functionGraders) {
            grade += fg.grade(object, point, environment) * fg.getCoefficient();
        }

        return grade;
    }

    @Override
    public Double getCoefficient() {
        return coefficient;
    }
}
