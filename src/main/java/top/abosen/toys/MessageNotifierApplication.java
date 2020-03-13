package top.abosen.toys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.awt.*;

/**
 * @author qiubaisen
 * @date 2020/3/13
 */

@EnableConfigurationProperties(Config.class)
@SpringBootApplication
public class MessageNotifierApplication implements ApplicationRunner {
    @Autowired
    private Environment _environment;

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(MessageNotifierApplication.class);
        builder.headless(false).web(WebApplicationType.NONE).run(args);
    }


    @Bean
    public Robot robot() throws AWTException {
        return new Robot();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("启动模式:" + _environment.getProperty("mode"));
    }
}
