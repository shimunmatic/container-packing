package hr.fer.shimun.packing.function;

import hr.fer.shimun.packing.function.grader.CollectiveGrader;
import hr.fer.shimun.packing.function.grader.FunctionGrader;
import hr.fer.shimun.packing.implementation.model.ContainerHolder;
import hr.fer.shimun.packing.model.Point;
import hr.fer.shimun.packing.util.Vector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HeuristicFunctionImpl implements HeuristicFunction {
    private FunctionGrader functionGrader;

    @Autowired
    public HeuristicFunctionImpl(@CollectiveGrader FunctionGrader functionGrader) {
        this.functionGrader = functionGrader;
    }

    @Override
    public Map<Point, Double> gradePoints(Vector<Integer, Integer, Integer> object, List<Point> points,
                                          ContainerHolder environment) {
        Map<Point, Double> gradedPoints = new HashMap<>();

        points.parallelStream().forEach(p -> gradedPoints.put(p, functionGrader.grade(object, p, environment)));

        return gradedPoints;
    }
}
