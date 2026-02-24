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
    void given_eingabe_1_then_result_has_five_spawnpoints() throws IOException {

        InputReader.ParsingResult parsingResult = reader.readFile("src/main/resources/IHK_01/Eingabe.txt");

        assertEquals(5, parsingResult.spwanPoints().size());

    }
    @Test
    void given_eingabe_1_then_result_has_100_endtimeOfSimulation() throws IOException{

        InputReader.ParsingResult parsingResult = reader.readFile("src/main/resources/IHK_01/Eingabe.txt");

        assertEquals(100,parsingResult.endtimeOfSimulation());
    }
    @Test
    void given_eingabe_1_than_result_has_1_tickspeed() throws IOException{

        InputReader.ParsingResult parsingResult = reader.readFile("src/main/resources/IHK_01/Eingabe.txt");

        assertEquals(1,parsingResult.tickspeed());
    }
    @Test
    void given_invalid_numbers_then_number_format_exception() {
        assertThrows(NumberFormatException.class, () ->
                reader.readFile("src/main/resources/invalid/Eingabe.txt")
        );
    }

    @Test
    void given_eingabe_1_then_references_are_correct() throws IOException {

        InputReader.ParsingResult result =
                reader.readFile("src/main/resources/IHK_01/Eingabe.txt");

        var spawnPoints = result.spwanPoints();
        var streets = result.streets();

        // --- SpawnPoints haben Straßen ---
        for (SpawnPoint sp : spawnPoints) {
            assertNotNull(sp.getStreet(), "SpawnPoint hat keine Straße: " + sp.getName());
        }

        // --- Alle Streets haben gültige Endpunkte ---
        for (Street street : streets) {
            assertNotNull(street.getFirstPoint(), "Street ohne Startpunkt");
            assertNotNull(street.getSecondPoint(), "Street ohne Zielpunkt");
        }

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

        // --- Jede Street sollte mit mindestens einem Crossing verbunden sein ---
        for (Street street : streets) {
            boolean hasCrossing =
                    street.getFirstPoint() instanceof Crossing ||
                    street.getSecondPoint() instanceof Crossing;

            assertTrue(hasCrossing, "Street ohne Crossing-Anbindung gefunden");
        }
    }



    /*TODO tests für
        - Zeitraum, ticks, ...
        - Tests, ob die Referenzen passen
        - invalide Eingaben
    */
}