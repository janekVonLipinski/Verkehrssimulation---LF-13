package traffic_simulation.model.street_network.street_network_points;


import traffic_simulation.model.Point;
import traffic_simulation.model.cars.Car;
import traffic_simulation.model.street_network.GridPoint;
import traffic_simulation.model.street_network.Street;

import java.util.Random;


public class SpawnPoint extends GridPoint {

    private final double spawnTick;
    private final Random random = new Random();
    private final Street street;
    private static final int EXPECTED_VALUE = 45;
    private static final int DEVIATION = 10;
    private int currentTick = 0;


    public SpawnPoint(Point point, double spawnTick, Street street) {
        super(point);
        this.spawnTick = spawnTick;
        this.street = street;
    }

    public Car spawnCar() {
        currentTick++;
        boolean shouldSpawnCarInThisTick = currentTick % spawnTick == 0;
        // right now is spawns cars at t = 0

        double carVelocityInKmPerH = random.nextGaussian(EXPECTED_VALUE, DEVIATION);

        if (!shouldSpawnCarInThisTick) {
            return null;
        }

        return new Car(carVelocityInKmPerH, street, getPoint()) ;
    }

}
