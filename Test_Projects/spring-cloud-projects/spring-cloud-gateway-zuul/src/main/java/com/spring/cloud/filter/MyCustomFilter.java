package com.spring.cloud.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * @Author: 赵小超
 * @Date: 2019/1/5 14:15
 * @Description:
 */
@Slf4j
@Component
public class MyCustomFilter extends ZuulFilter {

    /**
     * 过滤器类型
     * public static final String PRE_TYPE = "pre";
     * public static final String ROUTE_TYPE = "route";
     * public static final String POST_TYPE = "post";
     * public static final String ERROR_TYPE = "error";
     * 过滤器类型与请求生命周期
     * Zuul大部分功能都是通过过滤器来实现的。Zuul中定义了四种标准过滤器类型，这些过滤器类型对应于请求的典型生命周期。
     * <p>
     * (1) PRE：这种过滤器在请求被路由之前调用。我们可利用这种过滤器实现身份验证、在集群中选择请求的微服务、记录调试信息等。
     * <p>
     * (2) ROUTING：这种过滤器将请求路由到微服务。这种过滤器用于构建发送给微服务的请求，并使用Apache HttpClient或Netfilx Ribbon请求微服务。
     * <p>
     * (3) POST：这种过滤器在路由到微服务以后执行。这种过滤器可用来为响应添加标准的HTTP Header、收集统计信息和指标、将响应从微服务发送给客户端等。
     * <p>
     * (4) ERROR：在其他阶段发生错误时执行该过滤器。
     * <p>
     * 除了默认的过滤器类型，Zuul还允许我们创建自定义的过滤器类型。例如，我们可以定制一种STATIC类型的过滤器，直接在Zuul中生成响应，而不将请求转发到后端的微服务。
     * <p>
     * Zuul请求的生命周期如图8-5所示，该图详细描述了各种类型的过滤器的执行顺序。
     *
     * @return
     */
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    /**
     * 过滤器优先级-执行顺序
     * 数字越大越靠后执行
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     * 判断是否用这个过滤器
     * false 不用
     * true  用
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return false;
    }

    /**
     * 过滤器具体业务逻辑
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {

        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();

        String remoteHost = request.getRemoteHost();
        log.info("请求地址：" + remoteHost);
        return null;
    }
}
