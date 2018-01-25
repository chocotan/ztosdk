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
    <version>0.0.1</version>
</dependency>
```
使用示例
```java
ZtoClientProperties account = new ZtoClientProperties("http://japi.zto.cn/",
 "your companyid","your key", 2000L);
ZtoApiClient client = new ZtoApiClient(account);
String response = client.postApi("commonOrder", "{}");
System.out.println(response);

```

> 注意 companyid 和 key 需要登录中通开放平台后到个人中心查看
> 注意 url 不是中通文档中的全部url，是去掉路径之后的
> postApi方法适用于中通新开放平台接口，http://japi.zto.cn/apiName

### spring-boot项目
添加以下maven依赖
```xml
<dependency>
    <groupId>io.loli.zto</groupId>
    <artifactId>ztosdk-springboot-starter</artifactId>
    <version>0.0.1</version>
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
```

### 中通开放平台旧接口
如果要调用的接口地址是`http://japi.zto.cn/zto/api_utf8/serviceName`，使用`ZtoApiClient.postService`方法
如果要调用的接口地址是`http://japi.zto.cn/gateway.do`，使用`ZtoApiClient.postMsgType`方法
