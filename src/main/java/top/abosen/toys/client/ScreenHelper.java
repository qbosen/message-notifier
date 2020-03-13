package top.abosen.toys.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import top.abosen.toys.Config;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * @author qiubaisen
 * @date 2020/3/13
 */
@Service
@ConditionalOnProperty(value = "mode", havingValue = "client")
public class ScreenHelper {
    @Autowired
    private Robot _robot;
    @Autowired
    private Config _config;


    public boolean hasNotification() {
        return getAverageRed(_robot, _config) >= _config.getThresholdRed();
    }

    public static int getAverageRed(Robot robot, Config config) {
        BufferedImage screenCapture = robot.createScreenCapture(new Rectangle(config.getX(), config.getY(), config.getWidth(), config.getHeight()));
        final int[] pixels = ((DataBufferInt) screenCapture.getRaster().getDataBuffer()).getData();
        long red = 0;
        for (int pixel : pixels) {
            red += (pixel >> 16) & 0xFF; // Red
        }
        return (int) (red / (pixels.length));
    }
}
