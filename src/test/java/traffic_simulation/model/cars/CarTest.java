package traffic_simulation.model.cars;

import org.junit.jupiter.api.Test;
import traffic_simulation.model.Point;
import traffic_simulation.model.street_network.GridPoint;
import traffic_simulation.model.street_network.Street;
import traffic_simulation.model.street_network.street_network_points.Crossing;
import traffic_simulation.model.street_network.street_network_points.SpawnPoint;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {

    @Test
    void given_car_drives_parallel_to_y_Axis_then_position_updatesCorrectly() {

        Point point = new Point(0, 0);
        Point point1 = new Point(0, 1);

        Point direction = point1.subtract(point);

        SpawnPoint spawnPoint = new SpawnPoint(point, 0, null);
        SpawnPoint spawnPoint1 = new SpawnPoint(point1, 0, null);

        Street street = new Street(spawnPoint, spawnPoint1, 1, direction);
        spawnPoint.setStreet(street);
        spawnPoint1.setStreet(street);

        Car sut = new Car(3.6, street, point);

        Car resultingCar = sut.drive();

        assertEquals(1, resultingCar.getLocation().getY());
    }


    @Test
    void given_car_drives_parallel_to_y_Axis_and_reaches_crossing_then_car_turns_correctly_and_drives_the_remainnig_distance() {

        Point point_start = new Point(0, 0);
        Point point_crossing = new Point(0, 1);
        Point point_end = new Point(0, 2);

        Point direction = point_crossing.subtract(point_start);

        SpawnPoint spawnPoint = new SpawnPoint(point_start, 0, null);
        SpawnPoint spawnPoint1 = new SpawnPoint(point_crossing, 0, null);

        Street street = new Street(spawnPoint, spawnPoint1, 1, direction);
        spawnPoint.setStreet(street);
        spawnPoint1.setStreet(street);

        Car sut = new Car(3.6, street, point_start);

        Car resultingCar = sut.drive();

        assertEquals(1, resultingCar.getLocation().getY());
    }

}