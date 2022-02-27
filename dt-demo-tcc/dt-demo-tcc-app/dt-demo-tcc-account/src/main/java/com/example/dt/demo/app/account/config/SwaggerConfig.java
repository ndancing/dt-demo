package com.example.dt.demo.app.account.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;

@Configuration
@EnableSwagger2
@SuppressWarnings("all")
public class SwaggerConfig {

    private static final String VERSION = "1.0.0";

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Distributed Transation Example Order API")
                .description("Distributed Transation Example Order API")
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .termsOfServiceUrl("")
                .version(VERSION)
                .contact(new Contact("user", "", ""))
                .build();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .build().pathMapping("/").directModelSubstitute(LocalDate.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class).useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, Lists.newArrayList(new ResponseMessageBuilder().code(500).message("500 message")
                        .responseModel(new ModelRef("Error")).build()));
    }

}
