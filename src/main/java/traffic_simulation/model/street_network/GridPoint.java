package traffic_simulation.model.street_network;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import traffic_simulation.model.Point;

import java.util.ArrayList;

@RequiredArgsConstructor
public abstract class GridPoint {

    @Getter
    private final Point point;

    public abstract ArrayList<GridPoint> getNeighbours();
}
