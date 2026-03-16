package traffic_simulation;

import traffic_simulation.input_output.input.InputReader;
import traffic_simulation.simulation.Simulation;

public class Main {

    private static final String PATH_TO_FIRST_FILE = "src/main/resources/IHK_01/Eingabe.txt";

    public static void main(String[] args) {

        var reader = new InputReader();
        var input = reader.readFile(PATH_TO_FIRST_FILE);
        var simulation = new Simulation(input);

        simulation.simulateAndWriteToFile();
    }
}
