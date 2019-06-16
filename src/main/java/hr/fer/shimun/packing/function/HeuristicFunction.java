package hr.fer.shimun.packing.function;

import hr.fer.shimun.packing.implementation.model.ContainerHolder;
import hr.fer.shimun.packing.model.Point;
import hr.fer.shimun.packing.util.Vector;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Map;

public interface HeuristicFunction {

    @NonNull
    Map<Point, Double> gradePoints(Vector<Integer, Integer, Integer> object, List<Point> points,
                                   ContainerHolder environment);

}
