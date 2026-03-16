package traffic_simulation.input_output.output_writer;

import traffic_simulation.model.Point;
import traffic_simulation.model.cars.Car;
import traffic_simulation.model.street_network.Street;

import java.nio.Buffer;
import java.util.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.BiFunction;


public class OutPutWriter {

    public void writePlan(List<Street> streets) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("plan.txt"));

        for (Street street : streets) {
            writeStreet(writer, street);
        }
    }

    public void writeStatistik(List<Street> streets) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("statistik.txt"));
        writer.write(
                "Gesamtanzahl Fahrzeuge pro 100m:"
        );
        writer.newLine();
        for (Street street : streets) {
            writer.write(street.getFirstPoint().getName()+"->"+street.getSecondPoint().getName()+": "
            +street.getTotalCarsOnStreetPerOnehundredMeters());
            writer.newLine();
        }
        writer.write(
                "Maximale Anzahl Fahrzeuge pro 100m:"
        );
        writer.newLine();
        for(Street street : streets) {
            writer.write(street.getFirstPoint().getName()+"->"+street.getSecondPoint().getName()+": "
                    +street.getMaximumCarsOnStreetPerOnehundredMeters());
            writer.newLine();
        }
    }

    public void writeFahrzeuge(int endTimeOfSimulation,List<Car> cars) throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter("fahrzeuge.txt"));

        Map<Integer, Map<Integer, Map<Point,Point>>> carPositionsPerTimestep = new HashMap<>();

        for (Car car : cars) {
            for (Map.Entry<Integer, Map<Point,Point>> positionEntry : car.getPositions().entrySet()) {

                int timeStep = positionEntry.getKey();
                Map<Point, Point> positionAndDestination = positionEntry.getValue();

                carPositionsPerTimestep.computeIfAbsent(timeStep, t -> new HashMap<>()).put(car.getId(),positionAndDestination);

            }
        }

        for (int i = 0; i <= endTimeOfSimulation; i++) {
            writer.write("*** t = "+i);
            writer.newLine();
            if (carPositionsPerTimestep.containsKey(i)) {
                Map<Integer, Map<Point,Point>> currentPositionsByCarId = carPositionsPerTimestep.get(i);
                for (Map.Entry<Integer, Map<Point,Point>> idAndPositionEntry : currentPositionsByCarId.entrySet()) {
                    int carIDToPrint = idAndPositionEntry.getKey();
                    Map<Point, Point> positionAndDestination = idAndPositionEntry.getValue();
                    Map.Entry<Point, Point> positionAndDestinationToPrint = positionAndDestination.entrySet().iterator().next();
                    Point position = positionAndDestinationToPrint.getKey();
                    Point destination = positionAndDestinationToPrint.getValue();
                    writer.write(position.getX()+" "+position.getY()+" "+destination.getX()+" "+destination.getY()+" "+carIDToPrint);
                    writer.newLine();
                }
            }
        }

    }


    private void writeStreet(BufferedWriter writer, Street street) throws IOException {
        writer.write(street.getFirstPoint().getPoint().getX() + " "
                + street.getFirstPoint().getPoint().getY() + " "
                + street.getSecondPoint().getPoint().getX() + " "
                + street.getSecondPoint().getPoint().getY());
        writer.newLine();
    }
}
