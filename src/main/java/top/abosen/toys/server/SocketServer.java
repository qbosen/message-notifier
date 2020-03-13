package top.abosen.toys.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import top.abosen.toys.Config;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author qiubaisen
 * @date 2020/3/13
 */
@Service
@ConditionalOnProperty(value = "mode", havingValue = "server")
public class SocketServer {
    @Autowired
    private Config _config;
    @Autowired
    private Server _server;
    private ServerSocket serverSocket;

    @PostConstruct
    public void init() throws Exception {
        serverSocket = new ServerSocket(_config.getPort());
        System.out.println("服务器已经启动，等待客户端连接");

        while (true) {
            Socket socket = serverSocket.accept();
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            if (Config.MAGIC.equals(br.readLine())) {
                System.out.println("有消息来了");
                _server.receiveMessage();
            }
        }
    }

}
