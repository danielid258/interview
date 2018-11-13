package com.daniel.interview.redis;

/**
 * Daniel on 2018/11/5.
 *
 * Jedis 是一个优秀的基于Java语言的Redis客户端,其不足也很明显：Jedis 在实现上是直接连接 Redis-Server,在多个线程间共享一个Jedis实例时是线程不安全的,
 * 如果想要在多线程场景下使用Jedis,需要创建连接池,为每个Jedis实例增加物理连接,但是,当连接数量增多时,会消耗较多的物理资源。

 与 Jedis 相比,Lettuce 则完全克服了其线程不安全的缺点：Lettuce 是一个可伸缩的线程安全的 Redis 客户端,支持同步、异步和响应式模式。
 多个线程可以共享一个连接实例,而不必担心多线程并发问题。它基于优秀 Netty NIO 框架构建,支持 Redis 的高级功能,如 Sentinel,集群,流水线,自动重新连接和 Redis 数据模型。
 */
public class LettuceClient {
}
