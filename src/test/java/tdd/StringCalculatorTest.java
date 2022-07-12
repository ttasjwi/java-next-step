package tdd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

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
        @ValueSource(strings = {"ㅁㄴㅇㄹ", "!2ㅏㅁㅇ람ㄹ", "asdfadf", "332d짱29-"})
        @DisplayName("형식에 맞지 않는 문자열 -> NumberFormatException 발생")
        public void badFormatTest(String str) {
            assertThatThrownBy(() -> strCal.parseNonNegativeInteger(str))
                    .isInstanceOf(NumberFormatException.class);
        }

        @ParameterizedTest
        @ValueSource(strings = {"0", "1", "3", "5", "15", "65535", "2147483647"})
        @DisplayName("0 또는 양의 정수 문자열 -> 같은 값 반환")
        public void nonNegativeNumberTest(String text) {
            int result = strCal.parseNonNegativeInteger(text);
            int expect = Integer.parseInt(text);

            assertThat(result).isEqualTo(expect);
        }

        @ParameterizedTest
        @ValueSource(strings = {"-1", "-3", "-5", "-27", "-65535", "-2147483647"})
        @DisplayName("음의 정수 문자열 -> RuntimeException 발생")
        public void negativeNumberTest(String str) {
            assertThatThrownBy(() -> strCal.parseNonNegativeInteger(str))
                    .isInstanceOf(RuntimeException.class);
        }
    }

    @Nested
    @DisplayName("splitWords 메서드는")
    class test_splitWords {

        @Test
        @DisplayName("null -> 빈 리스트 반환")
        public void nullTest() {
            //given
            String str = null;

            //when
            List<String> result = strCal.splitWords(str);

            //then
            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("빈 문자열 -> 빈 리스트 반환")
        public void emptyTest() {
            //given
            String str = "";

            //when
            List<String> result = strCal.splitWords(str);

            //then
            assertThat(result).isEmpty();
        }

        @ParameterizedTest
        @ValueSource(strings = {" ", "  ", "   ", "     ", "      "})
        @DisplayName("공백 문자열 -> 빈 리스트 반환")
        public void blankTest(String str) {
            //when
            List<String> result = strCal.splitWords(str);

            //then
            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("콤마(,)로 문자열을 분리")
        public void commaTest() {
            String str = "토끼,너구리,닭";

            // when
            List<String> result = strCal.splitWords(str);

            // then
            assertThat(result).containsExactlyInAnyOrder("토끼", "너구리", "닭");
        }

        @Test
        @DisplayName("콜론(:)으로 문자열을 분리")
        public void colonTest() {
            String str = "가:나:다";

            // when
            List<String> result = strCal.splitWords(str);

            // then
            assertThat(result).containsExactlyInAnyOrder("가", "나", "다");
        }

        @Test
        @DisplayName("콤마(,) 또는 콜론(:)로 문자열을 분리")
        public void commaAndColonTest() {
            String str = "가,나:다";

            // when
            List<String> result = strCal.splitWords(str);

            // then
            assertThat(result).containsExactlyInAnyOrder("가", "나", "다");
        }

        @Nested
        @DisplayName("맨 앞에 //문자\\n을 지정하여 커스텀 구분자 설정이 가능하다.")
        class customDelimiterTest {

            @Test
            @DisplayName("\"//!\\n...\" 지정 -> \"!\"로 문자열을 분리")
            public void exclamationMarkTest() {
                String str = "//!\n가!나!다";

                // when
                List<String> result = strCal.splitWords(str);

                // then
                assertThat(result).containsExactlyInAnyOrder("가", "나", "다");
            }

            @Test
            @DisplayName("\"//;\\n...\" 지정 -> \";\"로 문자열을 분리")
            public void questionMarkTest() {
                String str = "//;\n가;나;다";

                // whe
                List<String> result = strCal.splitWords(str);

                // then
                assertThat(result).containsExactlyInAnyOrder("가", "나", "다");
            }


        }

    }

    @Nested
    @DisplayName("parseNonNegativeIntegers 메서드는")
    class test_parseNonNegativeIntegers {

        @Test
        @DisplayName("리스트가 null -> 빈 리스트 반환")
        public void nullListTest() {
            //given
            List<String> strs = null;

            //when
            List<Integer> numbers = strCal.parseNonNegativeIntegers(strs);

            //then
            assertThat(numbers).isEmpty();
        }

        @Test
        @DisplayName("빈 리스트 -> 빈 리스트 반환")
        public void emptyListTest() {
            //given
            List<String> strs = new ArrayList<>();

            //when
            List<Integer> numbers = strCal.parseNonNegativeIntegers(strs);

            //then
            assertThat(numbers).isEmpty();
        }

        @Test
        @DisplayName("하나라도 형식에 맞지 않는 문자열 있음 -> NumberFormatException 발생")
        public void strangeWordContains_Test() {
            //given
            List<String> strs = List.of("가", "1", "2");

            //when & then
            assertThatThrownBy(() -> strCal.parseNonNegativeIntegers(strs))
                    .isInstanceOf(NumberFormatException.class);
        }


        @Test
        @DisplayName("하나라도 음수가 있음 -> RuntimeException 발생")
        public void negativeIntegerContains_Test() {
            //given
            List<String> strs = List.of("-1", "1", "2");

            //when & then
            assertThatThrownBy(() -> strCal.parseNonNegativeIntegers(strs))
                    .isInstanceOf(RuntimeException.class);
        }

        @Test
        @DisplayName("0 또는 양의 정수 -> 같은 값 리스트 반환")
        public void nonNegativeNumbersTest() {
            // given
            List<String> strs = List.of("0", "1", "2", "3", "5", "7", "65535", "2147483647");

            // when
            List<Integer> numbers = strCal.parseNonNegativeIntegers(strs);

            // then
            assertThat(numbers)
                    .containsExactlyInAnyOrder(0, 1, 2, 3, 5, 7, 65535, 2147483647);
        }

        @Test
        @DisplayName("공백 -> 0 반환")
        public void emptyStrContainsTest() {
            // given
            List<String> strs = List.of("", "1", "2", "5");

            // when
            List<Integer> numbers = strCal.parseNonNegativeIntegers(strs);

            // then
            assertThat(numbers)
                    .containsExactlyInAnyOrder(0, 1, 2, 5);
        }

        @Test
        @DisplayName("null 요소 -> 0 반환")
        public void nullContainsTest() {
            // given
            List<String> strs = new ArrayList<>();
            strs.add(null);
            strs.add("1");

            // when
            List<Integer> numbers = strCal.parseNonNegativeIntegers(strs);

            // then
            assertThat(numbers)
                    .containsExactlyInAnyOrder(0, 1);
        }

    }

    @Nested
    @DisplayName("sum 메서드는 문자열을 읽고 쪼개서 그 합을 반환한다.")
    class test_sum {

        @Test
        @DisplayName("콤마(,)로 구분되있을 경우 그 합을 반환")
        public void commaSplitTest() {
            //given
            String text = "1,2,3,4,5";

            //when
            int sum = strCal.sum(text);

            //then
            int expect = 1+2+3+4+5;
            assertThat(sum).isEqualTo(expect);
        }

        @Test
        @DisplayName("콜론(:)로 구분되있을 경우 그 합을 반환")
        public void colonSplitTest() {
            //given
            String text = "1:2:3:4:5";

            //when
            int sum = strCal.sum(text);

            //then
            int expect = 1+2+3+4+5;
            assertThat(sum).isEqualTo(expect);
        }

        @Test
        @DisplayName("하나라도 음수가 있는 문자열 -> RuntimeException 발생")
        public void negativeIntegerContains_Test() {
            //given
            String text = "-1,0,1,2,3";

            //when & then
            assertThatThrownBy(() -> strCal.sum(text))
                    .isInstanceOf(RuntimeException.class);
        }

        @Test
        @DisplayName("null -> 0 반환")
        public void nullTest() {
            //given
            String text = null;

            //when
            int sum = strCal.sum(text);

            //then
            int expect = 0;
            assertThat(sum).isEqualTo(expect);
        }

        @Test
        @DisplayName("빈 문자열 -> 0 반환")
        public void emptyTest() {
            //given
            String text = "";

            //when
            int sum = strCal.sum(text);

            //then
            int expect = 0;
            assertThat(sum).isEqualTo(expect);
        }

        @ParameterizedTest
        @ValueSource(strings = {" ", "  ", "   ", "    ", "     "})
        @DisplayName("공백 문자열 -> 0 반환")
        public void blankTest(String text) {
            //when
            int sum = strCal.sum(text);

            //then
            int expect = 0;
            assertThat(sum).isEqualTo(expect);
        }

        @Nested
        @DisplayName("커스텀 문자열로 구분되있을 경우 그 합을 반환")
        class customDelimiterTest {

            @Test
            @DisplayName("세미콜론(;)을 커스텀 문자열로 지정 -> 그 합 반환")
            public void semicolonTest() {
                // given
                String text = "//;\n1;2;3;4;5";

                // when
                int sum = strCal.sum(text);

                // then
                int expect = 1+2+3+4+5;
                assertThat(sum).isEqualTo(expect);
            }
        }
    }
}
