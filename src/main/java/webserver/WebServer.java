package webserver;

import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServer {
    private static final Logger log = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;

    public static void main(String args[]) throws Exception {
        int port = initPort(args);

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            log.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                RequestHandler requestHandler = new RequestHandler(connection);
                requestHandler.start();
            }
        }
    }

    /**
     *
     자바 실행 과정에서 명령으로 넘겨진 인자가 있는지 확인 후 서버 포트를 확인.

     1. 포트를 지정하면 지정 포트를 사용
     2. 지정하지 않았을 경우 기본 포트인 8080 포트를 사용한다.

     */
    private static int initPort(String[] args) {
        return (isEmptyArguments(args))
                ? DEFAULT_PORT
                : Integer.parseInt(args[0]);
    }

    /**
     * 자바 실행 시, args가 존재하는 지 확인
     */
    private static boolean isEmptyArguments(String[] args) {
        return args == null || args.length == 0;
    }
}
