package com.daniel.interview;

import com.daniel.interview.domain.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * Daniel on 2018/11/5.
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void stringRedisTemplateTest(){
        //stringRedisTemplate test
        ExecutorService threadPool = Executors.newFixedThreadPool(1000);
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        IntStream.rangeClosed(0, 1000).forEach(i -> threadPool.execute(() -> valueOperations.increment("kk", 1)));

        valueOperations.set("k1","v1");
        final String k1 = valueOperations.get("k1");
        log.info("get cached value:[{}]", k1);
    }

    @Test
    public void redisTemplateTest(){
        //redisTemplate test
        UserInfo daniel = new UserInfo(1L, "Daniel", 25);
        String key = String.format("USER_INFO_%s", daniel.getId());
        redisTemplate.opsForValue().set(key, daniel);
        UserInfo userInfo = (UserInfo) redisTemplate.opsForValue().get(key);
        log.info("get cached userInfo:[{}]", userInfo.toString());
    }
}
