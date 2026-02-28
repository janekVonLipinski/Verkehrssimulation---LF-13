package traffic_simulation.input_output.input;

import org.junit.jupiter.api.Test;
import traffic_simulation.model.street_network.Street;
import traffic_simulation.model.street_network.street_network_points.Crossing;
import traffic_simulation.model.street_network.street_network_points.SpawnPoint;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class InputReaderTest {

    private final InputReader reader = new InputReader();

    @Test
    void given_eingabe_1_then_spawnPoint_count_is_five() {

        InputReader.ParsingResult parsingResult = reader.readFile("src/main/resources/IHK_01/Eingabe.txt");

        assertEquals(5, parsingResult.spawnPoints().size());

    }
    @Test
    void given_eingabe_1_then_endtimeOfSimulation_is_100() {
        InputReader.ParsingResult parsingResult = reader.readFile("src/main/resources/IHK_01/Eingabe.txt");

        assertEquals(100,parsingResult.endtimeOfSimulation());
    }
    @Test
    void given_eingabe_1_then_tickspeed_is_1() {

        InputReader.ParsingResult parsingResult = reader.readFile("src/main/resources/IHK_01/Eingabe.txt");

        assertEquals(1,parsingResult.tickspeed());
    }
    @Test
    void given_invalid_input_then_numberFormatException_is_thrown() {
        assertThrows(NumberFormatException.class, () ->
                reader.readFile("src/main/resources/invalid/Eingabe.txt")
        );
    }

    @Test
    void given_eingabe_1_then_each_spawnPoint_has_a_street() {

        InputReader.ParsingResult result =
                reader.readFile("src/main/resources/IHK_01/Eingabe.txt");

        var spawnPoints = result.spawnPoints();

        // --- SpawnPoints haben Straßen ---
        for (SpawnPoint spawnPoint : spawnPoints) {
            assertNotNull(spawnPoint.getStreet(), "SpawnPoint hat keine Straße: " + spawnPoint.getName());
        }
    }

    @Test
    void given_eingabe_1_then_all_streets_have_valid_endpoints() {

        InputReader.ParsingResult result =
                reader.readFile("src/main/resources/IHK_01/Eingabe.txt");

        var streets = result.streets();

        // --- Alle Streets haben gültige Endpunkte ---
        for (Street street : streets) {
            assertNotNull(street.getFirstPoint(), "Street ohne Startpunkt");
            assertNotNull(street.getSecondPoint(), "Street ohne Zielpunkt");
        }
    }

    @Test
    void given_eingabe_1_then_crossings_are_created() {

        InputReader.ParsingResult result = reader.readFile("src/main/resources/IHK_01/Eingabe.txt");

        var spawnPoints = result.spawnPoints();
        // --- Crossings über Traversierung finden ---
        Set<Crossing> crossings = new HashSet<>();

        for (SpawnPoint sp : spawnPoints) {
            Street street = sp.getStreet();

            if (street.getFirstPoint() instanceof Crossing c) {
                crossings.add(c);
            }
            if (street.getSecondPoint() instanceof Crossing c) {
                crossings.add(c);
            }
        }

        // --- Es sollten Crossings existieren ---
        assertFalse(crossings.isEmpty(), "Keine Crossings gefunden");
    }

    @Test
    void given_eingabe_1_then_each_street_is_connected_to_a_crossing() {

        InputReader.ParsingResult result = reader.readFile("src/main/resources/IHK_01/Eingabe.txt");

        var streets = result.streets();

        // --- Jede Street sollte mit mindestens einem Crossing verbunden sein ---
        for (Street street : streets) {
            boolean hasCrossing =
                    street.getFirstPoint() instanceof Crossing ||
                    street.getSecondPoint() instanceof Crossing;

            assertTrue(hasCrossing, "Street ohne Crossing-Anbindung gefunden");
        }
    }
}