package traffic_simulation.model.street_network;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Street {

    private final GridPoint firstPoint;
    private final GridPoint secondPoint;
}
