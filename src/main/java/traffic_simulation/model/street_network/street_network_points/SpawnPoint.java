package traffic_simulation.model.street_network.street_network_points;

import traffic_simulation.cars.Car;
import traffic_simulation.model.street_network.GridPoint;
import traffic_simulation.model.street_network.Street;

import java.util.Random;


public class SpawnPoint extends GridPoint {

    private final double spawnTick;
    private final Random random = new Random();
    private final Street street;
    private int currentTick = 0;

    public SpawnPoint(double x, double y, double spawnTick, Street street) {
        super(x, y);
        this.spawnTick = spawnTick;
        this.street = street;
    }

    public Car spawnCar() {
        currentTick++;
        boolean shouldSpawnCarInThisTick = currentTick % spawnTick == 0;
        // right now is spawns cars at t = 0

        if (!shouldSpawnCarInThisTick) {
            return null;
        }

        return new Car(street);
    }

}
