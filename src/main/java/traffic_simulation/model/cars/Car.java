package traffic_simulation.model.cars;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import traffic_simulation.model.Point;
import traffic_simulation.model.street_network.Street;

@AllArgsConstructor
public class Car {

    private final Street currentStreet;
    private final double velocity;
    private Point location;


    public void drive() {

        Point direction = currentStreet.getDirection();
        double streetLength = currentStreet.getLength();



    }
}
