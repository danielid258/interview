package com.daniel.interview.mq;

import com.daniel.interview.domain.UserInfo;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Daniel on 2018/10/24.
 */
@Component
public class FanoutSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(UserInfo userInfo) {
        rabbitTemplate.convertAndSend(RabbitMQCConfig.FANOUT_EXCHANGE,"",userInfo);
    }
}
