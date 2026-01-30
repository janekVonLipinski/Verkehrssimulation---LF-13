package traffic_simulation.input_output.input;

import lombok.Getter;
import traffic_simulation.model.Point;
import traffic_simulation.model.street_network.Street;
import traffic_simulation.model.street_network.street_network_points.*;
import java.io.*;
import java.util.*;
@Getter
public class InputReader {
    private int endtimeOfSimulation;
    private int tickspeed;

    private List<SpawnPoint> spawnPoints = new ArrayList<>();
    private List<Crossing> crossings = new ArrayList<>();

    private record bla(List<SpawnPoint> spawnPoints, List<Crossing> crossings) {
    }

    public bla readFile(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;

        // --- PHASE TRACKING ---
        boolean isspawnPoint = false;
        boolean iscrossing = false;

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
                iscrossing = false;
                line = br.readLine().trim();
                String[] parts = line.split(" ");
                endtimeOfSimulation = Integer.parseInt(parts[0]);
                tickspeed = Integer.parseInt(parts[1]);
                continue;
            }

            if (line.equalsIgnoreCase("Einfallspunkte:")) {
                isspawnPoint = true;
                iscrossing = false;
                continue;
            }

            if (line.equalsIgnoreCase("Kreuzungen:")) {
                isspawnPoint = false;
                iscrossing = true;
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

                spawnPoints.add(new SpawnPoint(name, x, y, spawnTick));
            }

            // Kreuzungen
            if (iscrossing) {
                String[] p = line.split(" ");

                String name = p[0];
                double x = Double.parseDouble(p[1]);
                double y = Double.parseDouble(p[2]);
                Map<Street, Double> streets = new HashMap<>();
                Crossing c = new Crossing(name, x, y, streets);


                crossings.add(c);
            }
        }
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
            if (line.equalsIgnoreCase("Kreuzungen:")) {
                iscrossing = true;
                continue;
            }
            // Kreuzungen
            if (iscrossing) {
                String[] p = line.split(" ");

                String name = p[0];
                double x = Double.parseDouble(p[1]);
                double y = Double.parseDouble(p[2]);
                Map<Street, Double> streets = new HashMap<>();

                // Paare: Straßen und die Wahrscheinlichkeit in diese abzubiegen
                for (int i = 3; i < p.length; i += 2) {
                    String targetName = p[i];
                    for(SpawnPoint point: spawnPoints){
                        for (SpawnPoint point2: spawnPoints){
                        if(point.getName().equals(name)) {
                            if (point.getName().equals(targetName)) {
                                Street street = new Street(point, point2);
                            }
                        }
                    }
                    double probability = Double.parseDouble(p[i + 1]);
                }
            }
            br.close();
            return new bla(spawnPoints, crossings);
        }
    }
}


