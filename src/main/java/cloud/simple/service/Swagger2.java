package cloud.simple.service;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static com.google.common.collect.Lists.*;

@Configuration
@EnableSwagger2
public class Swagger2 {

	@Bean
	public Docket createRestApi() {
		// @formatter:off
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("cloud"))
				.paths(PathSelectors.any())
				.build()
				.pathMapping("/")
				.globalOperationParameters(
						newArrayList(new ParameterBuilder().name("authKey").description("授权key")
								.modelRef(new ModelRef("string")).parameterType("header").required(true).build()));
		// @formatter:on
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("User-Service的RESTful APIs")
				.description("更多Spring Boot相关文章请关注：http://blog.didispace.com/")
				.termsOfServiceUrl("http://blog.didispace.com/").contact(new Contact("pipa", "www", "pipa@163.com"))
				.version("1.0.1").build();
	}

}
