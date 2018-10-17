package com.daniel.interview.distribution.lock.redis;

import redis.clients.jedis.Jedis;

import static com.daniel.interview.distribution.lock.redis.LockConstants.*;

/**
 * Daniel on 2018/10/17.
 */
public class LockCase1 extends RedisLock {
    public LockCase1(Jedis jedis, String lockKey) {
        super(jedis, lockKey);
    }

    //最基础的版本1
    //SET lockKey value NX
    @Override
    public void lock() {
        while (true) {
            String result = jedis.set(lockKey, "value", NOT_EXIST);
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
    /**
     * 假设有两个客户端A和B，A获取到分布式的锁。
     * A执行了一会，突然A所在的服务器断电了（或者其他什么的），也就是客户端A挂了。
     * 这时出现一个问题，这个锁一直存在，且不会被释放，其他客户端永远获取不到锁
     *
     *
     * 通过设置过期时间来解决这个问题 LockCase2
     */
}
