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
    private final Point direction; // should be normalized

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

    //Dumme blöde funktion, aber ich habe die Sorge, dass folgendes Problem auftritt:
    //Ich möchte den Nachbar eines Punktes haben, frage die Straße auf die der Punkt liegt ->
    //Nun weiß ich nicht, ob Punkt auf Straße street.firstPoint oder street.secondPoint ist
    //Es könnte also passieren wenn ich (der logik nach) davon ausgehe, das Punkt mein street.firstPoint ist
    //und ich street.secondPoint als Nachbar deklariere, in Wahrheit gar nicht der Nachbar zurückgegeben wird
    public GridPoint getOtherPoint(GridPoint point) {
        if (point == this.firstPoint) {
            return this.secondPoint;
        } else {
            return this.secondPoint;
        }
    }
}
