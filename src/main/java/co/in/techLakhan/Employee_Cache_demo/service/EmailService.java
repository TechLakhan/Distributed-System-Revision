package co.in.techLakhan.Employee_Cache_demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    @Async
    public void sendEmail(final String name) {
        log.info("EmailService Thread : {}",
                Thread.currentThread().getName());
        try {
            log.info("Send mail to employee {}", name);
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            throw new RuntimeException();
        }
        log.info("Mail sent successfully.");
    }
}
