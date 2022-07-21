package webserver;

import exception.HttpRequestMessageException;
import http.request.HttpMethod;
import http.request.HttpRequest;
import http.response.HttpResponse;
import member.dto.MemberJoinRequestDto;
import member.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

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
            HttpResponse response = new HttpResponse(out);
            sendResponse(request, response);
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (HttpRequestMessageException me) {
            log.error(me.getMessage());
        }
    }

    private void sendResponse(HttpRequest request, HttpResponse response) throws IOException {
        // 요청을 분석하고 응답하기
        String url = request.getUrl();
        HttpMethod method = request.getMethod();

        if (url.equals("/members/create") && method == HttpMethod.POST) {
            joinMember(request, response);
        }
        response.responseStaticResource(url);
    }

    private void joinMember(HttpRequest request, HttpResponse response) throws IOException {
        MemberJoinRequestDto memberJoinRequestDto = MemberJoinRequestDto.builder()
                .loginId(request.getParameter("loginId"))
                .password(request.getParameter("password"))
                .name(request.getParameter("name"))
                .email(request.getParameter("email"))
                .build();

        memberService.join(memberJoinRequestDto);

        response.redirectTo("/index.html");
    }

}
