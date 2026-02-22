package traffic_simulation.input_output.output_writer;

import traffic_simulation.model.street_network.GridPoint;
import traffic_simulation.model.street_network.Street;
import traffic_simulation.model.street_network.street_network_points.Crossing;
import traffic_simulation.simulation.Simulation;
import traffic_simulation.model.street_network.street_network_points.SpawnPoint;

import java.sql.Array;
import java.util.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class OutPutWriter {

    private BufferedWriter writer;
    void writePlan(Simulation simulation) throws IOException {
        //starte mit einem spawnpoint -> iteriere über graph mittels kreuzungen

        writer = new BufferedWriter(new FileWriter("plan.txt"));

        SpawnPoint startPoint = simulation.getSpawnPoints().getFirst();
        Queue<GridPoint> gridPointsToVisit = new ArrayDeque<>();
        Set<GridPoint> gridPointsvisited = new HashSet<>();

        gridPointsToVisit.add(startPoint);

        while(!gridPointsToVisit.isEmpty()) {
            GridPoint currentPoint = gridPointsToVisit.poll();

            if (currentPoint instanceof SpawnPoint) {
                SpawnPoint currentSpawnPoint = (SpawnPoint) currentPoint;
                ArrayList<GridPoint> neighbours = currentSpawnPoint.getNeighbours();
            } else {
                Crossing currentCrossing = (Crossing) currentPoint;

            }


        }

    }

    void writeStatistik() {
        //über die straßen, wie komme ich an die straßen? genauso wie bei writePlan

    }

    void writeFahrzeuge() {
        //
    }

    void writeStreet(BufferedWriter writer, GridPoint currentPoint, GridPoint connectedPoint) throws IOException {
        System.out.println("Street");
        writer.write(currentPoint.getPoint().getX()+" "
                +currentPoint.getPoint().getY()+" "
                +connectedPoint.getPoint().getX()+" "
                +connectedPoint.getPoint().getY());
        writer.newLine();
    }

}
