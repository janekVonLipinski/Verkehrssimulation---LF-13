package traffic_simulation.model.cars;

import lombok.AllArgsConstructor;
import traffic_simulation.model.Point;
import traffic_simulation.model.street_network.GridPoint;
import traffic_simulation.model.street_network.Street;
import traffic_simulation.model.street_network.street_network_points.Crossing;
import traffic_simulation.model.street_network.street_network_points.SpawnPoint;

public class Car {
    
    private final double velocity;
    private Street currentStreet;
    private Point location;
    private static final double CONVERSION_FACTOR_KM_PER_H_T_M_PER_S = 3.6;


    public Car(double carVelocityInKmPerH, Street currentStreet, Point location) {
        this.velocity = carVelocityInKmPerH / CONVERSION_FACTOR_KM_PER_H_T_M_PER_S;;
        this.currentStreet = currentStreet;
        this.location = location;
    }

    public Car drive() {
        double distanceToDrive = velocity; 
        //1 Tick = 1s => a car can drive a distance corresponding to its velocity
        boolean canDrive = true;
        
        while (canDrive) {
            Point direction = currentStreet.getDirection();
            double streetLength = currentStreet.getLength();

            Point directionWithVelocity = direction.multiply(velocity); // unitHandling is handled during Car creation
            Point nextPoint = location.add(directionWithVelocity);

            boolean hasReachedEndOfStreet = hasReachedEndOfStreet();

            if (!hasReachedEndOfStreet) {
                location = nextPoint;
                return this;
            }

            GridPoint nextGridPoint = getCurrentStreetEndpoint();
            Point nextGridPointLocation = nextGridPoint.getPoint();
            
            if (nextGridPoint instanceof SpawnPoint) {
                return null;
            }
            
            Crossing crossing = (Crossing) nextGridPoint;
            currentStreet = crossing.getNextStreet();

            distanceToDrive = calculateRemainingDistance(nextGridPointLocation, distanceToDrive);
            canDrive = distanceToDrive > 0;
        }
        
        return this;
    }

    private double calculateRemainingDistance(Point nextGridPointLocation, double distanceToDrive) {
        double drivenDistance = location.subtract(nextGridPointLocation).getLength();
        distanceToDrive -= drivenDistance;
        return distanceToDrive;
    }

    private boolean hasReachedEndOfStreet() {
        return true;
    }
    
    private GridPoint getCurrentStreetEndpoint() {
        //TODO implement
        return currentStreet.getSecondPoint();
    }
}
