package traffic_simulation;

import traffic_simulation.input_output.input.InputReader;
import traffic_simulation.simulation.Simulation;

public class Main {

    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("Call with the path of the input file and output: " +
                    "java -jar traffic_simulator.jar <path-to-input-file> <path-to_output_dir>");
        }

        String pathToInputFile = args[0];
        String pathToOutputDir = args[1];

        System.out.println("Start simulation of file: " + pathToInputFile);

        var reader = new InputReader();
        var input = reader.readFile(pathToInputFile);
        var simulation = new Simulation(input);

        simulation.simulateAndWriteToFile(pathToOutputDir);

        System.out.println("Finished simulation");
    }
}
