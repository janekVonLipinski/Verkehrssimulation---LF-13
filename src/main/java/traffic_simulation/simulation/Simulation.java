package traffic_simulation.simulation;

import lombok.RequiredArgsConstructor;
import traffic_simulation.cars.Car;
import traffic_simulation.model.street_network.street_network_points.SpawnPoint;

import java.util.List;

@RequiredArgsConstructor
public class Simulation {


    private final SpawnPoint spawnPoint;
    private final List<Car> cars;
}
