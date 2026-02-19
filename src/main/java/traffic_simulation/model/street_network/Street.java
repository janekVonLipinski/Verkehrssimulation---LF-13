package traffic_simulation.model.street_network;

import lombok.RequiredArgsConstructor;
import traffic_simulation.model.Point;

@RequiredArgsConstructor
public class Street {

    private final GridPoint firstPoint;
    private final GridPoint secondPoint;
    private int carCounter = 0;

    public int increaseCarCounter() {
        carCounter += 1;

        return carCounter;
    }

    public double getCarCounter() {
        Point subtractedPoint = firstPoint.getPoint().subtract(secondPoint.getPoint());

        double streetLength = subtractedPoint.getLength();

        double carsPerOnehundredMeter = carCounter / streetLength;

        return carsPerOnehundredMeter;
    }
}
