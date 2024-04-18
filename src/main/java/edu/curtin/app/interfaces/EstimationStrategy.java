package edu.curtin.app.interfaces;
import java.util.*;

/*
  Interface for the estimation strategy selected by the configuration.
  This is a part of the implementation of the strategy pattern
 */

public interface EstimationStrategy {
    int estimate(List<Integer> estimates);
}
