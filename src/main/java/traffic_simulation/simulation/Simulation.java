package traffic_simulation.simulation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import traffic_simulation.input_output.input.InputReader;
import traffic_simulation.model.cars.Car;
import traffic_simulation.model.street_network.street_network_points.SpawnPoint;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class  Simulation {

    @Getter
    private final List<SpawnPoint> spawnPoints;
    private final int numberOfGivenTicks;
    private List<Car> cars = new ArrayList<>();

    public Simulation(InputReader.ParsingResult parsingResult) {
        this.spawnPoints = parsingResult.spawnPoints();
        this.numberOfGivenTicks = parsingResult.endtimeOfSimulation();
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
