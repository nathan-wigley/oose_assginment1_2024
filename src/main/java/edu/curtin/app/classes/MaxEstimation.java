package edu.curtin.app.classes;
import java.util.List;
import java.util.Collections;
import edu.curtin.app.interfaces.EstimationStrategy;

/*
  When selected as the Estimation Approach, it will get the maximum estimate from all
  the estimates provided. This is a part of the Strategy Pattern
 */

public class MaxEstimation implements EstimationStrategy {
    @Override
    public int estimate(List<Integer> estimates) {
        return Collections.max(estimates);
    }
}
