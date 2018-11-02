package com.daniel.interview.aspect;

import com.daniel.interview.annotation.RequestLimit;
import com.daniel.interview.constant.RedisKeyPrefixConstant;
import com.daniel.interview.enums.ResultEnum;
import com.daniel.interview.exception.RequestLimitException;
import com.daniel.interview.util.HttpRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * Daniel on 2018/10/26.
 *
 * 使用redis计数来控制 限定的时间内对接口的访问频率
 *
 * 例如 验证码接口限制访问频率为: 1分钟内只能访问2次
 *
 * 使用自定义注解的方式 在需要被限制访问频率的方法上加注解即可控制
 */
@Slf4j
@Component
@Aspect
public class RequestLimitAspect {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * @param joinpoint
     * @param requestLimit
     */
    //拦截controller 中有 @RequestLimit的方法
    @Before("execution(public * com.daniel.interview.controller.*.*(..)) && @annotation(requestLimit)")
    public void requestLimit(JoinPoint joinpoint, RequestLimit requestLimit) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String ip = HttpRequestUtil.getIpAdd(request);
        String url = request.getRequestURL().toString();
        String key = RedisKeyPrefixConstant.METHOD_REQ_RATE_LIMIT.concat(url).concat(ip);
        //对key的值+1
        long count = redisTemplate.opsForValue().increment(key, 1);
        //第1次访问
        if (count == 1) {
            //把接口注解的限制时间 设置为key的过期时间 单位MILLISECONDS
            redisTemplate.expire(key, requestLimit.duration(), TimeUnit.MILLISECONDS);
        }
        //在限制时间内 没有超过限定的请求次数 放行请求

        //在限制时间内 超过了限定的请求次数 拒绝请求
        if (count > requestLimit.count()) {
            log.error(String.format("用户IP[%s]访问地址[%s],在限制时间[%d]毫秒内超过了限定的请求次数[%d],当前为第次[%d]访问", ip, url, requestLimit.duration(), requestLimit.count(), count));
            throw new RequestLimitException(ResultEnum.FAILURE,
                    String.format("接口访问频率超出限制:[%d]毫秒内只能请求[%d]次", requestLimit.duration(), requestLimit.count()));
        }
        //通过redis key过期时间 实现每个时间限制区间的自动推移 即每次过期就解除当前一轮的限制
    }
}