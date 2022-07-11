package tdd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("StringCalculator의")
class StringCalculatorTest {

    private StringCalculator strCal;

    @BeforeEach
    void setUp() {
        this.strCal = new StringCalculator();
    }

    @Nested
    @DisplayName("parseNonNegativeInteger 메서드는")
    class test_parseNonNegativeInteger {

        private String str;

        @Test
        @DisplayName("null -> 0 반환")
        public void nullTest() {
            // given
            str = null;

            // when
            int result = strCal.parseNonNegativeInteger(str);

            // then
            assertThat(result).isEqualTo(0);
        }

        @Test
        @DisplayName("빈 문자열 -> 0 반환")
        public void emptyTest() {
            str = "";

            int result = strCal.parseNonNegativeInteger(str);
            assertThat(result).isEqualTo(0);
        }

        @ParameterizedTest
        @ValueSource(strings = {" ", "  ", "   ", "    ", "     "})
        @DisplayName("공백 문자열 -> 0 반환")
        public void blankTest(String text) {
            int result = strCal.parseNonNegativeInteger(text);
            assertThat(result).isEqualTo(0);
        }

        @ParameterizedTest
        @ValueSource(strings = {"0", "1", "3", "5", "15", "65535", "2147483647"})
        @DisplayName("0 또는 양의 정수 문자열 -> 같은 값 반환")
        public void nonNegativeNumberTest(String text) {
            int result = strCal.parseNonNegativeInteger(text);
            int expect = Integer.parseInt(text);

            assertThat(result).isEqualTo(expect);
        }
    }

}
