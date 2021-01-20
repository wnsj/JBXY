package com.jiubo.project.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * @desc:Swagger配置类
 * @date: 2019-09-10 08:31
 * @author: dx
 * @version: 1.0
 */
@Configuration
//开启Swagger2
@EnableSwagger2
@EnableSwaggerBootstrapUI
//@Profile("dev")
public class SwaggerConfig {

    //配置要扫描接口的方式
    //指定要扫描的包
    //RequestHandlerSelectors.basePackage("com.jiubo.sam.action");
    //扫描全部
    //RequestHandlerSelectors.any();
    //都不扫描
    //RequestHandlerSelectors.none();
    //扫描类上的注解
    //RequestHandlerSelectors.withClassAnnotation(RestController.class);
    //扫描方法上的注解
    //RequestHandlerSelectors.withMethodAnnotation(PostMapping.class);
    //配置Swagger2
    @Bean
    public Docket docket(Environment environment) {
        //设置要使用Swagger的环境（例如：只在测试环境中开启）
        Profiles profiles = Profiles.of("dev");
        //判断自己是否在设置的环境中
        boolean flag = environment.acceptsProfiles(profiles);
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .pathMapping("/")
                .groupName("九博学院 API")//设置API文档分组名（如：需要多个分组则新建多个Docket）
//                .enable(flag)//是否启用Swagger2（false：不启用）
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jiubo.project.action")) //配置要扫描接口的方式
                //.paths(PathSelectors.ant("com/jiubo/sam/action/**))//过滤路径（只扫描action下的action接口）
                .build();
        return docket;
    }

    //文档基础信息
    private ApiInfo apiInfo() {
        //作者信息
        Contact contact = new Contact("jiubo", "http://xxx.com", "xxxxx@qq.com");
        //文档基础信息
        ApiInfo apiInfo = new ApiInfo(
                "九博学院 API文档",
                "九博学院 接口文档",
                "1.0",
                "urn:tos",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
        return apiInfo;
    }
}
