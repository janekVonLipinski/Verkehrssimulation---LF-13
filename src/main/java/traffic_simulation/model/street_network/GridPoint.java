package traffic_simulation.model.street_network;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import traffic_simulation.model.Point;

@RequiredArgsConstructor
public abstract class GridPoint {

    @Getter
    private final Point point;
}
