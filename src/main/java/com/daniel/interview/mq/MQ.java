package com.daniel.interview.mq;

/**
 * Daniel on 2018/10/24.
 */
public class MQ {
    /**
     *

     ***** 为什么使用MQ
     (1)解耦
         系统间耦合性太强 如, 系统A在代码中直接调用系统B和系统C的代码, 如果将来D系统接入, 系统A还需要修改代码
         将消息写入MQ, 需要消息的系统从MQ中订阅, 从而系统A不需要做任何修改
     (2)异步
         一些非必要的业务逻辑以同步的方式运行, 太耗费时间。
         将消息写入MQ, 非必要的业务逻辑以异步的方式运行, 加快响应速度
     (3)削峰
         并发量大的时候, 所有的请求直接发到数据库, 造成数据库连接异常
         请求先发到MQ形成短暂的高峰期积 系统从MQ中拉取消息按照数据库并发处理能力

     MQ缺点
         降低系统可用性:引入MQ后, 那MQ挂了, 整个系统将不能正常服务
         增加系统复杂性:比如一致性问题、保证消息不被重复消费, 保证消息可靠传输,...

     ***** MQ选型
     中小型公司建议RabbitMQ: 1 erlang语言天生具备高并发的特性，且管理界面方便; 2 数据量没那么大，应首选功能比较完备社区活跃的
     大型公司建议rocketMq kafka二选一: 1具备足够的资金搭建分布式环境，足够大的数据量 2 如果需要 实时处理/日志采集功能，首选kafka


     ***** 保证MQ是高可用-息队列的集群模式
     rcoketMQ: 多master模式、多master多slave异步复制模式、多master多slave同步双写模式

     Kafka: 集群中包含若干Producer（可以是web前端产生的Page View，或者是服务器日志，系统CPU、Memory等），
     若干broker（Kafka支持水平扩展，一般broker数量越多，集群吞吐率越高），若干Consumer Group，
     以及一个Zookeeper集群(通过Zookeeper管理集群配置)，选举leader，以及在Consumer Group发生变化时进行rebalance。
     Producer使用push模式将消息发布到broker，Consumer使用pull模式从broker订阅并消费消息。

     rabbitMQ: 普通集群和镜像集群模式


     ***** 保证消息不被重复消费-保证MQ的幂等性
     各种MQ造成重复消费的原因是类似的: 正常情况,消费者在完成消费后，会发送一个确认信息告知MQ该消息被消费了，然后MQ会将该消息从队列中删除
     只是不同的MQ发送的确认信息形式不同,如RabbitMQ是发送一个ACK确认消息，RocketMQ是返回一个CONSUME_SUCCESS成功标志，
     kafka每一个消息都有一个offset，消费消息后，消费者需要提交offset，让MQ知道已经消费过了。

     造成重复消费的原因: 网络传输等等故障，确认信息没有传送到MQ，导致MQ不知道已经消费过该消息并再次将该消息分发给消费者

     如何解决?针对业务场景回答
       (1)消息做数据库的insert操作，给个消息设置唯一主键，重复消费消费时，主键冲突可避免数据库出现脏数据
       (2)消息做redis的set的操作，不用解决，因为set操作本来就算幂等操作。无论set几次结果都是一样的
       (3)上面两种情况还不行，准备一个第三方介质保存消费记录。以redis为例，给每条消息分配一个全局id，把消费过的消息以<id,message>形式写入redis。消费者开始消费前，先在redis中查询有没消费记录即可



     ***** 保证消息的可靠性传输-保证消息不丢失
     每种MQ都要从三个角度来分析:生产者弄丢数据、消息队列弄丢数据、消费者弄丢数据

     RabbitMQ
     RabbitMQ生产者提供transaction和confirm模式来确保生产者不丢消息:
        transaction模式: 发送消息前开启事务(channel.txSelect())，然后发送消息，如果发送过程中出现异常，事物就会回滚(channel.txRollback())，
     如果发送成功则提交事物(channel.txCommit())。缺点是吞吐量下降了
        生产环境用confirm模式居多: 所有在该channel上面发布的消息都会被指派一个唯一的ID(从1开始)，一旦消息被投递到匹配的队列后，队列就会发送一个Ack(包含消息的唯一ID)给生产者，这就使生产者知道消息已经正确到达目的队列了.
        如果队列没能处理该消息，则会发送一个Nack消息给生产者，然后生产者进行重试。
     处理Ack和Nack的代码如下所示
     channel.addConfirmListener(new ConfirmListener() {  
                    @Override  
                    public void handleNack(long deliveryTag, boolean multiple) throws IOException {  
                        System.out.println("nack: deliveryTag = "+deliveryTag+" multiple: "+multiple);  
                    }  
                    @Override  
                    public void handleAck(long deliveryTag, boolean multiple) throws IOException {  
                        System.out.println("ack: deliveryTag = "+deliveryTag+" multiple: "+multiple);  
                    }  
                });  

     RabbitMQ队列确保不丢数据: 一般是开启持久化配置,即使队列挂了重启后也能恢复数据。持久化配置可以和confirm机制配合使用，队列收到消息持先久化到磁盘,再给生产者发送Ack信号。这样，如果消息持久化前队列挂了，那么生产者收不到Ack信号，会自动重发。
         那么如何持久化
        1、将队列的持久化标识durable设置为true,则代表是一个持久的队列
        2、发送消息的时候将deliveryMode=2

     RabbitMQ消费者丢数据一般是因为采用了自动确认消息模式。这种模式下，消费者会自动确认收到信息。收到确认后队列会立即将消息删除，此时如果消费者出现异常没有及时处理该消息，就会丢失该消息。
     解决方案，采用手动确认消息

     kafka
     kafka Replication的数据流向:
     Producer在发布消息到某个Partition时，先通过ZooKeeper找到该Partition的Leader，然后无论该Topic的Replication Factor为多少（也即该Partition有多少个Replica），Producer只将该消息发送到该Partition的Leader。Leader会将该消息写入其本地Log。每个Follower都从Leader中pull数据。

     针对上述情况，得出如下分析
     kafka生产者丢数据
     在kafka生产中，基本都有一个leader和多个follower。follower会去同步leader的信息。因此，为了避免生产者丢数据，做如下两点配置:
         1 在producer端设置acks=all,保证全部follower同步完成后才视为消息发送成功
         2 在producer端设置retries=MAX，写入失败后无限重试直至成功

     kafka队列丢数据
     follower数据还没同步完成，leader就挂了，这时zookeeper会将其他的follower切换为leader,导致那数据,针对这种情况做两个配置:
         replication.factor参数值必须大于1，即要求每个partition必须有至少2个副本
         min.insync.replicas参数值必须大于1，要求一个leader至少感知到有至少一个follower还跟自己保持联系
     这两个配置加上生产者的配置联合起来，基本可确保kafka不丢数据

     kafka消费者丢数据
     一般是自动提交了offset，然而消费者在处理过程中挂了。但是kafka队列由于收到消费者自动提交的offset认为消费成功
     offset：指的是kafka的topic中的每个消费组消费的下标。简单的来说就是一条消息对应一个offset下标，每次消费数据的时候如果提交offset，那么下次消费就从提交的offset+1处开始消费
     比如,一个topic中有100条数据，某个消费者消费了50条并且提交了offset，此时kafka服务端记录到该消费者提交的offset=49(offset从0开始)，下次消费的时候offset就从50开始消费
     解决方案很简单，改成手动提交即可。


     ***** 保证消息的顺序性
     将需要保持先后顺序的消息放到同一个消息队列中(kafka中就是partition,rabbitMq中就是queue)。然后只用一个消费者去消费该队列。










     Exchange分发消息时根据模式的不同 分发策略有区别,目前共四种类型：direct、fanout、topic、headers 。只说前三种模式。

     1.Direct模式

     消息中的路由键（routing key）如果和 Binding 中的 binding key 一致, 交换器就将消息发到对应的队列中。路由键与队列名完全匹配

     2.Topic模式

     topic 交换器通过模式匹配分配消息的路由键属性,将路由键和某个模式进行匹配,此时队列需要绑定到一个模式上。它将路由键和绑定键的字符串切分成单词,这些单词之间用点隔开。它同样也会识别两个通配符：符号“#”和符号“*”。#匹配0个或多个单词,*匹配一个单词。

     3.Fanout模式

     每个发到 fanout 类型交换器的消息都会分到所有绑定的队列上去。fanout 交换器不处理路由键,只是简单的将队列绑定到交换器上,每个发送到交换器的消息都会被转发到与该交换器绑定的所有队列上。很像子网广播,每台子网内的主机都获得了一份复制的消息。fanout 类型转发消息是最快的。

     */
}
