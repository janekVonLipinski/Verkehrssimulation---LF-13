package traffic_simulation.model.street_network.street_network_points;

import lombok.Getter;
import traffic_simulation.model.Point;
import traffic_simulation.model.street_network.GridPoint;
import traffic_simulation.model.street_network.Street;

import java.util.*;


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

        var sumOfPercentages = possibleStreets.stream()
                .map(streets::get)
                .mapToDouble(s -> s)
                .sum();

        var randomNumber = random.nextInt(1, (int) sumOfPercentages);

        for (Street street : possibleStreets) {
            int percentage = (int) (double) streets.get(street);

            randomNumber -= percentage;

            if (randomNumber <= 0) {
                return street;
            }
        }

        throw new RuntimeException("could not determine next street in crossing");
    }

    @Override
    public String toString() {
        return "Crossing{" +
                "streets=" + streets +
                ", random=" + random +
                '}';
    }
}
