package com.daniel.interview.nginx;

/**
 * Daniel on 2018/11/30.
 */
public class LoadBalance {
    /**
     常用到的命令如下：
     nginx -s stop ：快速关闭Nginx，可能不保存相关信息，并迅速终止web服务。

     nginx -s quit ：平稳关闭Nginx，保存相关信息，有安排的结束web服务。

     nginx -s reload ：因改变了Nginx相关配置，需要重新加载配置而重载。

     nginx -s reopen ：重新打开日志文件。

     nginx -c filename ：为 Nginx 指定一个配置文件，来代替缺省的。

     nginx -t ：不运行，而仅仅测试配置文件。nginx 将检查配置文件的语法的正确性，并尝试打开配置文件中所引用到的文件。

     nginx -v：显示 nginx 的版本。

     nginx -V：显示 nginx 的版本，编译器版本和配置参数。


     负载均衡配置
     假设这样一个应用场景：将应用部署在 192.168.1.11:80、192.168.1.12:80、192.168.1.13:80 三台linux环境的服务器上。
     网站域名叫 www.javastack.cn，公网IP为 192.168.1.11。在公网IP所在的服务器上部署 nginx，对所有请求做负载均衡处理

     http {
     #设定mime类型,类型由mime.type文件定义
     include       /etc/nginx/mime.types;
     default_type  application/octet-stream;
     #设定日志格式
     access_log    /var/log/nginx/access.log;

     #设定负载均衡的服务器列表
     upstream load_balance_server {
     #weigth参数表示权值，权值越高被分配到的几率越大
     server 192.168.1.11:80   weight=5;
     server 192.168.1.12:80   weight=1;
     server 192.168.1.13:80   weight=6;
     }

     #HTTP服务器
     server {
     #侦听80端口
     listen       80;

     #定义使用www.xx.com访问
     server_name  www.javastack.cn;

     #对所有请求进行负载均衡请求
     location / {
     root        /root;                 #定义服务器的默认网站根目录位置
     index       index.html index.htm;  #定义首页索引文件的名称
     proxy_pass  http://load_balance_server ;#请求转向load_balance_server 定义的服务器列表

     #以下是一些反向代理的配置(可选择性配置)
     #proxy_redirect off;
     proxy_set_header Host $host;
     proxy_set_header X-Real-IP $remote_addr;
     #后端的Web服务器可以通过X-Forwarded-For获取用户真实IP
     proxy_set_header X-Forwarded-For $remote_addr;
     proxy_connect_timeout 90;          #nginx跟后端服务器连接超时时间(代理连接超时)
     proxy_send_timeout 90;             #后端服务器数据回传时间(代理发送超时)
     proxy_read_timeout 90;             #连接成功后，后端服务器响应时间(代理接收超时)
     proxy_buffer_size 4k;              #设置代理服务器（nginx）保存用户头信息的缓冲区大小
     proxy_buffers 4 32k;               #proxy_buffers缓冲区，网页平均在32k以下的话，这样设置
     proxy_busy_buffers_size 64k;       #高负荷下缓冲大小（proxy_buffers*2）
     proxy_temp_file_write_size 64k;    #设定缓存文件夹大小，大于这个值，将从upstream服务器传

     client_max_body_size 10m;          #允许客户端请求的最大单文件字节数
     client_body_buffer_size 128k;      #缓冲区代理缓冲用户端请求的最大字节数
     }
     }
     }


     多个webapp的配置
     当一个网站功能越来越丰富时，往往需要将一些功能相对独立的模块剥离出来，这样会有多个 webapp
     举个例子：假如 www.javastack.cn 站点有好几个webapp，finance（金融）、product（产品）、admin（用户中心）。访问这些应用的方式通过上下文(context)来进行区分:
     www.javastack.cn/finance/
     www.javastack.cnproduct/
     www.javastack.cn/admin/

     如果在一台服务器上同时启动这3个 webapp 应用，三个应用需要分别绑定不同的端口号。用户在实际访问 www.javastack.cn 站点时，访问不同 webapp 需要再次用反向代理来做处理
     http {
     #此处省略一些基本配置

     upstream product_server{
     server www.javastack.cn:8081;
     }

     upstream admin_server{
     server www.javastack.cn:8082;
     }

     upstream finance_server{
     server www.javastack.cn:8083;
     }

     server {
     #此处省略一些基本配置
     #默认指向product的server
     location / {
     proxy_pass http://product_server;
     }

     location /product/{
     proxy_pass http://product_server;
     }

     location /admin/ {
     proxy_pass http://admin_server;
     }

     location /finance/ {
     proxy_pass http://finance_server;
     }
     }
     }



     静态站点配置
     如果所有的静态资源都放在了 /app/dist 目录下，我们只需要在 nginx.conf 中指定首页以及这个站点的 host 即可
     http {
     include       mime.types;
     default_type  application/octet-stream;
     sendfile        on;
     keepalive_timeout  65;

     gzip on;
     gzip_types text/plain application/x-javascript text/css application/xml text/javascript application/javascript image/jpeg image/gif image/png;
     gzip_vary on;

     server {
     listen       80;
     server_name  static.zp.cn;

     location / {
     root /app/dist;
     index index.html;
     #转发任何请求到 index.html
     }
     }
     }


     跨域解决方案
     举例：www.javastack.cn 网站是由一个前端 app ，一个后端 app 组成的。前端端口号为 9000， 后端端口号为 8080。

     前端和后端如果使用 http 进行交互时，请求会被拒绝，因为存在跨域问题。来看看，nginx 是怎么解决的吧：

     1 在 enable-cors.conf 文件中设置 cors ：
     ......

     2 在服务器中 include enable-cors.conf 来引入跨域配置：
     upstream front_server{
     server www.javastack.cn:9000;
     }
     upstream api_server{
     server www.javastack.cn:8080;
     }

     server {
     listen       80;
     server_name  www.javastack.cn;

     location ~ ^/api/ {
     include enable-cors.conf;
     proxy_pass http://api_server;
     rewrite "^/api/(.*)$" /$1 break;
     }

     location ~ ^/ {
     proxy_pass http://front_server;
     }
     }

     */

}
