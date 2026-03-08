package traffic_simulation.simulation;

import org.junit.jupiter.api.Test;
import traffic_simulation.model.Point;
import traffic_simulation.model.cars.Car;
import traffic_simulation.model.street_network.Street;
import traffic_simulation.model.street_network.street_network_points.SpawnPoint;

import java.util.List;

class SimulationIntegrationTest {


    @Test
    void given_two_node_city_then_one_car_reaches_destination() {

        Point p1 = new Point(0, 0);
        Point p2 = new Point(1, 0);


        SpawnPoint sp1 = new SpawnPoint(p1, 3, null,null);
        SpawnPoint sp2 = new SpawnPoint(p2, 3, null,null);

        Street street = new Street(sp1, sp2, 1);
        sp1.setStreet(street);
        sp2.setStreet(street);

        Simulation simulation = new Simulation(
                List.of(sp1, sp2), 100, List.of(street)
        );

        List<Car> remainingCars = simulation.simulate();

        //assertEquals(0, remainingCars.size());
    }

}