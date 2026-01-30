package traffic_simulation.model.street_network;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import traffic_simulation.model.Point;

@RequiredArgsConstructor
@Getter
public abstract class GridPoint {

    private final Point point;

    private final String name;
}
