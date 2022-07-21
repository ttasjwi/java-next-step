package http.request;

import exception.NullRequestLineException;
import http.common.Cookie;
import lombok.extern.slf4j.Slf4j;
import util.HttpRequestUtils;
import util.IOUtils;
import util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class HttpRequest {

    private final RequestLine requestLine;
    private final Map<String, String> headers = new HashMap<>();
    private final String body;
    private final Map<String, String> parameters = new HashMap<>();

    private HttpRequest(RequestLine requestLine, Map<String, String> headers,  String body, Map<String, String> parameters) {
        this.requestLine = requestLine;
        this.headers.putAll(headers);
        this.body = body;
        this.parameters.putAll(parameters);
    }

    public static HttpRequest of(InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        RequestLine requestLine = getRequestLine(br);
        log.debug("RequestLine = {}", requestLine);

        Map<String, String> headers = getHeaders(br);
        for (Map.Entry<String, String> header : headers.entrySet()) {
            log.debug("Request Header: key = {}, value = {}", header.getKey(), header.getValue());
        }

        String body = getBody(br, headers);
        log.debug("Request Body = {}", body);

        Map<String, String> parameters = getParameters(requestLine, body);
        for (Map.Entry<String, String> parameter : parameters.entrySet()) {
            log.debug("Request Parameter: key = {}, value = {}", parameter.getKey(), parameter.getValue());
        }
        return new HttpRequest(requestLine, headers, body , parameters);
    }

    private static RequestLine getRequestLine(BufferedReader br) throws IOException {
        String requestLineString = br.readLine();

        if (Objects.isNull(requestLineString)) {
            throw new NullRequestLineException();
        }
        return RequestLine.of(requestLineString);
    }

    private static Map<String, String> getHeaders(BufferedReader br) throws IOException {
        Map<String, String> headers = new HashMap<>();

        String line;
        while (!(line = br.readLine()).isEmpty()) {
            Pair<String, String> pair = HttpRequestUtils.parseHeader(line);
            headers.put(pair.getKey(), pair.getValue());
        }
        return headers;
    }

    private static String getBody(BufferedReader br, Map<String, String> headers) throws IOException {
        int contentLength = getContentLength(headers);
        return (contentLength == 0)
                ? null
                : HttpRequestUtils.decodeURLEncoding(IOUtils.readData(br, contentLength));
    }

    private static int getContentLength(Map<String, String> headers) {
        int contentLength = 0;
        String contentLengthHeader = headers.get("Content-Length");
        if (contentLengthHeader != null) {
            contentLength = Integer.parseInt(contentLengthHeader);
        }
        return contentLength;
    }

    private static Map<String, String> getParameters(RequestLine requestLine, String body) {
        HttpMethod method = requestLine.getMethod();

        return (method == HttpMethod.POST)
                ? HttpRequestUtils.parseQueryString(body)
                : requestLine.getQueryParameters();
    }

    public String getUrl() {
        return requestLine.getUrl();
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    public String getHeader(String key) {
        return headers.get(key);
    }
    public Map<String, Cookie> getCookies() {
        Map<String, Cookie> cookies = new HashMap<>();
        String cookiesString = getHeader("Cookie");
        cookies.putAll(HttpRequestUtils.parseCookies(cookiesString));
        return cookies;
    }
    public Cookie getCookie(String key) {
        Map<String, Cookie> cookies = getCookies();
        return cookies.get(key);
    }
}
