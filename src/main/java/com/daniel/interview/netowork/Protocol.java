package com.daniel.interview.netowork;

/**
 * Daniel on 2018/10/17.
 */
public class Protocol {
    /**
     OSI网络七层模型
         应用层 定义用于在网络中进行通信和传输数据的接口
         表示层 定义不同的系统中数据的传输格式 编码和解码规范等
         会话层 管理用户的会话 控制用户间逻辑连接的建立和中断
         传输层 管理网络中端到端的数据传输
         网络层 定义网络设备间如何传输数据
         链路层 将网络层的数据包封装成数据帧 便于物理层传输
         物理层 传输二进制数据
     实际应用中 表示层和会话层和应用层合并了 HTTP是应用层协议 TCP是传输层协议

     TCP/IP是个协议组 分为:
     应用层 FTP HTTP TELNET SMTP DNS
     传输层 TCP UDP
     网络层 IP ICMP ARP RARP BOOTP
     
     
     HTTP协议建立在请求/响应模型上 传输的数据都是未加密的 请求/响应建立一个新的TCP链接
     建立一次TCP链接需要3次握手
        1 client发起连接请求 向服务器发送syn包(syn=j) 并进入SYN_SENT状态 等待服务器确认；SYN：同步序列编号(Synchronize Sequence Numbers)

        2 服务器收到syn包 确认客户的SYN(ack=j+1) 同时服务器也发送一个SYN包(syn=k) 即SYN+ACK包 然后服务器进入SYN_RECV状态；ACK:确认字符(Acknowledgement)

        3 client收到服务器的SYN+ACK包 向服务器发送ACK(ack=k+1)确认包 ACK(ack=k+1)发送完毕 client和服务器进入ESTABLISHED(TCP连接成功)状态 完成三次握手


     Https Hyper Text Transfer Protocol over Secure Socket Layer 安全的超文本传输协议 
        SSL(Secure Sockets Layer)协议用于对Http协议传输的数据进行加密

        SSL协议用对称加密和非对称加密(公钥加密) 在建立传输链路时 SSL首先对 对称加密的密钥使用公钥进行非对称加密 链路建立好之后 SSL对传输内容使用对称加密

        Https双向认证:
         1 client向server发送SSL协议版本号 加密算法种类等信息

         2 server给client返回SSL协议版本号 加密算法种类等信息 同时也返回server的证书 即公钥证书[serverPublicKey]

         3 client使用server返回的信息验证服务器的合法性 包括:
                 证书是否过期
                 发行服务器证书的CA是否可靠
                 返回的公能否正确解开返回证书中的数字签名
                 服务器证书上的域名是否和服务器的实际域名相匹配
         验证通过后 将继续进行通信 否则终止通信

         4 server要求client发送client的证书 client将自己的证书发送至server

         5 server验证client的证书 通过验证后 获得client的公钥[clientPublicKey]

         6 client向server发送client能支持的对称加密方案 供服务器端进行选择

         7 server在client提供的加密方案中选择加密程度最高的加密方式

         8 server用5中获取的client的[clientPublicKey]对加密方案进行加密 并返回给client

         9 client收到server返回的加密方案密文后 用client的私钥解密获取具体加密方式 然后产生该加密方式的随机码作为加密过程中的密钥[secretKey]
         client用2中获取到的server的[serverPublicKey]对[secretKey]非对称加密生成[encryptSecretKey],client把[encryptSecretKey]发送给server

         10 server收到client发送的[encryptSecretKey] 用server的私钥[serverPrivateKey]解密 获取对称加密的密钥[secretKey]
         在接下来的会话中 server和client都使用[secretKey]进行对称加密以保证通信过程中信息的安全

     
     对称加密
     速度高 可加密内容较大 用来加密会话过程中的消息

     公钥加密
     加密速度较慢 但能提供更好的身份认证技术 用来加密对称加密的密钥

     
    UDP的差别
                    TCP         UDP
     是否连接     面向连接        面向非连接
     传输可靠性   可靠            不可靠
     应用场合     传输大量数据     少量数据
     速度         慢             快

     */
}
