package traffic_simulation.input_output.input;

import org.junit.jupiter.api.Test;
import traffic_simulation.model.street_network.Street;
import traffic_simulation.model.street_network.street_network_points.Crossing;
import traffic_simulation.model.street_network.street_network_points.SpawnPoint;

import java.io.IOException;

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
/*
    @Test void givenMyCock() {

        SpawnPoint sp = new SpawnPoint();

        //sp.getNeighbour()
        //

    }
*/


    /*TODO tests für
        - Zeitraum, ticks, ...
        - Tests, ob die Referenzen passen
        - invalide Eingaben
    */
}