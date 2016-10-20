package cloud.simple.service.oauth2;

import java.util.Map;

import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Configuration;

import com.simple.gateway.oauth2.CustomUserDetail;

@Configuration
public class CustomPrincipalExtractor implements PrincipalExtractor {

	@Override
	public Object extractPrincipal(Map<String, Object> map) {
		CustomUserDetail user = new CustomUserDetail();
		user.setUserId((String) map.get("userId"));
		user.setUsername((String) map.get("username"));
		user.setClientId((String) map.get("clientId"));
		return user;
	}

}
