package tdd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class StringCalculator {

    private static final String DEFAULT_WORD_SPLIT_DELIMITER = "[,:]";

    /**
     * 문자열 리스트가 주어질 때 각각을 음이 아닌 정수로 파싱
     *  1. 빈 문자열 요소, 공백문자열 요소, null 요소는 0으로 파싱
     *  2. null -> 빈 리스트 반환
     *  3. 빈 리스트 -> 빈 리스트 반환
     *  4. 하나라도 형식에 안 맞으면 NumberFormatException 발생
     *  5. 하나라도 음수면 RuntimeException 발생
     */
    public List<Integer> parseNonNegativeIntegers(List<String> strs) {
        if (Objects.isNull(strs)) {
            return new ArrayList<>();
        }

        return strs.stream()
                .map(this::parseNonNegativeInteger)
                .collect(Collectors.toList());
    }

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
