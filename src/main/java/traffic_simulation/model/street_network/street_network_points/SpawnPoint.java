package traffic_simulation.model.street_network.street_network_points;

import lombok.RequiredArgsConstructor;
import traffic_simulation.model.street_network.GridPoint;


public class SpawnPoint extends GridPoint {

    private final double spawnTick;

    public SpawnPoint(double x, double y, double spawnTick) {
        super(x, y);
        this.spawnTick = spawnTick;
    }
}
