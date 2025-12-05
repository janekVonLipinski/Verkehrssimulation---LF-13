package traffic_simulation.cars;

import lombok.RequiredArgsConstructor;
import traffic_simulation.model.street_network.Street;

@RequiredArgsConstructor
public class Car {

    private final Street currentStreet;
}
