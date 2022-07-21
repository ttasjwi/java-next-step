package http.response;

import http.common.Cookie;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class HttpResponse {

    private final DataOutputStream dos;
    private final List<Cookie> cookies = new ArrayList<>();

    public HttpResponse(OutputStream out) throws IOException {
        this.dos = new DataOutputStream(out);
    }

    /**
     * 정적 리소스를 반환한다.
     */
    public void responseStaticResource(String url) throws IOException {
        byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());
        dos.writeBytes("HTTP/1.1 200 OK \r\n");
        dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
        dos.writeBytes("Content-Length: " + body.length + "\r\n");
        responseCookies();
        dos.writeBytes("\r\n");
        responseBody(body);
    }

    /**
     * 출력 스트림을 통해서, 인자로 전달된 body를 스트림에 전달
     */
    private void responseBody(byte[] body) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }

    public void redirectTo(String redirectUrl) throws IOException {
        dos.writeBytes("HTTP/1.1 302 Found \r\n");
        dos.writeBytes("Location: " + redirectUrl + "\r\n");
        responseCookies();
        dos.writeBytes("\r\n");
    }

    public void addCookie(Cookie cookie) {
        cookies.add(cookie);
    }

    private void responseCookies() throws IOException {
        for (Cookie cookie : cookies) {
            dos.writeBytes(String.format("Set-Cookie: %s=%s;", cookie.getKey(),cookie.getValue()));

            if (cookie.getMaxAge() != null) {
                dos.writeBytes("max-age="+cookie.getMaxAge()+";");
            }

            dos.writeBytes("path=/\r\n");
        }
    }
}
