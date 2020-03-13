package top.abosen.toys.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import top.abosen.toys.Config;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author qiubaisen
 * @date 2020/3/13
 */
@Service
@ConditionalOnProperty(value = "mode",havingValue = "server")
public class Server {
    @Autowired
    private Config _config;

    public void receiveMessage() throws IOException {
        ProcessBuilder builder = new ProcessBuilder();
        String timeInfo = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
        builder.command(_config.getTriggerScript(), "内网通收到了一条消息!", "消息通知", timeInfo);
        builder.start();
    }


}
