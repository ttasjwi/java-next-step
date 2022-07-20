package webserver;

import exception.HttpRequestMessageException;
import http.request.HttpMethod;
import http.request.HttpRequest;
import member.dto.MemberJoinRequestDto;
import member.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

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
            HttpRequest request = HttpRequest.of(in);
            sendResponse(request, out);
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (HttpRequestMessageException me) {
            log.error(me.getMessage());
        }
    }

    private void sendResponse(HttpRequest request, OutputStream out) throws IOException {
        // 요청을 분석하고 응답하기
        String url = request.getUrl();
        HttpMethod method = request.getMethod();

        if (url.equals("/members/create") && method == HttpMethod.POST) {
            log.info("회원 가입 요청");
            joinMember(request);
        }
        DataOutputStream dos = new DataOutputStream(out);
        byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());
        response200Header(dos, body.length);
        responseBody(dos, body);
    }

    private void joinMember(HttpRequest request) {
        MemberJoinRequestDto memberJoinRequestDto = MemberJoinRequestDto.builder()
                .loginId(request.getParameter("loginId"))
                .password(request.getParameter("password"))
                .name(request.getParameter("name"))
                .email(request.getParameter("email"))
                .build();

        memberService.join(memberJoinRequestDto);
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
