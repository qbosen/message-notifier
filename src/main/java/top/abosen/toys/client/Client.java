package top.abosen.toys.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import top.abosen.toys.Config;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author qiubaisen
 * @date 2020/3/13
 */

@Service
@ConditionalOnProperty(value = "mode",havingValue = "client")
public class Client {
    @Autowired
    private ScreenHelper _screenHelper;
    @Autowired
    private Config _config;
    @Autowired
    private SocketClient _socketClient;

    @PostConstruct
    public void init() throws InterruptedException, IOException {
        run();
    }

    public void run() throws InterruptedException, IOException {
        AtomicReference<LocalDateTime> reference = new AtomicReference<>(null);
        while (true) {
            if (scanOnce() && checkNotifyInterval(reference)) {
                doNotify();
            }
            TimeUnit.SECONDS.sleep(_config.getBigPeriod());
        }
    }

    private void doNotify() throws IOException {
        _socketClient.notifyServer();
    }

    private boolean checkNotifyInterval(AtomicReference<LocalDateTime> reference) {
        LocalDateTime lastTime = reference.get();
        if (lastTime == null) {
            reference.set(LocalDateTime.now());
            return true;
        }
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(lastTime, now);
        if (duration.toMillis() >= _config.getNotifyInterval() * 1000L) {
            reference.set(now);
            return true;
        }
        return false;
    }

    private boolean scanOnce() throws InterruptedException {
        boolean flag = false;
        int count = 0;
        while (!flag && count++ < _config.getSamples()) {
            flag = _screenHelper.hasNotification();
            TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(_config.getLittlePeriod()));
        }
        return flag;
    }


}
