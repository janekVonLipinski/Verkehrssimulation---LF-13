package traffic_simulation;

import traffic_simulation.input_output.input.InputReader;
import traffic_simulation.simulation.Simulation;

public class Main {

    public static void main(String[] args) {

        if (args.length == 0) {
            throw new IllegalArgumentException(
                    "Call with the path of the input file: " +
                    "java -jar traffic_simulator.jar <path-to-file>");
        }

        String path_to_file = args[0];

        System.out.println("Start simulation of file: " + path_to_file);

        var reader = new InputReader();
        var input = reader.readFile(path_to_file);
        var simulation = new Simulation(input);

        simulation.simulateAndWriteToFile();

        System.out.println("Finished simulation");
    }
}
