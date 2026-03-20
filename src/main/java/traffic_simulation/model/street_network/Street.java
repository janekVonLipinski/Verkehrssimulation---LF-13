package traffic_simulation.model.street_network;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import traffic_simulation.model.Point;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Street street = (Street) o;
        return Double.compare(length, street.length) == 0 && totalCarCounter == street.totalCarCounter && maximumCarCounter == street.maximumCarCounter && Objects.equals(firstPoint, street.firstPoint) && Objects.equals(secondPoint, street.secondPoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstPoint, secondPoint, length, totalCarCounter, maximumCarCounter);
    }
}
