package cloud.simple.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cloud.simple.service.conf.DataSourceProperties;

@SpringBootApplication
@RestController
@EnableConfigurationProperties(DataSourceProperties.class)
public class UserServiceApplication {

	@Value("${name}")
	String name = "World";

	@Value("${age}")
	Integer age = 0;

	@Autowired
	private DataSourceProperties dataSourceProperties;

	@RequestMapping("/")
	public String home() {
		return "name=" + name + " ,age=" + age + " ,pass=" + dataSourceProperties.getPassword() + ",url=" + dataSourceProperties.getUrl();
	}

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
