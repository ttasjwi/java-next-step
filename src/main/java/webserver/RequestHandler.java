package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

import exception.HttpRequestMessageException;
import exception.NullRequestLineException;
import http.request.RequestLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler extends Thread {

    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private static final String REQUEST_LINE_DELIMITER = " +";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            // RequestLine 읽기
            RequestLine requestLine = readRequestLine(br);
            String url = requestLine.getUrl();
            log.debug("Request Line : {}", requestLine);

            String requestHeader;

            // 요청 헤더들을 마지막까지 읽기
            while (!(requestHeader = br.readLine()).isEmpty()) {
                log.debug("Request Header/ {}", requestHeader);
            }

            // 요청을 분석하고 응답하기
            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = Files.readAllBytes(new File("./webapp" + url). toPath());
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (HttpRequestMessageException me) {
            log.error(me.getMessage());
        }
    }

    private RequestLine readRequestLine(BufferedReader br) throws IOException {
        String requestLineString = br.readLine();

        if (Objects.isNull(requestLineString)) {
            throw new NullRequestLineException();
        }
        return RequestLine.of(requestLineString);
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
