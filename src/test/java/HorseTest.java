import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HorseTest {

    @Test
    public void nullCheckName() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 10, 100));
    }

    @Test
    public void nullCheckNameException() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 10, 100));
        assertEquals("Name cannot be null.", e.getMessage());
    }

    static Stream<String> nameValueFactory() {
        return Stream.of("", " ", "  ", "\t", "\n", "\n\n\n\n", "\t\t\t");
    }

    @ParameterizedTest
    @MethodSource("nameValueFactory")
    public void paramsEmptyCheckName(String name) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 10, 100));
        assertEquals(IllegalArgumentException.class, e.getClass());
    }

    @ParameterizedTest
    @MethodSource("nameValueFactory")
    public void paramsCheckNameException(String name) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 10, 100));
        assertEquals("Name cannot be blank.", e.getMessage());
    }

    @Test
    public void negativeCheckSpeed() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("KOHb", -10, 12));
    }

    @Test
    public void negativeCheckSpeedException() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse("NEKOHb", -12.0, 100));
        assertEquals("Speed cannot be negative.", e.getMessage());
    }

    @Test
    public void negativeCheckDistance() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("KOHb", 12, -120.0));
    }

    @Test
    public void negativeCheckDistanceException() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse("NEKOHb", 15, -90.0));
        assertEquals("Distance cannot be negative.", e.getMessage());
    }

    @Test
    public void getName() throws NoSuchFieldException, IllegalAccessException {
        Horse testHorse = new Horse("HEKOHb", 12, 14);
        Field field = Horse.class.getDeclaredField("name");
        field.setAccessible(true);
        String nameValue = (String) field.get(testHorse);
        assertEquals("HEKOHb", nameValue);
        // assertEquals("HEKOHb", testHorse.getName()); //изнвчально сделал так, после просмотра видео поправил
    }

    @Test
    public void getSpeed() {
        Double checkspeed = 443.0;
        Horse testhorse = new Horse("KOHb", checkspeed, 12);
        assertEquals(checkspeed, testhorse.getSpeed());
    }

    @Test
    public void getDistance() {
        Horse testHorse = new Horse("HEKOHb", 12, 122);
        assertEquals(122, testHorse.getDistance());
    }

    @Test
    public void getZeroDistance() {
        Horse testZeroHorse = new Horse("HEKOHb", 12);
        assertEquals(0, testZeroHorse.getDistance());
    }

    @Test
    public void moveCheckGetRandom() {
        try (MockedStatic<Horse> mockStatic = Mockito.mockStatic(Horse.class)) {
            new Horse("HEKOHb", 12, 200).move();
            mockStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

//    @ParameterizedTest
//    @ValueSource(doubles = { 120.0, 250.0,235.23})
//    public void moveCheckParamGetRandom(Double doubles) {
//        try (MockedStatic<Horse> mockStatic = Mockito.mockStatic(Horse.class)) {
//            mockStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(doubles);
//            assertEquals(doubles, Horse.getRandomDouble(0.2, 0.9));
//        }
//}


        @ParameterizedTest
        @ValueSource( doubles = {1.0,4.0, 15.0, 5.0, 18.0, 44.0})
        public void moveCheckParamGetRandom(Double value) {
            try (MockedStatic<Horse> mockStatic = Mockito.mockStatic(Horse.class)) {
                Horse horseTest = new Horse("KOHb",15,140);
                mockStatic.when(() -> Horse.getRandomDouble(0.2,0.9)).thenReturn(value);

                horseTest.move();

                assertEquals(140+15*value, horseTest.getDistance());
            }
        }


}
