package traffic_simulation.model.street_network.street_network_points;

import org.junit.jupiter.api.Test;
import traffic_simulation.model.street_network.Street;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CrossingTest {


    @Test
    void given_3_streets_with_equal_probability_and_random_number_one_then_first_street_is_chosen() {

        Random random = mock(Random.class);

        Crossing sut = new Crossing("1. e4 c6", 0, 0, random);
        Crossing cr = new Crossing("1. e4 e6", 0, 0, random);

        Street s1 = new Street(sut, cr, 1);
        Street s2 = new Street(sut, cr, 2);
        Street s3 = new Street(sut, cr, 3);

        sut.addStreetToMap(s1, 1);
        sut.addStreetToMap(s2, 1);
        sut.addStreetToMap(s3, 1);

        when(random.nextInt(1, 3)).thenReturn(1);

        var res = sut.getNextStreet(s2);

        assertEquals(res, s1);
    }
}