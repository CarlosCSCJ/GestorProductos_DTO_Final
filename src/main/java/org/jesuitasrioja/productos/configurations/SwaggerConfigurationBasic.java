package org.jesuitasrioja.productos.configurations;

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
public class SwaggerConfigurationBasic {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("org.jesuitasrioja.productos")).paths(PathSelectors.any()).build()
				.apiInfo(apiInfo());
	}

	@Bean
	public ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("API rest curso PSP")
				.description("Esta es la descripcion de nuestro proyecto").version("0.1")
				.contact(new Contact("Carlos", "url", "csaenzadan@jesuitasformacion.com")).build();
	}
}
