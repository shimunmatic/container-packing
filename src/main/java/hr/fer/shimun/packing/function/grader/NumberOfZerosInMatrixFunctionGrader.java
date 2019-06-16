package hr.fer.shimun.packing.function.grader;

import hr.fer.shimun.packing.implementation.model.ContainerHolder;
import hr.fer.shimun.packing.model.Point;
import hr.fer.shimun.packing.util.Vector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Checks and returns how many empty spaces will be occupied by this change
 */
@Component
@SingleGrader
public class NumberOfZerosInMatrixFunctionGrader implements FunctionGrader {
    private Double coefficient;

    public NumberOfZerosInMatrixFunctionGrader(
            @Value("${heuristic.function.grader.coefficient.method.zeros}") Double coefficient) {
        this.coefficient = coefficient;
    }

    @Override
    public Double grade(Vector<Integer, Integer, Integer> object, Point point, ContainerHolder environment) {
        double grade;
        int[][] matrix = environment.getMatrix();
        long count = 0;
        for (int i = point.getPositionX(); i < point.getPositionX() + object.getX(); i++) {
            for (int j = point.getPositionY(); j < point.getPositionY() + object.getY(); j++) {
                if (matrix[i][j] == 0) { count++; }
            }
        }
        int surface = object.getY() * object.getX();

        grade = (count - 0.) / (surface - 0.) * (1 - -1) + -1;

        return grade;
    }

    @Override
    public Double getCoefficient() {
        return coefficient;
    }
}
