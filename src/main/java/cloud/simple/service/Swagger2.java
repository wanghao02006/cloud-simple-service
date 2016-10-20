package cloud.simple.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.ClientCredentialsGrant;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.ImplicitGrant;
import springfox.documentation.service.LoginEndpoint;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static com.google.common.collect.Lists.*;

@Configuration
@EnableSwagger2
public class Swagger2 {

	@Bean
	public Docket createRestApi() {
		// @formatter:off
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select().apis(RequestHandlerSelectors.basePackage("cloud")).paths(PathSelectors.any())
				.build()
				.pathMapping("/")
//				.globalOperationParameters(
//						newArrayList(new ParameterBuilder().name("authKey").description("授权key")
//								.modelRef(new ModelRef("string")).parameterType("header").required(true).build()))
				.securitySchemes(securitySchema())
//				.securityContexts(securityContext())
				;
		// @formatter:on
	}

	public static final String schemaImplicit = "oauth2SchemaImplicit";
	public static final String schemaClientCrendential = "oauth2SchemaClientCrendential";
	public static final String authorizationScopeRead = "read";
	public static final String authorizationScopeReadDesc = "read Everything";
	public static final String authorizationScopeWrite = "write";
	public static final String authorizationScopeWriteDesc = "write Everything";

	private List<OAuth> securitySchema() {
		AuthorizationScope readScope = new AuthorizationScope("1:"+authorizationScopeRead, authorizationScopeReadDesc);
		AuthorizationScope writeScope = new AuthorizationScope("1:"+authorizationScopeWrite, authorizationScopeWriteDesc);
		LoginEndpoint loginEndpoint = new LoginEndpoint("http://localhost:8080/oauth/authorize");
		GrantType implictGrantType = new ImplicitGrant(loginEndpoint, "access_token");
		OAuth implicitAuth = new OAuth(schemaImplicit, 
//				new ArrayList<AuthorizationScope>(),
				newArrayList(readScope, writeScope), 
				newArrayList(implictGrantType));
		
		readScope = new AuthorizationScope("2:"+authorizationScopeRead, authorizationScopeReadDesc);
		writeScope = new AuthorizationScope("2:"+authorizationScopeWrite, authorizationScopeWriteDesc);
		GrantType clientCredentialsGrant = new ClientCredentialsGrant("http://localhost:8080/oauth/token");
		OAuth clientCredentialAuth = new OAuth(schemaClientCrendential, 
//				new ArrayList<AuthorizationScope>(),
				newArrayList(readScope, writeScope),
				newArrayList(clientCredentialsGrant));
		return newArrayList(clientCredentialAuth,implicitAuth);
	}

	private List<SecurityContext> securityContext() {
		SecurityContext a = SecurityContext.builder()
				.securityReferences(defaultAuth(schemaClientCrendential))
				.forPaths(PathSelectors.ant("/user/conf")).build();
//		SecurityContext b = SecurityContext.builder().securityReferences(defaultAuth(schemaClientCrendential))
//				.forPaths(PathSelectors.any()).build();
		return newArrayList(a);
	}

	private List<SecurityReference> defaultAuth(String schema) {
		AuthorizationScope readScope = new AuthorizationScope("2:"+authorizationScopeRead, authorizationScopeReadDesc);
		AuthorizationScope writeScope = new AuthorizationScope("2:"+authorizationScopeWrite, authorizationScopeWriteDesc);
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[2];
		authorizationScopes[0] = readScope;
		authorizationScopes[1] = writeScope;
		return newArrayList(new SecurityReference(schema, new AuthorizationScope[0]));
	}

	@Bean
	public SecurityConfiguration securityInfo() {
		return new SecurityConfiguration("tonr3", "secret", "realm", "appname", "", ApiKeyVehicle.HEADER, "api_key", ",");
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("User-Service的RESTful APIs").description("更多Spring Boot相关文章请关注：http://blog.didispace.com/")
				.termsOfServiceUrl("http://blog.didispace.com/").contact(new Contact("pipa", "www", "pipa@163.com")).version("1.0.1")
				.build();
	}

}
