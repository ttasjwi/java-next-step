package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

import exception.HttpRequestMessageException;
import exception.NullRequestLineException;
import http.request.HttpMethod;
import http.request.RequestLine;
import member.dto.MemberJoinRequestDto;
import member.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler extends Thread {

    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    private final MemberService memberService;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;

        AppConfig appConfig = AppConfig.getSingletonInstance();
        memberService = appConfig.memberService();
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            // RequestLine 읽기
            RequestLine requestLine = readRequestLine(br);
            log.debug("Request Line : {}", requestLine);

            // 요청 헤더들을 마지막까지 읽기
            String requestHeader;
            while (!(requestHeader = br.readLine()).isEmpty()) {
                log.debug("Request Header/ {}", requestHeader);
            }
            sendResponse(requestLine, out);
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (HttpRequestMessageException me) {
            log.error(me.getMessage());
        }
    }

    private void sendResponse(RequestLine requestLine, OutputStream out) throws IOException {
        // 요청을 분석하고 응답하기
        String url = requestLine.getUrl();
        HttpMethod method = requestLine.getMethod();

        if (url.equals("/members/create") && method == HttpMethod.GET) {
            log.info("회원 가입 요청");
            joinMember(requestLine);
        }
        DataOutputStream dos = new DataOutputStream(out);
        byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());
        response200Header(dos, body.length);
        responseBody(dos, body);
    }

    private void joinMember(RequestLine requestLine) {
        MemberJoinRequestDto memberJoinRequestDto = MemberJoinRequestDto.builder()
                .loginId(requestLine.getParameter("loginId"))
                .password(requestLine.getParameter("password"))
                .name(requestLine.getParameter("name"))
                .email(requestLine.getParameter("email"))
                .build();

        memberService.join(memberJoinRequestDto);
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
