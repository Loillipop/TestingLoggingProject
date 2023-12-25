import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HippodromeTest {
    @Test
    public void nullCheckHorse() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    public void nullCheckHorseException() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", e.getMessage());
    }


    @Test
    public void nullCheckListHorse() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
    }

    @Test
    public void nullCheckListHorseException() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));

        assertEquals("Horses cannot be empty.", e.getMessage());
    }

    @Test
    public void getHorse() {
        List<Horse> horses;
        Random random = new Random();
        horses = random.ints()
                .limit(30)
                .mapToObj(x -> new Horse("" + Math.random(), Math.random(), Math.random()))
                .collect(Collectors.toList());

        Hippodrome hippodrome = new Hippodrome(horses);

        assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    public void moveHorse() {

        List<Horse> horses;
        Random random = new Random();
        horses = random.ints()
                .limit(50)
                .mapToObj(mock -> mock(Horse.class))
                .collect(Collectors.toList());

        new Hippodrome(horses).move();

        horses.forEach(horse -> verify(horse).move());
    }

    @Test
    public void getWinner() {
        Horse horse1 = new Horse("12", 12, 5);
        Horse horse2 = new Horse("123", 10, 15);
        Horse horse3 = new Horse("1234", 2, 25);
        Horse horse4 = new Horse("12345", 4, 40);
        Horse horse5 = new Horse("123456", 6, 50);

        Hippodrome horses = new Hippodrome(List.of(horse1, horse2, horse3, horse4, horse5));

        assertSame(horse5, horses.getWinner());

    }

}
