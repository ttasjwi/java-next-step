package http.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(of = {"method", "url", "protocol"})
public class RequestLine {

    private static final String REQUEST_LINE_DELIMITER = " +";
    private String method;
    private String url;
    private String protocol;

    @Builder
    public RequestLine(String method, String url, String protocol) {
        this.method = method;
        this.url = url;
        this.protocol = protocol;
    }

    /**
     * Http 요청의 첫번째 라인을 기반으로 RequestLine 객체 생성
     */
    public static RequestLine of(String requestLineString) {
        String[] token = requestLineString.split(REQUEST_LINE_DELIMITER);

        return RequestLine.builder()
                .method(token[0])
                .url(token[1])
                .protocol(token[2])
                .build();
    }

}
