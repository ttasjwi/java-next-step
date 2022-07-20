package http.request;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("RequestLine 생성 시")
public class RequestLineTest {

    @Test
    @DisplayName("GET 메서드를 정상적으로 분리해 갖고 있어야 한다.")
    void getMethodTest() {
        // given
        String requestLineString = "GET /index.html HTTP/1.1";
        RequestLine requestLine = RequestLine.of(requestLineString);

        // when
        HttpMethod method = requestLine.getMethod();

        // then
        assertThat(method).isSameAs(HttpMethod.GET);
    }

    @Test
    @DisplayName("POST 메서드를 정상적으로 분리해 갖고 있어야 한다.")
    void postMethodTest() {
        // given
        String requestLineString = "POST /members HTTP/1.1";
        RequestLine requestLine = RequestLine.of(requestLineString);

        // when
        HttpMethod method = requestLine.getMethod();

        // then
        assertThat(method).isEqualTo(HttpMethod.POST);
    }

    @Test
    @DisplayName("프로토콜을 정상적으로 분리해 갖고 있어야 한다.")
    void protocolTest() {
        // given
        String requestLineString = "GET /index.html HTTP/1.1";
        RequestLine requestLine = RequestLine.of(requestLineString);

        // when
        String protocol = requestLine.getProtocol();

        // then
        assertThat(protocol).isEqualTo("HTTP/1.1");
    }

    @Nested
    @DisplayName("QueryString이 없을 때")
    class when_has_no_queryString {

        @Test
        @DisplayName("url을 정상적으로 분리해 갖고 있어야한다.")
        void has_right_url_test() {
            // given
            String requestLineString = "GET /index.html HTTP/1.1";
            RequestLine requestLine = RequestLine.of(requestLineString);

            // when
            String url = requestLine.getUrl();

            // then
            assertThat(url).isEqualTo("/index.html");
        }

        @Test
        @DisplayName("QueryParameters가 비어 있어야한다.")
        void it_has_empty_QueryParameters() {
            // given
            String requestLineString = "GET /index.html HTTP/1.1";
            RequestLine requestLine = RequestLine.of(requestLineString);

            // when
            Map<String, String> queryParameters = requestLine.getQueryParameters();

            // then
            assertThat(queryParameters).isEmpty();
        }
    }

    @Nested
    @DisplayName("QueryString이 1개 있을 때")
    class when_has_one_queryString {

        private final String utf8RequestUrl = "GET /index.html?name=ttasjwi HTTP/1.1";

        @Test
        @DisplayName("url을 정상적으로 분리해 갖고 있어야한다.")
        void has_right_url_test() {
            // given
            String urlEncodedRequestLine = URLEncoder.encode(utf8RequestUrl, StandardCharsets.UTF_8);

            // when
            RequestLine requestLine = RequestLine.of(urlEncodedRequestLine);

            // then
            assertThat(requestLine.getUrl()).isEqualTo("/index.html");
        }

        @Test
        @DisplayName("QueryParameters에 1개의 파라미터가 있어야한다.")
        void it_has_one_QueryParameter() {
            // given
            String urlEncodedRequestLine = URLEncoder.encode(utf8RequestUrl, StandardCharsets.UTF_8);

            // when
            RequestLine requestLine = RequestLine.of(urlEncodedRequestLine);
            Map<String, String> queryParameters = requestLine.getQueryParameters();

            // then
            assertThat(queryParameters.size()).isEqualTo(1);
        }

        @Test
        @DisplayName("올바른 쿼리 파라미터를 분리해, 가지고 있어야한다.")
        void it_has_right_QueryParameter() {
            // given
            String urlEncodedRequestLine = URLEncoder.encode(utf8RequestUrl, StandardCharsets.UTF_8);

            // when
            RequestLine requestLine = RequestLine.of(urlEncodedRequestLine);

            // then
            assertThat(requestLine.getParameter("name")).isEqualTo("ttasjwi");
        }

    }

    @Nested
    @DisplayName("QueryString이 2개 있을 때")
    class when_has_two_queryString {

        private final String utf8RequestUrl = "GET /index.html?name=ttasjwi&age=20 HTTP/1.1";

        @Test
        @DisplayName("url을 정상적으로 분리해 갖고 있어야한다.")
        void has_right_url_test() {
            // given
            String urlEncodedRequestLine = URLEncoder.encode(utf8RequestUrl, StandardCharsets.UTF_8);

            // when
            RequestLine requestLine = RequestLine.of(urlEncodedRequestLine);

            // then
            assertThat(requestLine.getUrl()).isEqualTo("/index.html");
        }

        @Test
        @DisplayName("QueryParameters에 1개의 파라미터가 있어야한다.")
        void it_has_two_QueryParameter() {
            // given
            String urlEncodedRequestLine = URLEncoder.encode(utf8RequestUrl, StandardCharsets.UTF_8);

            // when
            RequestLine requestLine = RequestLine.of(urlEncodedRequestLine);
            Map<String, String> queryParameters = requestLine.getQueryParameters();

            // then
            assertThat(queryParameters.size()).isEqualTo(2);
        }

        @Test
        @DisplayName("올바른 파라미터들을 분리해서, 가지고 있어야한다.")
        void it_has_right_QueryParameters() {
            // given
            String urlEncodedRequestLine = URLEncoder.encode(utf8RequestUrl, StandardCharsets.UTF_8);

            // when
            RequestLine requestLine = RequestLine.of(urlEncodedRequestLine);

            // then
            SoftAssertions softAssertions = new SoftAssertions();
            softAssertions.assertThat(requestLine.getParameter("name")).isEqualTo("ttasjwi");
            softAssertions.assertThat(requestLine.getParameter("age")).isEqualTo("20");
            softAssertions.assertAll();
        }

    }

}
