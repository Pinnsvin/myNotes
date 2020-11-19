# shadowsock 配置

## 部署服务端

[GitHub Shadowsocks-使用说明](https://github.com/shadowsocks/shadowsocks/wiki/Shadowsocks-使用说明)

### Debian/ubuntu

```bash
apt-get install python-pip
pip install shadowsocks
```

### Centos

```bash
yum install python-setuptools && easy_install pip
pip install shadowsocks
```

### 配置文件

Create a config file ``/etc/shadowsocks.json``:

```json
{
    "server":"my_server_ip",
    "server_port":8388,
    "local_address": "127.0.0.1",
    "local_port":1080,
    "password":"mypassword",
    "timeout":300,
    "method":"aes-256-cfb",
    "fast_open": false
}
```

多用户:

```json
{
    "server":"my_server_ip",
    "local_address": "127.0.0.1",
    "local_port":1080,
    "port_password":{
        "8388":"mypassword",
        "8389":"mypassword"
    },
    "timeout":300,
    "method":"aes-256-cfb",
    "fast_open": false
}
```

### 使用

根据配置文件(后台运行)启动:

```bash
ssserver -c /etc/shadowsocks.json -d start
```

停止:

```bash
ssserver -d stop
```

检查日志:

```bash
sudo less /var/log/shadowsocks.log
```

## 客户端

* [Andorid](https://github.com/shadowsocks/shadowsocks-android)/[iOS](https://github.com/shadowsocks/shadowsocks-iOS/wiki/Help)
* [Windows](https://github.com/shadowsocks/shadowsocks-windows/releases)

### 配置

``~/.shadowsockClient.json``

```json
{
    "server":["server_ip", "127.0.0.1"],
    "server_port":port,
    "local_port":1080,
    "password":"pass",
    "timeout":60,
    "method":"aes-256-cfb",
    "local_address":"127.0.0.1",
    "fast_open":false
}
```

### 启动

```bash
sudo sslocal -c ~/.shadowsockClient.json -d start
```

## 防火墙相关

[参考](https://blog.csdn.net/yujicun/article/details/17419823)

查看防火墙:

```bash
iptables -L
```

查看端口信息:

```bash
nmap
```

开启端口(22):

```bash
iptables -A INPUT -p tcp --dport 22 -j ACCEPT
iptables -A OUTPUT -p tcp --sport 22 -j ACCEPT
```