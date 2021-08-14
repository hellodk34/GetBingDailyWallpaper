# GetBingDailyWallpaper

获取每日 Bing 图片，一个获取每日 Bing 图片的 API

# API 列表

- https://bingwallpaper.hellodk.com/image # 获取图片本身
- https://bingwallpaper.hellodk.com/url # 获取图片 url
- https://bingwallpaper.hellodk.com/copyright # 获取图片 copyright 信息
- https://bingwallpaper.hellodk.com/copyrightlink # 获取图片 copyright 直链
- https://bingwallpaper.hellodk.com/info # 获取全部信息 json 格式返回

# CSS background image

可以借助获取图片本身的 API 来设置 html 文档的 `background-image` 属性

```css
background-image: url(https://bingwallpaper.hellodk.com/image);
height: 100%;
background-position: center center;
background-repeat: no-repeat;
background-size: cover;
```

# Demo

https://hellodk.com

# 创建自己的 API

下载最新版 `jar` 包（比如 repo 中的`getbingdailywallpaper-0.0.2-SNAPSHOT.jar`）放在 Linux 服务器任意路径下，在该路径下创建文件 `run.sh`，输入下面内容保存退出

```
#!/bin/bash
nohup /usr/local/java/jdk1.8/bin/java -jar ./getbingdailywallpaper-0.0.1-SNAPSHOT.jar > ./getbingdailywallpaper.log 2>&1 &
```

添加可执行权限

```
# chmod +x ./run.sh
```

执行 `sh run.sh`

此时应用已经起来。下面给一个 nginx https & reverse proxy config example

```
server {
        listen 443 ssl http2;
        listen [::]:443 ssl http2;

        server_name bingwallpaper.hellodk.com;

        # SSL
        ssl_certificate /etc/letsencrypt/live/hellodk.com/fullchain.pem;
        ssl_certificate_key /etc/letsencrypt/live/hellodk.com/privkey.pem;
        ssl_trusted_certificate /etc/letsencrypt/live/hellodk.com/chain.pem;


        # logging
        access_log /var/log/nginx/bingwallpaper.access.log;
        error_log /var/log/nginx/bingwallpaper.error.log warn;

        # reverse proxy
        location / {
                proxy_pass http://127.0.0.1:8011;
        }
}

# HTTP redirect
server {
        listen 80;
        listen [::]:80;

        server_name bingwallpaper.hellodk.com;

        location / {
                return 301 https://bingwallpaper.hellodk.com$request_uri;
        }
}
```

应用默认运行端口 8011，如果发现无法使用自己创建的 API，优先查看防火墙是否放行 8011 端口。

拿 centOS7 举例

查看当前服务器已放行哪些端口

```
# firewall-cmd --zone=public --list-ports
```

放行 8011 tcp 端口

```
# firewall-cmd --zone=public --add-port=8011/tcp --permanent
```

重载防火墙配置

```
# firewall-cmd --reload
```

# 使用 docker image 创建自己的 API

# Usage

## pull this image

```
docker pull dko0/bingwallpaper:latest
```

## run it as a container

```
docker run -d --name bingwallpaper -p 8011:8011 --restart unless-stopped dko0/bingwallpaper:latest
```

## fetch today's bing wallpaper

access http://YOUR_IP:8011/image

## other API

- image original url: http://YOUR_IP:8011/url
- image copyright: http://YOUR_IP:8011/copyright
- image copyrighturl: http://YOUR_IP:8011/copyrighturl
- image full info(in a json format): http://YOUR_IP:8011/info
