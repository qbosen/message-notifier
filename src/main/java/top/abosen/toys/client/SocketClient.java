package top.abosen.toys.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import top.abosen.toys.Config;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/**
 * @author qiubaisen
 * @date 2020/3/13
 */
@Service
@ConditionalOnProperty(value = "mode", havingValue = "client")
public class SocketClient {
    @Autowired
    private Config _config;


    public void notifyServer() throws IOException {
        try (Socket socket = new Socket(_config.getHost(), _config.getPort())) {
            PrintStream ps = new PrintStream(new BufferedOutputStream(socket.getOutputStream()));
            ps.println(Config.MAGIC);
            ps.flush();
        }
    }

}
