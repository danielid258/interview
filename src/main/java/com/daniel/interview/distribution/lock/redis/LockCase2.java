package com.daniel.interview.distribution.lock.redis;

import redis.clients.jedis.Jedis;

import static com.daniel.interview.distribution.lock.redis.LockConstants.NOT_EXIST;
import static com.daniel.interview.distribution.lock.redis.LockConstants.OK;
import static com.daniel.interview.distribution.lock.redis.LockConstants.SECONDS;

/**
 * Daniel on 2018/10/17.
 */
public class LockCase2 extends RedisLock {
    public LockCase2(Jedis jedis, String lockKey) {
        super(jedis, lockKey);
    }

    //版本2-设置锁的过期时间
    //SET lockKey value NX EX 30
    @Override
    public void lock() {
        while (true) {
            String result = jedis.set(lockKey, "value", NOT_EXIST, SECONDS, 30);
            if (OK.equals(result)) {
                System.out.println(Thread.currentThread().getId() + "加锁成功!");
                break;
            }
        }
    }

    @Override
    public void unlock() {
        jedis.del(lockKey);
    }
}
