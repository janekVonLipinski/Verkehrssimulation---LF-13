package traffic_simulation.model.cars;

import lombok.RequiredArgsConstructor;
import traffic_simulation.model.street_network.Street;

@RequiredArgsConstructor
public class Car {

    private final Street currentStreet;
    private final double velocity;


}
