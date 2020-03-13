package top.abosen.toys;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author qiubaisen
 * @date 2020/3/13
 */

@Getter
@Setter
@ConfigurationProperties("notifier")
public class Config {
    public static final String MAGIC = "0xCAFEBABE";

    private String host;
    private int port;
    private int x;
    private int y;
    private int height;
    private int width;
    private int thresholdRed;

    private int bigPeriod;
    private int littlePeriod;
    private int samples;
    private int notifyInterval;

    private String triggerScript;

}
