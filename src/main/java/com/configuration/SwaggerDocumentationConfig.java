package com.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerDocumentationConfig {
  ApiInfo apiInfo() {
    return new  ApiInfoBuilder()
        .title("Crypto Prices Tracker")
        .description("A REST API app that continuously monitors the price of Bitcoin using a third-party API and alerts a given email when the price either goes above or below given limits.")
        .license("")
        .licenseUrl("http://unlicense.org")
        .termsOfServiceUrl("")
        .version("0.0.1")
        .contact(new Contact("", "", ""))
        .build();
  }

  @Bean
  public Docket customImplementation() {
    return new Docket(DocumentationType.OAS_30)
        .select()
        .paths(PathSelectors.any())
        .apis(RequestHandlerSelectors.basePackage("com"))
        .build()
        .directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
        .directModelSubstitute(java.time.OffsetDateTime.class, java.sql.Date.class)
        .apiInfo(apiInfo());
  }
}
