package top.abosen.toys.picture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import top.abosen.toys.Config;
import top.abosen.toys.client.ScreenHelper;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author qiubaisen
 * @date 2020/3/13
 */

@Service
@ConditionalOnProperty(value = "mode", havingValue = "picture")
public class ShowPicture {

    @Autowired
    private Robot _robot;
    @Autowired
    private Config _config;

    public void showPic() {
        BufferedImage image = _robot.createScreenCapture(new Rectangle(_config.getX(), _config.getY(), _config.getWidth(), _config.getHeight()));
        JFrame frame = new JFrame();
        JPanel comp = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                g.drawImage(image, 20, 20, this);
            }
        };

        frame.getContentPane().add(comp);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200, 200);
        frame.setVisible(true);
    }

    @PostConstruct
    public void init() {
        showPic();
        System.out.println("average red: " + ScreenHelper.getAverageRed(_robot, _config));
    }

}
