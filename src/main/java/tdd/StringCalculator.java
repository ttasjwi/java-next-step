package tdd;

import java.util.Objects;

public class StringCalculator {

    /**
     * 문자열을 숫자로 변환한다.
     * - 형식에 맞지 않으면 NumberFormatException 발생
     * - 음수이면 RuntimeException 발생
     * - Null, 빈 문자열, 공백 문자열 -> 0 반환
     */
    public int parseNonNegativeInteger(String str) {
        if (Objects.isNull(str) || str.isBlank()) {
            return 0;
        }

        int number = Integer.parseInt(str);
        validateNumberRange(number);
        return number;
    }

    /**
     * 숫자가 음수이면 RuntimeException을 발생 시킨다.
     */
    private void validateNumberRange(int number) {
        if (number < 0) {
            throw new RuntimeException();
        }
    }
}
