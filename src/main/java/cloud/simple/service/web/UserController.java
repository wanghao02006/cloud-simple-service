package cloud.simple.service.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cloud.simple.service.domain.UserService;
import cloud.simple.service.model.User;

@RestController
@EnableEurekaClient
public class UserController {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
	public List<User> readUserInfo(@PathVariable("username") String userName) {
		List<User> ls = userService.searchAll();
		User u = new User();
		u.setId(userName);
		u.setUsername(userName);
		ls.add(u);
		return ls;
	}
}
