package com.happyloves.zc.service.common.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author 赵小胖
 * @Date 2019/9/30 18:59
 * @Description:
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {

/*
        //可以添加多个header或参数
        ParameterBuilder aParameterBuilder = new ParameterBuilder();
        aParameterBuilder
                //参数类型支持header, cookie, body, query etc
                .parameterType("header")
                //参数名
                .name("user-token")
                //默认值
                .defaultValue("t122222")
                .description("用户登录凭证")
                //指定参数值的类型
                .modelRef(new ModelRef("string"))
                //非必需，这里是全局配置
                .required(false).build();
        List<Parameter> aParameters = new ArrayList<>();
        aParameters.add(aParameterBuilder.build());
*/

        return new Docket(DocumentationType.SWAGGER_2)
//        return new Docket(DocumentationType.SPRING_WEB)
                .apiInfo(apiInfo())
                .pathMapping("/")
                .select()// 选择那些路径和api会生成document
                .apis(RequestHandlerSelectors.any())// 对所有api进行监控
                // 不显示错误的接口地址
                .paths(Predicates.not(PathSelectors.regex("/error.*")))// 错误error路径不监控
                .paths(Predicates.not(PathSelectors.regex("/actuator.*")))// 错误error路径不监控
                .paths(PathSelectors.regex("/.*"))// 对根下所有路径进行监控
                .paths(PathSelectors.any())   // 对所有路径进行监控
                // 自行修改为自己的包路径
//                .apis(RequestHandlerSelectors.basePackage("com.happyloves.zc.service.account.api"))
                .build()
//                .globalOperationParameters(aParameters)
                .enable(true);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API接口")
                .description("API接口文档")
                //服务条款网址
//                .termsOfServiceUrl("https://www.google.com")
                .version("1.0")
//                .contact(new Contact("啦啦啦", "url", "email"))
                .build();
    }
}
