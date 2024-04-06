package edu.curtin.app.classes;

public class Estimates {
    
    private final int maxEstimate;
    private final double medianEstimate;

    public Estimates(int maxEstimate, double medianEstimate) {
        this.maxEstimate = maxEstimate;
        this.medianEstimate = medianEstimate;
    }

    public int getMaxEstimate() {
        return maxEstimate;
    }

    public double getMedianEstimate() {
        return medianEstimate;
    }
    
}
