package cloud.simple.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.session.data.redis.RedisFlushMode;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import cloud.simple.service.conf.DataSourceProperties;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@EnableConfigurationProperties(DataSourceProperties.class)
// 1分钟后session失效
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60, redisFlushMode = RedisFlushMode.IMMEDIATE)
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
