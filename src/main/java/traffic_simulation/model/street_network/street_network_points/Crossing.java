package traffic_simulation.model.street_network.street_network_points;

import lombok.RequiredArgsConstructor;
import traffic_simulation.model.Point;
import traffic_simulation.model.street_network.GridPoint;
import traffic_simulation.model.street_network.Street;

import java.util.HashMap;
import java.util.Map;


public class Crossing extends GridPoint {

    private final Map<Street, Double> streets;

    public Crossing(double x, double y) {
        super(new Point(x, y));
        this.streets = new HashMap<>();
    }

    public void addStreetToMap(Street street, double probability) {
        streets.put(street, probability);
    }
}
