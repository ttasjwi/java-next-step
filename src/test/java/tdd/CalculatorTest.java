package tdd;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Calculator의")
class CalculatorTest {

    @Test
    @DisplayName("add 메서드 인자로 9,3이 주어지면 12를 반환해야한다.")
    void addTest() {
        Calculator cal = new Calculator();
        System.out.println(cal.add(9, 3));
    }

    @Test
    @DisplayName("subtract 메서드 인자로 9,3이 주어지면 6을 반환해야한다.")
    void subtractTest() {
        Calculator cal = new Calculator();
        System.out.println(cal.subtract(9, 3));
    }

    @Test
    @DisplayName("multiply 메서드 인자로 9,3이 주어지면 27을 반환해야한다.")
    void multiplyTest() {
        Calculator cal = new Calculator();
        System.out.println(cal.multiply(9, 3));
    }

    @Test
    @DisplayName("divide 메서드 인자로 9,3이 주어지면 3을 반환해야한다.")
    void divideTest() {
        Calculator cal = new Calculator();
        System.out.println(cal.divide(9, 3));
    }
}
