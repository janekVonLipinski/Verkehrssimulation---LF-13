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

    public record ParsingResult(int endtimeOfSimulation, int tickspeed, List<SpawnPoint> spawnPoints,  List<Street> streets) {}


    public ParsingResult readFile(String path){

        List<GridPoint> gridPoints = new ArrayList<>();
        List<Street> streets = new ArrayList<>();

        int endtimeOfSimulation = 0;
        int tickspeed = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            boolean isspawnPoint = false;

            while((line =br.readLine())!=null) {
                line = line.trim();

                // leere Zeilen überspringen
                if (line.isEmpty()) {
                    continue;
                }

                boolean isComment = line.startsWith("#");
                if (isComment) {
                    continue;
                }

                boolean isTimePeriod = line.equalsIgnoreCase("Zeitraum:");
                if (isTimePeriod) {
                    line = br.readLine().trim();
                    String[] parts = line.split(" ");
                    endtimeOfSimulation = Integer.parseInt(parts[0]);
                    tickspeed = Integer.parseInt(parts[1]);
                    continue;
                }

                boolean isSpawnPointSection = line.equalsIgnoreCase("Einfallspunkte:");
                if (isSpawnPointSection) {
                    isspawnPoint = true;
                    continue;
                }

                boolean isCrossingSection = line.equalsIgnoreCase("Kreuzungen:");
                if (isCrossingSection) {
                    isspawnPoint = false;
                    continue;
                }

                if (isspawnPoint) {
                    String[] p = line.split(" ");
                    String name = p[0];
                    double x = Double.parseDouble(p[1]);
                    double y = Double.parseDouble(p[2]);
                    //p[3] is das Ziel des Spawnpoints und wird nicht genutzt
                    int spawnTick = Integer.parseInt(p[4]);
                    gridPoints.add(new SpawnPoint(new Point(x, y), spawnTick, null, name));
                    continue;
                }


                String[] p = line.split(" ");

                String name = p[0];
                double x = Double.parseDouble(p[1]);
                double y = Double.parseDouble(p[2]);

                gridPoints.add(new Crossing(name, x, y, new Random()));
            }
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException("File not found "+e);
        }
        catch (IOException ex){
            System.out.println("Reading error "+ex);
        }

        create_streets(path, gridPoints, streets);
        var spawnPoints = gridPoints.stream()
                .filter(i -> i instanceof SpawnPoint)
                .map(i -> (SpawnPoint) i)
                .toList();

        for (SpawnPoint spawnPoint : spawnPoints) {
            streets.stream()
                    .filter(s -> s.getFirstPoint() == spawnPoint || s.getSecondPoint() == spawnPoint)
                    .findFirst().ifPresent(spawnPoint::setStreet);

        }
        return new ParsingResult(endtimeOfSimulation, tickspeed, spawnPoints,streets);
    }

    private void create_streets(String path, List<GridPoint> gridPoints, List<Street> streets) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String line;
            boolean isCrossing = false;

            while ((line = bufferedReader.readLine()) != null) {
                line = line.trim();

                // leere Zeilen überspringen
                if (line.isEmpty()) {
                    continue;
                }

                boolean isComment = line.startsWith("#");

                if (isComment) {
                    continue;
                }

                boolean isCrossingSection = line.equalsIgnoreCase("Kreuzungen:");
                if (isCrossingSection) {
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

                    //i =3, weil erste Referenz auf einen Gridpoint das vierte Element einer Zeile ist
                    //i += 2, weil nach Gridpoint noch Wahrscheinlichkeit angegeben wird
                    for (int i = 3; i < parsed_line.length; i += 2) {
                        String targetName = parsed_line[i];
                        int probability = Integer.parseInt(parsed_line[i + 1]);

                        var target = gridPoints.stream()
                                .filter(a -> targetName.equals(a.getName()))
                                .findFirst()
                                .orElse(null);

                        if (target == null) {
                            continue;
                        }

                        Street street = new Street(crossing, target, crossing.getPoint().getDistanceToPoint(target.getPoint()));
                        Street streetOppositeDirection =
                                new Street(target, crossing, crossing.getPoint().getDistanceToPoint(target.getPoint()));

                        streets.add(street);
                        streets.add(streetOppositeDirection);

                        Crossing casted_crossing = (Crossing) crossing;

                        casted_crossing.addStreetToMap(street, probability);
                        casted_crossing.addStreetToMap(streetOppositeDirection, probability);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found "+e);
        }
        catch (IOException ex){
            System.out.println("Reading error "+ex);
        }
    }
}


