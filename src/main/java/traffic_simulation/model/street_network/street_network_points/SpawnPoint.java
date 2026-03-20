package traffic_simulation.model.street_network.street_network_points;


import lombok.Setter;
import lombok.Getter;
import traffic_simulation.model.Point;
import traffic_simulation.model.cars.Car;
import traffic_simulation.model.street_network.GridPoint;
import traffic_simulation.model.street_network.Street;

import java.util.Random;


public class SpawnPoint extends GridPoint {
    @Getter
    private final String name;
    private final double spawnTick;
    private final Random random = new Random();
    @Getter
    @Setter
    private Street street;


    //TODO rethink this, both street and Point now each other which is inconvenient
    private static final int EXPECTED_VALUE = 45;
    private static final int DEVIATION = 10;


    public SpawnPoint(Point point, double spawnTick, Street street, String name) {
        super(point,null);
        this.spawnTick = spawnTick;
        this.street = street;
        this.name = name;
    }

    public Car spawnCar(int currentTick) {

        boolean shouldSpawnCarInThisTick = currentTick % spawnTick == 0;
        // right now it spawns cars at t = 0

        double carVelocityInKmPerH = random.nextGaussian(EXPECTED_VALUE, DEVIATION);

        GridPoint destination = street.getOtherPoint(this);

        if (!shouldSpawnCarInThisTick) {
            return null;
        }
        return new Car(carVelocityInKmPerH, street, getPoint(), destination) ;
    }

}
