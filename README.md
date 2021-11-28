## 项目说明
随机图api，接入第三方云存储，获取云存储的图片url并随机返回。
目前只对接了阿里oss。
可作为 halo-Sakura主题 的随机图api。
## TODO


## 使用方法
建议使用docker部署

1. 准备外部持久化目录
```shell
mkdir -p /opt/randomImageApi/config/
mkdir -p /opt/randomImageApi/logs/

```
2. 提供自定义yaml配置
```shell
cp ./application.yaml-template /opt/randomImageApi/config/application.yaml

```

3. 启动
/application/config 用于传递yaml配置文件，需持久化。
/application/logs 用来存放日志，可不挂载出来。
```shell
docker run -it -d --name randomImageApi \
-p 32919:22909 \
-v /opt/randomImageApi/config:/application/config \
-v /opt/randomImageApi/logs:/application/logs \
--restart=always \
linshen/random-image-api

```
4. 查看日志
```shell
docker logs -f randomImageApi
```
5. 更新镜像
```shell
docker rm -f randomImageApi 
docker pull linshen/random-image-api
# 然后再次执行启动命令

```
6. 更改配置
修改配置文件，然后重启即可
```shell
docker restart randomImageApi

```
## 配置说明

