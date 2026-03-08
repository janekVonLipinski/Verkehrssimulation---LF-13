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
    private final int numberOfGivenTicks;
    private List<Car> cars = new ArrayList<>();
    private final OutPutWriter writer = new OutPutWriter();
    private final List<Street> streets;

    public Simulation(InputReader.ParsingResult parsingResult) {
        this.spawnPoints = parsingResult.spawnPoints();
        this.numberOfGivenTicks = parsingResult.endtimeOfSimulation();
        this.streets = parsingResult.streets();
    }

    //convenience Method, since I dont want evrey test to create outputfiles

    public List<Car> simulateAndWriteToFile() {

        List<Car> cars = simulate();

        //Intentionally not putting this into an extra function, since this code should only be
        //called after a finished simulation

        writer.writeFahrzeuge(numberOfGivenTicks, cars);
        writer.writePlan(streets);
        writer.writeStatistic(streets);

        return cars;
    }

    public List<Car> simulate() {

        int ticksDone = 0;
        //TODO wir haben Tickspeed, wollen aber wahrscheinlich jeden Tick simulieren -> fehlt atm
        while (ticksDone < numberOfGivenTicks) {
            spawnCars();
            simulateCars();
            ticksDone++;
        }

        return cars;
    }

    private void spawnCars() {
        for (SpawnPoint spawnPoint : spawnPoints) {
            Car car = spawnPoint.spawnCar();

            if (car != null) {
                cars.add(car);
            }
        }
    }

    private void simulateCars() {

        List<Car> survivingCars = new ArrayList<>();

        for (Car car : cars) {
            car = car.drive();

            if (car != null) {
                survivingCars.add(car);
            }
        }

        cars = survivingCars;
    }
}
