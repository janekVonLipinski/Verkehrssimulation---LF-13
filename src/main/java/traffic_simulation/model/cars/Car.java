package traffic_simulation.model.cars;

import lombok.Getter;
import traffic_simulation.model.Point;
import traffic_simulation.model.street_network.GridPoint;
import traffic_simulation.model.street_network.Street;
import traffic_simulation.model.street_network.street_network_points.Crossing;
import traffic_simulation.model.street_network.street_network_points.SpawnPoint;

import java.util.HashMap;
import java.util.Map;

public class Car {

    @Getter
    private final int id;
    private final double velocity;
    private GridPoint destination;
    @Getter
    private Street currentStreet;
    @Getter
    private Point location;
    @Getter
    private final Map<Integer, Map<Point, Point>> positions = new HashMap<>();

    private static int last_id = 0;
    private static final double CONVERSION_FACTOR_KM_PER_H_T_M_PER_S = 3.6;
    private static final Car CAR_WILL_BE_DELETED = null;

    public Car(double carVelocityInKmPerH, Street currentStreet, Point location, GridPoint destination) {
        this.id = last_id;
        this.velocity = carVelocityInKmPerH / (CONVERSION_FACTOR_KM_PER_H_T_M_PER_S * 100);
        this.currentStreet = currentStreet;
        this.location = location;
        this.destination = destination;
        this.currentStreet.increaseMaximumCarCounter();
        this.currentStreet.increaseTotalCarCounter();
        last_id++;
    }

    public Car drive(int tick) {
        double distanceToDrive = velocity;
        //1 Tick = 1s => a car can drive a distance corresponding to its velocity

        while (true) {

            //TODO in which Point am I. Must be possible to move in opposite direction
            Point nextPosition = calculateNextPosition(distanceToDrive);

            boolean hasReachedStreet = hasReachedEndOfStreet(nextPosition, destination.getPoint());

            if (!hasReachedStreet) {
                location = nextPosition;
                updatePositionLog(tick);
                return this;
            }

            GridPoint nextGridPoint = destination;
            
            if (nextGridPoint instanceof SpawnPoint) {
                return CAR_WILL_BE_DELETED;
            }

            handleCrossing(nextGridPoint);

            distanceToDrive = calculateRemainingDistance(nextGridPoint.getPoint(), distanceToDrive);
        }
    }

    private void handleCrossing(GridPoint nextGridPoint) {

        Point reachedGridPointLocation = nextGridPoint.getPoint();
        Crossing crossing = (Crossing) nextGridPoint;

        Street oldStreet = currentStreet;
        currentStreet = crossing.getNextStreet(currentStreet);
        updateStreetCounter(currentStreet, oldStreet);

        GridPoint destination = currentStreet.getOtherPoint(crossing);

        location = reachedGridPointLocation;
        this.destination = destination;
    }

    private void updateStreetCounter(Street currentStreet, Street oldStreet) {
        oldStreet.decreaseMaximumCarCounter();
        currentStreet.increaseTotalCarCounter();
        currentStreet.increaseMaximumCarCounter();
    }

    private Point calculateNextPosition(double distanceToDrive) {
        GridPoint otherPoint = currentStreet.getOtherPoint(destination);
        Point destinationPoint = this.destination.getPoint();
        Point direction = destinationPoint.subtract(otherPoint.getPoint());
        Point normalizedDirection = direction.normalize();

        Point directionWithVelocity = normalizedDirection.multiply(distanceToDrive); // unitHandling is handled during Car creation
        Point nextPoint = location.add(directionWithVelocity);
        return nextPoint;
    }

    private double calculateRemainingDistance(Point nextGridPointLocation, double distanceToDrive) {
        double drivenDistance = location.subtract(nextGridPointLocation).getLength();
        distanceToDrive -= drivenDistance;
        return distanceToDrive;
    }

    private boolean hasReachedEndOfStreet(Point nextPoint, Point destinationPoint) {
        double xOrientation = destinationPoint.getX();
        double yOrientation = destinationPoint.getY();

        boolean isXOrientationGreaterZero = xOrientation > 0;
        boolean isYOrientationGreaterZero = yOrientation > 0;

        boolean hasOverShotInXDirection = nextPoint.getX() > destinationPoint.getX();
        boolean hasOverShotInYDirection = nextPoint.getY() > destinationPoint.getY();

        boolean hasUnderShotInXDirection = nextPoint.getX() < destinationPoint.getX();
        boolean hasUnderShotInYDirection = nextPoint.getY() < destinationPoint.getY();


        if (isXOrientationGreaterZero && isYOrientationGreaterZero) {
            return hasOverShotInXDirection || hasOverShotInYDirection;
        }

        if (isXOrientationGreaterZero) {
            return hasOverShotInXDirection || hasOverShotInYDirection;
        }

        if (isYOrientationGreaterZero) {
            return hasUnderShotInXDirection || hasOverShotInYDirection;
        }

        return hasUnderShotInYDirection || hasUnderShotInXDirection;
    }

    private void updatePositionLog(int tick) {
        var pointMap = positions.computeIfAbsent(tick, k -> new HashMap<>());
        pointMap.put(location, destination.getPoint());
    }
}
