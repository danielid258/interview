package com.daniel.interview.distribution.lock.redis;

import redis.clients.jedis.Jedis;

import java.time.LocalTime;

import static com.daniel.interview.distribution.lock.redis.LockConstants.NOT_EXIST;
import static com.daniel.interview.distribution.lock.redis.LockConstants.OK;
import static com.daniel.interview.distribution.lock.redis.LockConstants.SECONDS;

/**
 * Daniel on 2018/10/17.
 */
public class LockCaseFinal extends RedisLock {
    public LockCaseFinal(Jedis jedis, String lockKey) {
        super(jedis, lockKey);
    }

    //版本2-设置锁的过期时间
    //SET lockKey value NX EX 30
    @Override
    public void lock() {
        while (true) {
            String result = jedis.set(lockKey, lockValue, NOT_EXIST, SECONDS, 30);
            if (OK.equals(result)) {
                System.out.println("线程id:" + Thread.currentThread().getId() + "加锁成功!时间:" + LocalTime.now());

                //开启定时刷新过期时间
                isOpenExpirationRenewal = true;
                scheduleExpirationRenewal();
                break;
            }
            System.out.println("线程id:" + Thread.currentThread().getId() + "获取锁失败，休眠10秒!时间:" + LocalTime.now());
            //休眠10秒
            sleepBySecond(10);
        }
    }

    //利用Redis的lua脚本来实现解锁操作的原子性
    @Override
    public void unlock() {
        System.out.println("线程id:" + Thread.currentThread().getId() + "解锁!时间:" + LocalTime.now());
        //这段Lua脚本在执行的时候要把的lockValue作为ARGV[1]的值传进去，把lockKey作为KEYS[1]的值传进去
        String checkAndDelScript = "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                "return redis.call('del', KEYS[1]) " +
                "else " +
                "return 0 " +
                "end";
        jedis.eval(checkAndDelScript, 1, lockKey, lockValue);

        //将isOpenExpirationRenewal属性置为false，停止刷新过期时间的线程轮询。
        isOpenExpirationRenewal = false;
    }
}
