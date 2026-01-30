package traffic_simulation.model.street_network.street_network_points;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import traffic_simulation.model.Point;
import traffic_simulation.model.street_network.GridPoint;


public class SpawnPoint extends GridPoint {
    @Getter
    private final double spawnTick;

    public SpawnPoint(double x, double y, double spawnTick) {
        super(new Point(x, y));
        this.spawnTick = spawnTick;
    }
}
