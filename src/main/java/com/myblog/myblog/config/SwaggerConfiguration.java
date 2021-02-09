package com.myblog.myblog.config;

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



@Configuration
@EnableSwagger2 
public class SwaggerConfiguration {

 @Bean
 public Docket createRestApi() {
     return new Docket(DocumentationType.SWAGGER_2) 
             .apiInfo(this.apiInfo()) 
             .select()
             .apis(RequestHandlerSelectors.basePackage("com.myblog.myblog.controller.rest"))
             .paths(PathSelectors.any())
             .build();
 }

 /**
  * 创建 API 信息
  */
 private ApiInfo apiInfo() {
     return new ApiInfoBuilder()
             .title("Test Api document")
             .description("This is for test webflux api but JPA does not support.")
             .version("1.0.0") // 版本号
             .contact(new Contact("YuShin", "http://github.io/pingodgs", "pingdogs@gmail.com")) 
             .build();
 }

}