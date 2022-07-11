package tdd;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Calculator의")
class CalculatorTest {

    private Calculator cal;

    @BeforeEach
    public void setUp() {
        cal = new Calculator();
    }

    @Test
    @DisplayName("add 메서드 인자로 9,3이 주어지면 12를 반환해야한다.")
    void addTest() {
        assertEquals(12, cal.add(9,3));
    }

    @Test
    @DisplayName("subtract 메서드 인자로 9,3이 주어지면 6을 반환해야한다.")
    void subtractTest() {
        assertEquals(6, cal.subtract(9,3));
    }

    @Test
    @DisplayName("multiply 메서드 인자로 9,3이 주어지면 27을 반환해야한다.")
    void multiplyTest() {
        assertEquals(27, cal.multiply(9,3));
    }

    @Test
    @DisplayName("divide 메서드 인자로 9,3이 주어지면 3을 반환해야한다.")
    void divideTest() {
        assertEquals(3, cal.divide(9,3));
    }

}
