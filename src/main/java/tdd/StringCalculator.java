package tdd;

import java.util.*;
import java.util.stream.Collectors;

public class StringCalculator {

    private static final String DEFAULT_WORD_SPLIT_DELIMITER = "[,:]";

    /**
     * 문자열이 주어지면 구분자로 분리하고 리스트로 반환.
     * - 디폴트 : , 또는 :로 분리
     * - //문자\n : 지정 문자로 분리
     */
    public List<String> splitWords(String str) {
        if (Objects.isNull(str) || str.isBlank()) {
            return new ArrayList<>();
        }
        return Arrays.stream(str.split(DEFAULT_WORD_SPLIT_DELIMITER))
                .collect(Collectors.toList());
    }

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
