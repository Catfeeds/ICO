package com.tongwii.ico.configurer;

import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;
import java.util.function.Predicate;

/**
 * Swagger2 api doc config
 *
 * @author Zeral
 * @date 2017-09-06
 */
@Configuration
@EnableSwagger2
public class ApiDocConfig {

    /** header key **/
    @Value( "${jwt.header}" )
    private String  tokenHeaderKey;

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select().apis(RequestHandlerSelectors.basePackage("com.tongwii.ico.controller"))
                .paths(PathSelectors.any())
                .build()
                .enable(true)
                .apiInfo(apiInfo())
                .securityContexts(Lists.newArrayList(securityContext()))
                .securitySchemes(Lists.newArrayList(apiKey()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("ICO 项目 API 接口文档")
                .description("ICO 项目 API 接口文档")
                .contact(new Contact("Zeral", "https://github.com/Zeral-Zhang/", "zeralzhang@gmail.com"))
                .termsOfServiceUrl("http://www.tongwii.com")
                .version("1.0")
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey(tokenHeaderKey, "jwt-token", "header");
    }

    @Bean
    SecurityConfiguration security() {
        return new SecurityConfiguration(
                null,
                null,
                null, // realm Needed for authenticate button to work
                null, // appName Needed for authenticate button to work
                null,// apiKeyValue
                ApiKeyVehicle.HEADER,
                tokenHeaderKey, //apiKeyName
                ",");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(userAuth())
                .forPaths(PathSelectors.regex("/user/*"))
                .securityReferences(adminAuth())
                .forPaths(Predicates.in(Lists.newArrayList("/project/add", "/project//{id}/inputTokenMoney", "/project/{id}/outputTokenMoney", "/project/{id}/createUser"
                        , "/project/{id}", "/userProjectInvestRecord", "/userProjectInvestRecord/project/{projectId}")))
                .build();
    }

    List<SecurityReference> userAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("user", "access by user role");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(new SecurityReference(tokenHeaderKey, authorizationScopes));
    }

    List<SecurityReference> adminAuth() {
        AuthorizationScope adminScope = new AuthorizationScope("ADMIN", "access by admin role");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = adminScope;
        return Lists.newArrayList(new SecurityReference(tokenHeaderKey, authorizationScopes));
    }

}
