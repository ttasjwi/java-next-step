package tdd;

public class Calculator {

    /**
     * 두 정수를 합을 반환하는 메서드
     */
    public int add(int i, int j) {
        return i + j;
    }

    /**
     * 두 정수의 차를 반환하는 메서드
     */
    public int subtract(int i, int j) {
        return i - j;
    }

    /**
     * 두 정수의 곱을 반환하는 메서드
     */
    public int multiply(int i, int j) {
        return i * j;
    }

    /**
     * 두 정수를 나눴을 때의 몫을 반환하는 메서드
     */
    public int divide(int i, int j) {
        return i / j;
    }

    public static void main(String[] args) {
        Calculator cal = new Calculator();
        System.out.println(cal.add(9,3));
        System.out.println(cal.subtract(9,3));
        System.out.println(cal.multiply(9,3));
        System.out.println(cal.divide(9,3));
    }
}
