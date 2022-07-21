package http.response;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class HttpResponse {

    private final DataOutputStream dos;
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
}
