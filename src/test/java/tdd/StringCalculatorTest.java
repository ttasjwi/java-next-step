package tdd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("StringCalculator의")
class StringCalculatorTest {

    private StringCalculator strCal;

    @BeforeEach
    void setUp() {
        this.strCal = new StringCalculator();
    }

    @Nested
    @DisplayName("add 메서드는")
    class testAdd {

        private String text;

        @Test
        @DisplayName("null -> 0 반환")
        public void nullTest() {
            // given
            text = null;

            // when
            int result = strCal.add(text);

            // then
            assertThat(result).isEqualTo(0);
        }

        @Test
        @DisplayName("빈 문자열 -> 0 반환")
        public void emptyTest() {
            text = "";

            int result = strCal.add(text);
            assertThat(result).isEqualTo(0);
        }

        @Test
        @DisplayName("공백 문자열 -> 0 반환")
        public void blankTest() {
            text = "   ";

            int result = strCal.add(text);
            assertThat(result).isEqualTo(0);
        }
    }

}
