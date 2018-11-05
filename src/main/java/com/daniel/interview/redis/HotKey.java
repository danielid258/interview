package com.daniel.interview.redis;

/**
 * Daniel on 2018/11/5.
 *
 * 热点 Key 问题的发现与解决
 */
public class HotKey {
    /**
     *
     *
     热点问题产生的原因大致有以下两种：
     1 用户消费的数据远大于生产的数据（热卖商品、热点新闻、热点评论、明星直播）
     2 请求分片集中，超过单 Server 的性能极限。

     热点问题的危害
     流量集中，达到物理网卡上限。
     请求过多，缓存分片服务被打垮。
     DB 击穿，引起业务雪崩。
     如前文讲到的，当某一热点 Key 的请求在某一主机上超过该主机网卡上限时，由于流量的过度集中，会导致服务器中其它服务无法进行。如果热点过于集中，热点 Key 的缓存过多，超过目前的缓存容量时，就会导致缓存分片服务被打垮现象的产生。当缓存服务崩溃后，此时再有请求产生，会缓存到后台 DB 上，由于DB 本身性能较弱，在面临大请求时很容易发生请求穿透现象，会进一步导致雪崩现象，严重影响设备的性能。


     常见解决方案
     通常的解决方案主要集中在对客户端和 Server 端进行相应的改造。


     */
}