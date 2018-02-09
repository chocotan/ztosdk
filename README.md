## 中通开放平台SDK
这是一个调用中通开放平台接口的java SDK（非官方）

中通开放平台：http://zop.zto.com

接入中通开放平台，可以实现以下功能
1. 查询中通物流轨迹
2. 在线预约寄件
3. 获取电子面单号（电商、仓库发货打单）
其他见中通开放平台官网

## 使用方法

### 非spring-boot项目

添加以下maven依赖
```xml
<dependency>
    <groupId>io.loli.zto</groupId>
    <artifactId>ztosdk-core</artifactId>
    <version>0.0.4</version>
</dependency>
```
使用示例
```java
ZtoClientProperties ztoClientProperties = new ZtoClientProperties("http://japi.zto.cn/",
    "your companyid","your key",false , 2000L);
ZtoApiClient client = new ZtoApiClient(ztoClientProperties);
client.api().traceInterfaceNewTraces(new String[]{"12345678901"})
    .subscribe(resp->{
        if (resp.isFailure()) {
            System.out.println(resp.getCause().getMessage());
        } else {
            System.out.println(resp.get());
        }
    });
```

1. companyid 和 key 需要登录中通开放平台后到个人中心查看
2. 注意 url 不是中通文档中的全部url，是去掉路径之后的，一般情况下，生产环境为`http://japi.zto.cn`，测试环境为`http://58.40.16.125:9001/`，如果此处和中通开放平台上的文档上不同，请以中通开放平台的为准


### 中通开放平台的接口类型
中通开放平台中有三种接口类型，分别为 api，msgtype和service

区分方式:
1. msgtype: 接口地址是`http://japi.zto.cn/gateway.do`
2. api: 接口地址是`http://japi.zto.cn/apiName`
3. service: 接口地址`http://japi.zto.cn/zto/api_utf8/serviceName`


ZtoApiClient支持三种调用方式，见ZtoApiClient的三个方法msgtype、api和service

目前中通开放平台上的接口类型大多为`msgtype`

`ZtoApiClient.msgtype()`提供了部分接口的调用，分别是`电子面单-获取单号`、`快件轨迹-获取快件轨迹信息`、`快件轨迹-获取快件最新一条`、`电子面单-集包地大头笔接口`

如果需要调用其他接口或者是需要自己做一些额外的定制，可以参考如下栗子
```java
client.msgtype().post("这里是msgtype", dataObject,
              (Function<Object, Object>) o -> {
                  // 这里return的是请求参数中data字段的字符串
                  return JSON.toJSONString(o);
              }, (Function<String, Object>) s -> {
                  // 这里的s是http请求返回值字符串
                  return JSON.parseObject(s, YourResponseClass.class);
              });
```


### spring-boot项目
添加以下maven依赖
```xml
<dependency>
    <groupId>io.loli.zto</groupId>
    <artifactId>ztosdk-springboot-starter</artifactId>
    <version>0.0.3</version>
</dependency>
```

在启动类上添加 `@EnableZtoClient` 注解，然后注入使用
```java
@Autowired
ZtoApiClient client;
```

需要在application.yml中添加以下配置
```yaml
zto:
  url: http://japi.zto.cn/
  company-id: your-company-id
  key: your-key
  timeout: 2000
  async: false
```

### 日志
如果要查看基本的请求返回值耗时等日志，在application.yml里添加
```yaml
logging:
  level:
    io.loli.zto.client.ZtoLoggingInterceptor: debug
```
会输出类似下面的日志：
```yaml
url =http://japi.zto.cn/commonOrder ,header={} ,cost=100 ,code=200 ,body=>{{}} ,response={}
```

### RxJava
ZtoApiClient的请求方法都返回Observable对象，如果想直接使用返回值，使用Observable的blockingFirst方法的返回值

### 异常处理
ZtoApiClient的请求方法返回值都用Try包了一层，所以无需自行try-catch

```java
Try<YourResponseClass> respTry = respObservable.blockingFirst();
if (respTry.isSuccess()) {
    // 成功时候的处理
} else {
    Throwable throwable = resp.getCause();
    // 异常时候的处理
}
```
在出现异常时，如果要查看具体的请求日志，请调整日志级别（见上文）

### 异步
默认为同步调用，如果想使用异步，可以按照以下方式配置

```yaml
zto.async: true
```
异步调用时就别用Observable的blockingFirst方法了，直接subscribe吧~

## LICENSE
本项目是MIT协议，请放心fork和使用
