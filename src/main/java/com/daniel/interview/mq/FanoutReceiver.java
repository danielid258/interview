package com.daniel.interview.mq;

import com.daniel.interview.domain.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Daniel on 2018/10/24.
 */
@Component
@Slf4j
public class FanoutReceiver {
    @RabbitListener(queues = RabbitMQCConfig.FANOUT_QUEUE1)
    public void receiveFromQueue1(UserInfo userInfo) {
        log.info("receiveFromQueue1 user info:{}", userInfo.toString());
    }

    @RabbitListener(queues = RabbitMQCConfig.FANOUT_QUEUE2)
    public void receiveFromQueue2(UserInfo userInfo) {
        log.info("receiveFromQueue2 user info:{}", userInfo.toString());
    }

}
