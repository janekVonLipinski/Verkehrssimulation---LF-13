package traffic_simulation.model.street_network;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import traffic_simulation.model.Point;

@RequiredArgsConstructor
@Getter
public class Street {

    private final GridPoint firstPoint;
    private final GridPoint secondPoint;
    private final double length;
    private final Point direction; // should be normalized

}
