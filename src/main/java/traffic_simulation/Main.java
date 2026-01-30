package traffic_simulation;

import traffic_simulation.input_output.input.InputReader;
import traffic_simulation.model.street_network.street_network_points.SpawnPoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
        InputReader inputReader = new InputReader();
            try {
                inputReader.readFile("C:\\Users\\zandi\\Documents\\Verkehrssimulation\\Verkehrssimulation---LF-13\\src\\main\\resources\\IHK_01\\Eingabe.txt");
                List<SpawnPoint> erg = inputReader.getSpawnPoints();
                for(SpawnPoint x: erg){
                    System.out.println(x);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }
}
