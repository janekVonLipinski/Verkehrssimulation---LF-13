package traffic_simulation.simulation;

import lombok.RequiredArgsConstructor;
import traffic_simulation.model.cars.Car;
import traffic_simulation.model.street_network.street_network_points.SpawnPoint;

import java.util.List;

@RequiredArgsConstructor
public class Simulation {


    private final List<SpawnPoint> spawnPoints;
    private final List<Car> cars;

    public void simulate() {

        while (true) {
            spawnCars();
            simulateCars();
        }

    }

    private void spawnCars() {
        for (SpawnPoint spawnPoint : spawnPoints) {
            Car car = spawnPoint.spawnCar();

            if (car != null) {
                cars.add(car);
            }
        }
    }

    private void simulateCars() {

    }
}
