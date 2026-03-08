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
    private List<Car> cars = new ArrayList<>();
    private final OutPutWriter writer = new OutPutWriter();
    private final List<Street> streets;

    public Simulation(InputReader.ParsingResult parsingResult) {
        this.spawnPoints = parsingResult.spawnPoints();
        this.maximumTicks = parsingResult.endtimeOfSimulation();
        this.streets = parsingResult.streets();
    }


    public void simulateAndWriteToFile() {
        //convenience Method, since I don't want every test to create outputfiles

        List<Car> cars = simulate();

        writer.writeFahrzeuge(maximumTicks, cars);
        writer.writePlan(streets);
        writer.writeStatistic(streets);
    }

    public List<Car> simulate() {

        int ticksDone = 0;
        //TODO wir haben Tickspeed, wollen aber wahrscheinlich jeden Tick simulieren -> fehlt atm
        while (ticksDone < maximumTicks) {
            spawnCars(ticksDone);
            simulateCars(ticksDone);
            ticksDone++;
        }

        return cars;
    }

    private void spawnCars(int currentTick) {
        for (SpawnPoint spawnPoint : spawnPoints) {
            Car car = spawnPoint.spawnCar(currentTick);

            if (car != null) {
                cars.add(car);
            }
        }
    }

    private void simulateCars(int currentTick) {

        List<Car> survivingCars = new ArrayList<>();

        for (Car car : cars) {
            car = car.drive(currentTick);

            if (car != null) {
                survivingCars.add(car);
            }
        }

        cars = survivingCars;
    }
}
