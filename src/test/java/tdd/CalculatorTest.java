package tdd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


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
        assertThat(cal.add(9,3)).isEqualTo(12);
    }

    @Test
    @DisplayName("subtract 메서드 인자로 9,3이 주어지면 6을 반환해야한다.")
    void subtractTest() {
        assertThat(cal.subtract(9,3)).isEqualTo(6);
    }

    @Test
    @DisplayName("multiply 메서드 인자로 9,3이 주어지면 27을 반환해야한다.")
    void multiplyTest() {
        assertThat(cal.multiply(9,3)).isEqualTo(27);
    }

    @Test
    @DisplayName("divide 메서드 인자로 9,3이 주어지면 3을 반환해야한다.")
    void divideTest() {
        assertThat(cal.divide(9,3)).isEqualTo(3);
    }

}
