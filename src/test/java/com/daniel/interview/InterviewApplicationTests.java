package com.daniel.interview;

import com.daniel.interview.domain.UserInfo;
import com.daniel.interview.mq.FanoutSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InterviewApplicationTests {
    @Autowired
    private FanoutSender fanoutSender;

    @Test
    public void fanoutTest() {
        fanoutSender.send(new UserInfo(1L, "daiel", 25));
    }

}
