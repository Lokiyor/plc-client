package com.lokiy.iot.protocol.plc.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author Lokiy
 * @Date 2021/7/19 17:13
 * @Description 接口配置
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Web Interface")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lokiy.iot.protocol.plc.client.controller"))
                .paths(PathSelectors.any())
                .build();
    }


    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("PLC客户端")
                .description("PLC客户端接口文档")
                .version("0.0.1")
                .contact(new Contact("lokiy", "http://www.lokiy66.com","ljy88808@gmail.com"))
                .build();
    }
}
