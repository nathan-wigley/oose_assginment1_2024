package edu.curtin.app.classes;
import java.util.List;
import java.util.Collections;
import edu.curtin.app.interfaces.EstimationStrategy;

public class MaxEstimation implements EstimationStrategy {
    @Override
    public int estimate(List<Integer> estimates) {
        return Collections.max(estimates);
    }
}
