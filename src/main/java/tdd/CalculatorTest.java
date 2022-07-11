package tdd;

public class CalculatorTest {

    public static void main(String[] args) {
        Calculator cal = new Calculator();
        addTest(cal);
        subtractTest(cal);
        multiplyTest(cal);
        divideTest(cal);
    }

    private static void addTest(Calculator cal) {
        System.out.println("Calculator의 add 메서드 인자로 9,3이 주어지면 12를 반환해야한다.");
        System.out.println(cal.add(9, 3));
    }

    private static void subtractTest(Calculator cal) {
        System.out.println("Calculator의 subtract 메서드 인자로 9,3이 주어지면 6을 반환해야한다.");
        System.out.println(cal.subtract(9, 3));
    }

    private static void multiplyTest(Calculator cal) {
        System.out.println("Calculator의 multiply 메서드 인자로 9,3이 주어지면 27을 반환해야한다.");
        System.out.println(cal.multiply(9, 3));
    }

    private static void divideTest(Calculator cal) {
        System.out.println("Calculator의 divide 메서드 인자로 9,3이 주어지면 3을 반환해야한다.");
        System.out.println(cal.divide(9, 3));
    }

}
