package traffic_simulation.model.street_network.street_network_points;

import lombok.RequiredArgsConstructor;
import traffic_simulation.model.street_network.GridPoint;
import traffic_simulation.model.street_network.Street;

import java.util.Map;


public class Crossing extends GridPoint {

    private final Map<Street, Double> streets;

    public Crossing(double x, double y, Map<Street, Double> streets) {
        super(x, y);
        this.streets = streets;
    }
}
