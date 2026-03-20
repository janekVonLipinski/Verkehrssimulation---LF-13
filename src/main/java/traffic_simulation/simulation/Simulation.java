package traffic_simulation.simulation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import traffic_simulation.input_output.input.InputReader;
import traffic_simulation.input_output.output_writer.OutPutWriter;
import traffic_simulation.model.cars.Car;
import traffic_simulation.model.street_network.Street;
import traffic_simulation.model.street_network.street_network_points.SpawnPoint;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class  Simulation {

    @Getter
    private final List<SpawnPoint> spawnPoints;
    private final int maximumTicks;
    private final OutPutWriter writer = new OutPutWriter();
    private final List<Street> streets;
    private final List<Car> loggingCars = new ArrayList<>();
    private List<Car> simulationCars = new ArrayList<>();

    public Simulation(InputReader.ParsingResult parsingResult) {
        this.spawnPoints = parsingResult.spawnPoints();
        this.maximumTicks = parsingResult.endtimeOfSimulation();
        this.streets = parsingResult.streets();
    }


    public void simulateAndWriteToFile(String pathToOutputDir) {
        //convenience Method, since I don't want every test to create outputfiles

        simulate();

        writer.writeFahrzeuge(maximumTicks, loggingCars, pathToOutputDir);
        writer.writePlan(streets, pathToOutputDir);
        writer.writeStatistic(streets, pathToOutputDir);
    }

    public List<Car> simulate() {

        int ticksDone = 0;
        //TODO wir haben Tickspeed, wollen aber wahrscheinlich jeden Tick simulieren -> fehlt atm
        while (ticksDone < maximumTicks) {
            spawnCars(ticksDone);
            simulateCars(ticksDone);
            ticksDone++;
        }

        return simulationCars;
    }

    private void spawnCars(int currentTick) {
        for (SpawnPoint spawnPoint : spawnPoints) {
            Car car = spawnPoint.spawnCar(currentTick);

            if (car != null) {
                simulationCars.add(car);
                loggingCars.add(car);
            }
        }
    }

    private void simulateCars(int currentTick) {

        List<Car> survivingCars = new ArrayList<>();

        for (Car car : simulationCars) {
            car = car.drive(currentTick);

            if (car.getDestination() != null) {
                survivingCars.add(car);
            }
        }

        simulationCars = survivingCars;
    }
}
