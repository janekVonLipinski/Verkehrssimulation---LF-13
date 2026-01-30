package traffic_simulation.input_output.input;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class InputReaderTest {

    private final InputReader reader = new InputReader();

    @Test
    void given_eingabe_1_then_result_has_five_spawnpoints() throws IOException {

        String path = "src/main/resources/IHK_01/Eingabe.txt";

        InputReader.ParsingResult parsingResult = reader.readFile(path);

        assertEquals(5, parsingResult.spwanPoints().size());

    }

    /*TODO tests für
        - Zeitraum, ticks, ...
        - Tests, ob die Referenzen passen
        - invalide Eingaben
    */
}