package edu.curtin.app.classes;

import edu.curtin.app.interfaces.EstimationStrategy;
import java.util.Collections;
import java.util.List;

/*
  When selected as the Estimation Approach, it will get the median estimate from all
  the estimates providedl This is a part of the Strategy Pattern
 */

public class MedianEstimation implements EstimationStrategy {
    @Override
    public int estimate(List<Integer> estimates) {
        Collections.sort(estimates);
        int middle = estimates.size() / 2;
        if (estimates.size() % 2 == 1) {
            return estimates.get(middle);
        } else {
            return (estimates.get(middle - 1) + estimates.get(middle)) / 2;
        }
    }
}
