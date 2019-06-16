package hr.fer.shimun.packing.function.grader;

import hr.fer.shimun.packing.implementation.model.ContainerHolder;
import hr.fer.shimun.packing.model.Point;
import hr.fer.shimun.packing.util.Vector;

public interface FunctionGrader {

    Double grade(Vector<Integer, Integer, Integer> object, Point point, ContainerHolder environment);

    Double getCoefficient();
}
