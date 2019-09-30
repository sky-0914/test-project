### Eureka 客户端配置
```yml
eureka:
  instance:
#    ip-address: #指定ip地址
    # 是否以IP注册到Eureka Server上，如果false则不是IP而是服务器名称
    # 但我设置了false，eureka主页仍显示192.168.100.16:client-microservice:8010
    preferIpAddress: true # 将IP注册到Eureka Server上，而如果不配置就是机器的主机名。默认false。应该始终设置为true。如果基于Docker等容器的部署，容器会生成一个随机的主机名，此时DNS不存在该名，无法解析 - John Carnell
    # 实例名。SpringCloud体系里的，服务实体向eureka注册时，注册名默认是“IP名:应用名:应用端口名”${spring.application.name}:${spring.cloud.client.ip-address}:${spring.application.instance_id:${random.int}}
    # 如果服务名，ip，端口都一致的话，eureka只显示一个服务
    instance-id: ${spring.cloud.client.hostname}:${spring.application.name}:${spring.cloud.client.ip-address}:${spring.application.instance_id:${random.int[1,9]}}-@project.version@
    # 服务续约的两个重要属性
    leaseRenewalIntervalInSeconds: 30 # 服务续约间隔时间。默认每隔30秒，客户端会向服务端发送心跳。见DiscoveryClient.initScheduledTasks
    leaseExpirationDurationInSeconds: 90 # 服务失效时间。缺省为90秒服务端接收不到客户端的心跳，则剔除该客户端服务实例。
    # 端点配置。若配置了context-path，actuator的监控端点会增加前缀，此时eureka也需要相应增加
    #status-page-url-path: ${server.servlet.context-path}/actuator/info
    #health-check-url-path: ${server.servlet.context-path}/actuator/health

    #    Eureka 的元数据
    metadata-map:
      zc-data: Current services are commodity services  # 不会影响客户端
      zone: ABD               # Eureka可以理解的元数据，可以影响客户端
#    appname: AAAAA            # 填坑 Swagger：配置和spring.application.name 冲突
  client:
    # eureka服务的位置，如配置错误，则：Cannot execute request on any known server
    # 详见：com.netflix.discovery.endpoint.EndpointUtils
    service-url:
      defaultZone: http://localhost:8761/eureka/ #应用的主机名称
    # 是否启用eureka客户端。默认true
    enabled: true # 本地调试时，若不想启动eureka，可配置false即可，而不需要注释掉@EnableDiscoveryClient这么麻烦。感谢永超，从他的书知道这个属性。
    # 支持registerWithEureka(John、周立)和register-with-eureka(翟永超)两种写法，eclipse的STS默认使用后者。
    # 基本所有配置使用横杠或者驼峰都可以，鼠标放在上面，eclipse都可以显示详细注解和默认值（如果有）。
    registerWithEureka: true # 默认true，因此也可省略。
    fetchRegistry: true # 默认true，此处可不配置。
    # 缓存清单更新时间，默认30秒。见EurekaClientConfigBean，其中DefaultEurekaClientConfig可不看（前者spring实现，后者Netflix实现）
    registry-fetch-interval-seconds: 30 # 如果想eureka server剔除服务后尽快在client体现，我觉得可缩短此时间。
    # 周立在Camden SR4（对应eureka-client.jar1.2.6）中说有该属性，但我在SR6（对应1.2.4）和SR4中都找不到；
    # 又查找了Brixton SR7（对应1.1.7，其实不光eureka-client，整个spring-cloud-netflix都是这个版本），也是没有。
    # 这是因为该属性IDE确实不能提示，但写法是正确的。作用是修改eureka的健康检查方式（心跳），改为用actuator，详见HealthCheckHandler HealthIndicator。
    # 周立写的不是太详细，可详见这博客：https://blog.csdn.net/xiao_jun_0820/article/details/77991963
    # 若配置healthcheck，需引入actuator。
    healthcheck:
      enabled: true # 我建议配置为true。心跳机制有个问题，如当客户端的数据库连接出现问题导致不可用时，心跳机制不能反映，但actuator的health可以。
# 显示所有健康状态
management:
  endpoint.health.show-details: always
  endpoints.web.exposure.include: "*"
```

### Mybatis 相关配置
```
@MapperScan("com.happyloves.zc.service.order") //在接口类上添加了@Mapper，在编译之后会生成相应的接口实现类
```
>   通用Mapper,专用代码生成器
```pom
<!-- 通用Mapper -->
<dependency>
    <groupId>tk.mybatis</groupId>
    <artifactId>mapper-spring-boot-starter</artifactId>
    <version>${version}</version>
</dependency>
```





