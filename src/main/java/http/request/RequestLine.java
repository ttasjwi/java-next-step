package http.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import util.HttpRequestUtils;

import java.util.HashMap;
import java.util.Map;

@Getter
@ToString(of = {"method", "url", "protocol"})
public class RequestLine {

    private static final String REQUEST_LINE_DELIMITER = " +";
    private static final String QUESTION_MARK = "?";

    private final String method;
    private final String url;
    private final String protocol;
    private final Map<String, String> queryParameters = new HashMap<>();

    @Builder
    public RequestLine(String method, String url, String protocol, Map<String, String> queryParameters) {
        this.method = method;
        this.url = url;
        this.protocol = protocol;
        this.queryParameters.putAll(queryParameters);
    }

    /**
     * Http 요청의 첫번째 라인을 기반으로 RequestLine 객체 생성
     */
    public static RequestLine of(String requestLineString) {
        String decodedString = HttpRequestUtils.decodeURLEncoding(requestLineString);
        
        String[] requestLineTokens = decodedString.split(REQUEST_LINE_DELIMITER);
        String method = requestLineTokens[0];
        String url = requestLineTokens[1];
        String queryString = "";
        String protocol = requestLineTokens[2];
        
        if (url.contains(QUESTION_MARK)) {
            int indexOfQuestionMark = url.indexOf(QUESTION_MARK);
            queryString = url.substring(indexOfQuestionMark + 1);
            url = url.substring(0, indexOfQuestionMark);
        }

        Map<String, String> queryParameters = HttpRequestUtils.parseQueryString(queryString);

        return new RequestLine(method, url, protocol, queryParameters);
    }

    public String getParameter(String key) {
        return queryParameters.get(key);
    }

}
