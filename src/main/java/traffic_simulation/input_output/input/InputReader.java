package traffic_simulation.input_output.input;

import lombok.Getter;
import traffic_simulation.model.Point;
import traffic_simulation.model.street_network.GridPoint;
import traffic_simulation.model.street_network.Street;
import traffic_simulation.model.street_network.street_network_points.*;
import java.io.*;
import java.util.*;
@Getter
public class InputReader {

    public record ParsingResult(int endtimeOfSimulation, int tickspeed, List<SpawnPoint> spwanPoints,  List<Street> streets) {}

    public ParsingResult readFile(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));

        String line;

        int endtimeOfSimulation = 0;
        int tickspeed = 0;

        // --- PHASE TRACKING ---
        boolean isspawnPoint = false;
        boolean isCrossing = false;

        List<GridPoint> gridPoints = new ArrayList<>();
        List<Street> streets = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            line = line.trim();

            // leere Zeilen überspringen
            if (line.isEmpty()) {
                continue;
            }

            // Zeilen mit Kommentaren überspringen
            if (line.startsWith("#")) {
                continue;
            }

            // Zeitraum wird eingelesen mit Zeispanne und Tickrate
            if (line.equalsIgnoreCase("Zeitraum:")) {
                isspawnPoint = false;
                isCrossing = false;
                line = br.readLine().trim();
                String[] parts = line.split(" ");
                endtimeOfSimulation = Integer.parseInt(parts[0]);
                tickspeed = Integer.parseInt(parts[1]);
                continue;
            }

            if (line.equalsIgnoreCase("Einfallspunkte:")) {
                isspawnPoint = true;
                isCrossing = false;
                continue;
            }

            if (line.equalsIgnoreCase("Kreuzungen:")) {
                isspawnPoint = false;
                isCrossing = true;
                continue;
            }

            // Einfallspunkte
            if (isspawnPoint) {
                String[] p = line.split(" ");
                String name = p[0];
                int x = Integer.parseInt(p[1]);
                int y = Integer.parseInt(p[2]);
                String target = p[3];
                int spawnTick = Integer.parseInt(p[4]);
                gridPoints.add(new SpawnPoint(new Point(x, y), spawnTick,null, name));
            }
            // Kreuzungen
            if (isCrossing) {
                String[] p = line.split(" ");

                String name = p[0];
                double x = Double.parseDouble(p[1]);
                double y = Double.parseDouble(p[2]);

                gridPoints.add(new Crossing(name, x, y));
            }
        }
        br.close();
        isCrossing = false;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));

        while ((line = bufferedReader.readLine()) != null) {
            line = line.trim();

            // leere Zeilen überspringen
            if (line.isEmpty()) {
                continue;
            }

            // Zeilen mit Kommentaren überspringen
            if (line.startsWith("#")) {
                continue;
            }
            if (line.equalsIgnoreCase("Kreuzungen:")) {
                isCrossing = true;
                continue;
            }
            // Kreuzungen
            if (isCrossing) {
                String[] parsed_line = line.split(" ");
                String name = parsed_line[0];

                var crossing = gridPoints.stream()
                        .filter(a -> name.equals(a.getName()))
                        .findFirst()
                        .orElse(null);

                if (crossing == null) {
                    continue;
                }

                // Paare: Straßen und die Wahrscheinlichkeit in diese abzubiegen
                for (int i = 3; i < parsed_line.length; i += 2) {
                    String targetName = parsed_line[i];
                    double probability = Double.parseDouble(parsed_line[i + 1]);

                    var target = gridPoints.stream()
                            .filter(a -> targetName.equals(a.getName()))
                            .findFirst()
                            .orElse(null);

                    if (target == null) {
                        continue;
                    }
                    Street street = new Street(crossing, target,crossing.getPoint().getDistantsToPoint(target.getPoint()));
                    streets.add(street);
                    Crossing casted_crossing = (Crossing) crossing;
                    casted_crossing.addStreetToMap(street, probability);
                }
            }
        }
        bufferedReader.close();

        var spawnPoints = gridPoints.stream()
                .filter(i -> i instanceof SpawnPoint)
                .map(i -> (SpawnPoint) i)
                .toList();

        for (SpawnPoint sp : spawnPoints) {
            Street matchingStreet = streets.stream()
                    .filter(s -> s.getFirstPoint() == sp || s.getSecondPoint() == sp)
                    .findFirst()
                    .orElse(null); if (matchingStreet != null) { sp.setStreet(matchingStreet); }

            if (matchingStreet != null) {
                sp.setStreet(matchingStreet);
            } else {
                matchingStreet = streets.stream()
                        .filter(s -> s.getSecondPoint().getPoint().equals(sp.getPoint()))
                        .findFirst()
                        .orElse(null);

                if (matchingStreet != null) {
                    sp.setStreet(matchingStreet);
                }
            }
        }
        return new ParsingResult(endtimeOfSimulation, tickspeed, spawnPoints,streets);
    }
}


