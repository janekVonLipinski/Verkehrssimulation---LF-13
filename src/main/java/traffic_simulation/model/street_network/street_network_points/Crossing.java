package traffic_simulation.model.street_network.street_network_points;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import traffic_simulation.model.Point;
import traffic_simulation.model.street_network.GridPoint;
import traffic_simulation.model.street_network.Street;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class Crossing extends GridPoint {
    @Getter
    private final Map<Street, Double> streets;
    private final Random random = new Random();

    public Crossing(String name, double x, double y) {
        super(new Point(x, y),name);
        this.streets = new HashMap<>();
    }

    public void addStreetToMap(Street street, double probability) {
        streets.put(street, probability);
    }

    public Street getNextStreet(Street currentStreet) {

        var possibleStreets = streets.keySet().stream()
                .filter(street -> street != currentStreet)
                .toList();


        //TODO implement correct choosing logic, not just a random street
        int randomStreetIndex = possibleStreets.size() == 1 ? 0 : random.nextInt(0, possibleStreets.size() - 1);
        return possibleStreets.get(randomStreetIndex);
    }

    @Override
    public ArrayList<GridPoint> getNeighbours() {
        ArrayList<GridPoint> neighbours = new ArrayList<>();
        for (Street street : this.streets.keySet()) {
            neighbours.add(street.getOtherPoint(this));
        }
        return neighbours;
    }
}
