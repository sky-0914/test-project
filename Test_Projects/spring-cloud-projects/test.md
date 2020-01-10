##Eureka
```yaml 
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka                     #应用的主机名称
    # defaultZone: http://user:password123@localhost:8761/eureka/ #配置SpringSecurity
    register-with-eureka: false                                     #值为false意味着自身仅作为服务器，不作为客户端
    fetch-registry: false                                           #值为false意味着无需注册自身
    #  server:
    #    enable-self-preservation: false                            # 设为false，关闭自我保护
    #    eviction-interval-timer-in-ms: 4000                        # 清理间隔（单位毫秒，默认是60*1000）
    #    renewal-percent-threshold: 0.49                            # 如果您只想在demo / dev环境中部署，您可以将eureka.server.renewalPercentThreshold设置为0.49，因此当您单独启动Eureka服务器时，阈值将为0。
  instance:
    #    Eureka 的元数据
    metadata-map:
      zc-data: ZC de diannao                                        # 不会影响客户端
      zone: ABD                                                     # Eureka可以理解的元数据，可以影响客户端
    appname: AAAAA                                                  # 填坑 Swagger：配置和spring.application.name 冲突
  datacenter: cloud                                                 # 修改Eureka监控页面的System Status Data center
  environment: uat                                                  # 修改Eureka监控页面的System Status Environment
```

```java
//Spring Cloud Finchley及更高版本必须添加这一段，在Edgware以及更早的版本中无需这一步骤。
/**
 * Spring Cloud Finchley及更高版本，必须添加如下代码，部分关闭掉Spring Security
 * 的CSRF保护功能，否则应用无法正常注册！
 * ref: http://cloud.spring.io/spring-cloud-netflix/single/spring-cloud-netflix.html#_securing_the_eureka_server
 * @author zhouli
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().ignoringAntMatchers("/eureka/**");
    super.configure(http);
  }
}
```

##Ribbon
1. 内置负载均衡规则
负载均衡规则是Ribbon的核心，下面来看一下Ribbon内置的负载均衡规则。

- AvailabilityFilteringRule：过滤掉一直连接失败的被标记为circuit tripped的后端Server，并过滤掉那些高并发的后端Server或者使用一个AvailabilityPredicate来包含过滤server的逻辑，其实就就是检查status里记录的各个Server的运行状态；
- BestAvailableRule：选择一个最小的并发请求的Server，逐个考察Server，如果Server被tripped了，则跳过。
- RandomRule：随机选择一个Server；
- ResponseTimeWeightedRule：作用同WeightedResponseTimeRule，二者作用一样；
- RetryRule：对选定的负载均衡策略机上重试机制，在一个配置时间段内当选择Server不成功，则一直尝试使用subRule的方式选择一个可用的server；
- RoundRobinRule：轮询选择， 轮询index，选择index对应位置的Server；
- WeightedResponseTimeRule：根据响应时间加权，响应时间越长，权重越小，被选中的可能性越低；
- ZoneAvoidanceRule：复合判断Server所在区域的性能和Server的可用性选择Server；
- 如需自定义负载均衡规则，只需实现IRule 接口或继承AbstractLoadBalancerRule 、PredicateBasedRule即可 ，读者可参考RandomRule 、RoundRobinRule 、ZoneAvoidanceRule 等内置Rule编写自己的负载均衡规则。

Ribbon配置自定义【细粒度配置】
Ribbon可实现精确到目标服务的细粒度配置。例如A服务调用服务B，A服务调用C，可以针对B服务一套配置，针对C服务另一套配置。

方式1、代码配置方式
在Spring Cloud中，Ribbon的默认配置如下(格式是BeanType beanName: ClassName)：

- IClientConfig ribbonClientConfig: DefaultClientConfigImpl
- IRule ribbonRule: ZoneAvoidanceRule
- IPing ribbonPing: NoOpPing
- ServerList<Server> ribbonServerList: ConfigurationBasedServerList
- ServerListFilter<Server> ribbonServerListFilter: ZonePreferenceServerListFilter
- ILoadBalancer ribbonLoadBalancer: ZoneAwareLoadBalancer
- ServerListUpdater ribbonServerListUpdater: PollingServerListUpdater

##Feign
```yaml
logging:
  level:
    com.spring.cloud.feignclient.Server1Client: DEBUG   #Feign日志记录仅响应DEBUG级别。
feign:
  hystrix:
    enabled: true                                       #全局禁用或开启 feign默认hystrix是false不开启的
```
> * 一般来说，建议使用Feign，并杜绝使用RestTmplate。为什么用Feign相信不必啰嗦；可为什么要杜绝RestTemplate，那是因为在一个项目里，保持统一的编码风格乃至体验，是非常重要的。我个人的架构原则是尽量减少开发人员的选择，如果A能解决问题，就杜绝使用B——最佳实践永远只有一个！并且，共存带来的往往不是相得益彰，反而是歧义、错乱以及额外的学习成本、理解成本（笔者当年参与过一个同时使用Struts1 + Struts2 + Servlet的项目，可以想象一下学习成本有多高；笔者还参与一个一个使用Jackson + FastJson + json-lib + Gson的项目，可想而知操作JSON的代码有多混乱……80%的开发在骂娘中度过时光，并抨击别人使用他不熟悉的JSON操作库，后来被笔者统一成Jackson后，大家都安心干活了）！
> * 有人可能会对Feign的性能存在顾虑，笔者认为，Feign的性能虽然不那么优秀，但大部分场景下都是OK的——项目的性能瓶颈一般都不出在HTTP客户端上，而在于自身业务的处理！
> * 求同存异——上文虽说要杜绝RestTemplate，但事无绝对，你得根据具体情况具体分析——对于某些变态需求，在使用Feign很难实现或无法实现时，可考虑使用RestTemplate + Feign共存的方式……Spring Cloud官方也承认，无论Fegin怎么改进，其灵活性也无法比得上RestTemplate！但是，这么做之前请务必慎重，记住，共存带来的往往不是相得益彰，反而是歧义、错乱以及额外的学习成本、理解成本。

management.endpoint.health.show-details: always //显示所有健康状态

management.endpoints.web.exposure.include: ["hystrix-stream"]   //但是actuator/health就无法访问了

management.endpoints.web.exposure.include: "*"  //所以还可以选择全部放开。

Spring2.0特性全部在actuaton下面 management.endpoints.web.exposure.include这个是用来暴露 endpoints 的
springcloud 中使用 hystrix-dashboard 进行监控的时候启动报错”path”:”/actuator/hystrix.stream”,”status”:404,”error”:”Not Found”

---
## SpringCloud 2.0
spring cloud2 hystrix没有hystrix.stream路径解决方案

第一步：在启动类上引入
@EnableHystrix 或者 @EnableCircuitBreaker ，表示启用熔断器

第二步：在启动类中加入如下代码
```java
    @Bean
    public ServletRegistrationBean getServlet(){
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
        registrationBean.setLoadOnStartup(1);  //系统启动时加载顺序
        registrationBean.addUrlMappings("/hystrix.stream");//路径
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }
```

---
##Actuator健康检查
```yaml
# 显示所有健康状态
management:
  endpoint:
    health:
      show-details: always
  # Spring2.0特性全部在actuaton下面 management.endpoints.web.exposure.include这个是用来暴露 endpoints 的
  #  springcloud 中使用 hystrix-dashboard 进行监控的时候启动报错”path”:”/actuator/hystrix.stream”,”status”:404,”error”:”Not Found”
  endpoints:
    web:
      exposure:
        #        include: ["hystrix-stream"] #但是actuator/health就无法访问了，所以还可以选择全部放开。
        include: "*"
```
---
##Config 手动刷新与自动刷新
1. 在客户端添加Actuator依赖，/refresh是Actuator提供和暴露的断点
2. 在需要加载配置的类中添加@RefreshScope注解，最好不要与@Configuration注解同时使用
3. 调用/refresh这个端点去刷新配置

刷新多个节点或全部刷新
需要添加Spring Cloud Bus
---
##SidCecar 支持异构平台的微服务
---
## SpringCloud中一些访问端点
1. /actuator   //健康检查
2. /actuator/hystrix.stream //监控节点流
3. /hystrix   //Hystrix Dashboard断容器仪表盘 
4. /turbine.stream //将这个断点输入监控仪表盘，可以监控集群
5. /actuator/routes //Zuul路由的列表