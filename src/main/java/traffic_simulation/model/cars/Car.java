package traffic_simulation.model.cars;

import lombok.Getter;
import traffic_simulation.model.Point;
import traffic_simulation.model.street_network.GridPoint;
import traffic_simulation.model.street_network.Street;
import traffic_simulation.model.street_network.street_network_points.Crossing;
import traffic_simulation.model.street_network.street_network_points.SpawnPoint;

public class Car {
    
    private final double velocity;
    private GridPoint destination;
    @Getter
    private Street currentStreet;
    @Getter
    private Point location;
    private static final double CONVERSION_FACTOR_KM_PER_H_T_M_PER_S = 3.6;

    public Car(double carVelocityInKmPerH, Street currentStreet, Point location, GridPoint destination) {
        this.velocity = carVelocityInKmPerH / (CONVERSION_FACTOR_KM_PER_H_T_M_PER_S * 100);
        this.currentStreet = currentStreet;
        this.location = location;
        this.destination = destination;
    }

    public Car drive() {
        double distanceToDrive = velocity;
        //1 Tick = 1s => a car can drive a distance corresponding to its velocity
        boolean canDrive = true;
        
        while (canDrive) {

            System.out.println(location);
            //TODO in which Point am I. Must be possible to move in opposite direction
            Point direction = this.destination.getPoint().normalize();

            Point directionWithVelocity = direction.multiply(distanceToDrive); // unitHandling is handled during Car creation
            Point nextPoint = location.add(directionWithVelocity);

            boolean hasReachedStreet = hasReachedEndOfStreet(nextPoint, direction);

            if (!hasReachedStreet) {
                location = nextPoint;
                return this;
            }

            GridPoint nextGridPoint = destination;

            Point nextGridPointLocation = nextGridPoint.getPoint();
            
            if (nextGridPoint instanceof SpawnPoint) {
                return null;
            }
            
            Crossing crossing = (Crossing) nextGridPoint;
            currentStreet = crossing.getNextStreet(currentStreet);
            GridPoint destination = currentStreet.getOtherPoint(crossing);

            distanceToDrive = calculateRemainingDistance(nextGridPointLocation, distanceToDrive);
            location = nextGridPointLocation;
            this.destination = destination;
            canDrive = distanceToDrive > 0;
        }
        
        return this;
    }

    private double calculateRemainingDistance(Point nextGridPointLocation, double distanceToDrive) {
        double drivenDistance = location.subtract(nextGridPointLocation).getLength();
        distanceToDrive -= drivenDistance;
        return distanceToDrive;
    }

    private boolean hasReachedEndOfStreet(Point nextPoint, Point destinationPoint) {
        //TODO is it possible to make this method less ugly
        double xOrientation = currentStreet.getDirection().getX();
        double yOrientation = currentStreet.getDirection().getY();

        boolean isXOrientationGreaterZero = xOrientation > 0;
        boolean isYOrientationGreaterZero = yOrientation > 0;

        boolean hasOverShotInXDirection = nextPoint.getX() > destinationPoint.getX();
        boolean hasOverShotInYDirection = nextPoint.getY() > destinationPoint.getY();

        boolean hasUnderShotInXDirection = nextPoint.getX() < destinationPoint.getX();
        boolean hasUnderShotInYDirection = nextPoint.getY() < destinationPoint.getY();


        //TODO this is wrong, if we move parallel to axis
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
}
