package traffic_simulation.model.street_network;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import traffic_simulation.model.Point;

@RequiredArgsConstructor
@Getter
public class Street {

    private final GridPoint firstPoint;
    private final GridPoint secondPoint;
    private final double length;
    private int totalCarCounter = 0;
    private int maximumCarCounter = 0;

    public GridPoint getOtherPoint(GridPoint point) {

        if (point == this.firstPoint) {
            return this.secondPoint;
        }

        return this.firstPoint;
    }

    public int increaseTotalCarCounter() {
        totalCarCounter += 1;
        return totalCarCounter;
    }

    public int increaseMaximumCarCounter() {
        maximumCarCounter += 1;
        return maximumCarCounter;
    }

    public int decreaseMaximumCarCounter() {
        maximumCarCounter -= 1;
        return maximumCarCounter;
    }

    public double getMaximumCarsOnStreetPerOnehundredMeters() {
        Point subtractedPoint = firstPoint.getPoint().subtract(secondPoint.getPoint());

        double streetLength = subtractedPoint.getLength();

        double maximumCarsPerOnehundredMeter = maximumCarCounter / streetLength;

        return maximumCarsPerOnehundredMeter;
    }

    public double getTotalCarsOnStreetPerOnehundredMeters() {
        Point subtractedPoint = firstPoint.getPoint().subtract(secondPoint.getPoint());

        double streetLength = subtractedPoint.getLength();

        double totalCarsPerOnehundredMeter = totalCarCounter / streetLength;

        return totalCarsPerOnehundredMeter;
    }
}
