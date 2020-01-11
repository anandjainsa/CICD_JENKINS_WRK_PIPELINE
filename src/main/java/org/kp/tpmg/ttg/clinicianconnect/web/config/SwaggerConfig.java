package org.kp.tpmg.ttg.clinicianconnect.web.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.service.Contact;


@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	 public static final Contact DEFAULT_CONTACT = new Contact(
		      "Prokarma", "http://prokarma.com", "bchandan@prokarma.com");

	@Bean
	  public Docket oncallServicesApiConfig() {
		return new Docket(DocumentationType.SWAGGER_2)
	        .select()
	        .paths(PathSelectors.ant("/oncallController/*"))
	        .apis(RequestHandlerSelectors.basePackage("org.kp.tpmg.ttg.clinicianconnect"))
	        .build()
	        .apiInfo(apiDetails());
	  }
	
	private ApiInfo apiDetails() {
		return new ApiInfo(
				"OncallServices API",
				"OncallServices API Description",
				"1.0",
				"free to use",
				DEFAULT_CONTACT,
				"API Licence",
				"http://www.in.kpmg.com",
				Collections.emptyList());
	}
}
