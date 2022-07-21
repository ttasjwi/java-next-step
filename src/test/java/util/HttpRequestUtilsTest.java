package util;

import http.common.Cookie;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("HttpRequestUtils의")
public class HttpRequestUtilsTest {

    @Nested
    @DisplayName("parseQueryString 메서드는")
    class parseQueryStringTest {

        @Test
        @DisplayName("null -> 빈 Map 반환")
        public void nullTest() {
            //given
            String queryString = null;

            //when
            Map<String, String> parameters = HttpRequestUtils.parseQueryString(queryString);

            //then
            assertThat(parameters).isEmpty();
        }

        @ParameterizedTest
        @ValueSource(strings = {"", " ", "  ", "   "})
        @DisplayName("빈 문자열 또는 공백 문자열 -> 빈 Map 반환")
        public void blankTest(String queryString) {
            // when
            Map<String, String> parameters = HttpRequestUtils.parseQueryString(queryString);

            // then
            assertThat(parameters).isEmpty();
        }

        @Nested
        @DisplayName("파라미터 한 개일 때")
        class when_singleParameter {

            String queryString = "userId=javajigi";

            @Test
            @DisplayName("결과 Map에 하나의 요소만 담겨 있어야함")
            public void result_has_one_element() {
                Map<String, String> parameters = HttpRequestUtils.parseQueryString(queryString);
                assertThat(parameters.size()).isEqualTo(1);
            }

            @Test
            @DisplayName("key, value가 잘 분리되어 map에 저장되어야함")
            public void result_has_right_key_and_value() {
                Map<String, String> parameters = HttpRequestUtils.parseQueryString(queryString);
                assertThat(parameters.get("userId")).isEqualTo("javajigi");
            }

            @Test
            @DisplayName("관계 없는 파라미터를 꺼낼 때 null을 반환해야함")
            public void getInvalidParameterTest() {
                Map<String, String> parameters = HttpRequestUtils.parseQueryString(queryString);
                assertThat(parameters.get("password")).isNull();
            }

        }

        @Nested
        @DisplayName("파라미터 두 개일 때")
        class when_double_Parameter {

            String queryString = "userId=javajigi&password=password2";

            @Test
            @DisplayName("결과 Map에 두 개의 요소만 담겨 있어야함")
            public void result_has_two_element() {
                Map<String, String> parameters = HttpRequestUtils.parseQueryString(queryString);
                assertThat(parameters.size()).isEqualTo(2);
            }

            @Test
            @DisplayName("key, value가 잘 분리되어 map에 저장되어야함")
            public void result_has_right_key_and_value() {
                Map<String, String> parameters = HttpRequestUtils.parseQueryString(queryString);

                SoftAssertions softAssertions = new SoftAssertions();
                softAssertions.assertThat(parameters.get("userId")).isEqualTo("javajigi");
                softAssertions.assertThat(parameters.get("password")).isEqualTo("password2");
                softAssertions.assertAll();
            }

            @Test
            @DisplayName("관계 없는 파라미터를 꺼낼 때 null을 반환해야함")
            public void getInvalidParameterTest() {
                Map<String, String> parameters = HttpRequestUtils.parseQueryString(queryString);
                assertThat(parameters.get("trash")).isNull();
            }

        }

        @Test
        @DisplayName("형식에 맞지 않는 파라미터가 속해있을 때 이는 파싱하지 않음")
        public void contaians_invalid_parameter_test() {
            // given
            String queryString = "userId=javajigi&password";

            // when
            Map<String, String> parameters = HttpRequestUtils.parseQueryString(queryString);

            // then
            SoftAssertions softAssertions = new SoftAssertions();
            softAssertions.assertThat(parameters.get("userId")).isEqualTo("javajigi");
            softAssertions.assertThat(parameters.get("password")).isNull();
            softAssertions.assertAll();
        }

    }

    @Nested
    @DisplayName("getKeyValue 메서드는")
    class getKeyValueTest {

        @Test
        @DisplayName("구분자를 기준으로 분리하여 key, value를 가진 Pair를 만든다")
        public void createPairSuccess() {
            // given
            String keyValue = "userId=javajigi";
            String regex = "=";

            // when
            Pair<String, String> pair = HttpRequestUtils.getKeyValue(keyValue, regex);

            // then
            SoftAssertions softAssertions = new SoftAssertions();
            softAssertions.assertThat(pair.getKey()).isEqualTo("userId");
            softAssertions.assertThat(pair.getValue()).isEqualTo("javajigi");
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("형식에 맞지 않을 경우 null 반환")
        public void invalidKeyValue_make_null() {
            Pair<String, String> pair = HttpRequestUtils.getKeyValue("userId", "=");
            assertThat(pair).isNull();
        }
    }

    @Nested
    @DisplayName("parseCookies 메서드로 얻은 쿠키 목록에서는")
    class parseCookiesTest {

        String cookieString = "logined=true; JSessionId=1234";
        @Test
        @DisplayName("갯수가 맞게 쿠키가 반환되어야 한다.")
        public void cookieCountTest() {
            // when
            Map<String, Cookie> cookies = HttpRequestUtils.parseCookies(cookieString);

            //then
            assertThat(cookies.size()).isEqualTo(2);
        }

        @Test
        @DisplayName("올바른 쿠키들이 반환되어야 한다.")
        public void it_returns_right_cookies() {
            // when
            Map<String, Cookie> cookies = HttpRequestUtils.parseCookies(cookieString);

            // then
            Cookie loginedCookie = cookies.get("logined");
            Cookie jSessionIdCookie = cookies.get("JSessionId");

            SoftAssertions softAssertions = new SoftAssertions();
            softAssertions.assertThat(loginedCookie.getValue()).isEqualTo("true");
            softAssertions.assertThat(jSessionIdCookie.getValue()).isEqualTo("1234");
            softAssertions.assertAll();
        }

    }
    @Test
    public void parseHeader() throws Exception {
        String header = "Content-Length: 59";
        Pair<String, String> pair = HttpRequestUtils.parseHeader(header);
        assertThat(pair).isEqualTo(new Pair<>("Content-Length", "59"));
    }

    @Nested
    @DisplayName("decodeURLEncoding 메서드는")
    class decodeURLEncodingTest {
        //given
        String urlEncodedString = "userId=%EB%95%83%EB%95%83%EB%95%83";

        @Test
        @DisplayName("URL 인코딩 문자열을 UTF-8로 디코딩할 수 있어야 한다.")
        public void it_decode_urlEncodedString_to_utf8_string() {
            //when
            String decodedString = HttpRequestUtils.decodeURLEncoding(urlEncodedString);

            //then
            assertThat(decodedString).isEqualTo("userId=땃땃땃");
        }
    }

}
