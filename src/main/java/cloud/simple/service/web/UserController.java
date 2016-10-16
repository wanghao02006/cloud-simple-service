package cloud.simple.service.web;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cloud.simple.service.conf.DataSourceProperties;
import cloud.simple.service.domain.UserService;
import cloud.simple.service.model.CustomUserDetail;
import cloud.simple.service.model.User;

@RestController
@RequestMapping("/user")
public class UserController {

	@Value("${name}")
	String name = "World";

	@Value("${age}")
	Integer age = 0;

	@Autowired
	UserService userService;

	@Autowired
	private DataSourceProperties dataSourceProperties;

	@RequestMapping(value = "/conf", method = RequestMethod.GET)
	public String home(Principal p) {
		CustomUserDetail user = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomUserDetail u=(CustomUserDetail)SecurityContextHolder.getContext().getAuthentication().getDetails();
		String s = "";
		if (user != null)
			s = "principal=" + user;
		else
			s = "principal=UNKNOWN";
		s += ", name=" + name + " ,age=" + age + " ,pass=" + dataSourceProperties.getPassword() + ",url="
				+ dataSourceProperties.getUrl();
		return s;
	}

	// 通过http://user-service/swagger-ui.html可以查看api文档页面
	@ApiOperation(value = "Get方式获取用户列表", notes = "notes for Get方式获取用户列表")
	@RequestMapping(value = "/{username}", method = RequestMethod.GET)
	public List<User> readUserInfo(
			@ApiParam(name = "username", value = "用户名", required = true) @PathVariable("username") String userName) {
		List<User> ls = userService.searchAll();
		User u = new User();
		u.setId(userName);
		u.setUsername(userName);
		ls.add(u);
		return ls;
	}

	@ApiOperation(value = "Post方式获取用户列表", notes = "notes for Post方式获取用户列表")
	@RequestMapping(value = "/{username}", method = RequestMethod.POST)
	public List<User> readUserInfo2(
			@ApiParam(name = "username", value = "用户名", required = true) @PathVariable("username") String userName,
			@ApiParam(name = "param", value = "request参数", required = false) @RequestParam("param") String param,
			@ApiParam(name = "some_header", value = "header参数", required = false) @RequestHeader("some_header") String header,
			@ApiParam(name = "user", value = "用户详细实体user", required = true) @RequestBody User user) {
		List<User> ls = userService.searchAll();
		User u = new User();
		u.setId(user.getId());
		u.setUsername(user.getUsername());
		ls.add(u);
		u = new User();
		u.setId(userName);
		u.setUsername(userName);
		ls.add(u);
		u = new User();
		u.setId(param);
		u.setUsername(param);
		ls.add(u);
		u = new User();
		u.setId(header);
		u.setUsername(header);
		ls.add(u);
		return ls;
	}

	@ApiOperation(value = "测试session传递", notes = "notes for 测试session传递")
	@RequestMapping(value = "/session", method = RequestMethod.GET)
	public String testSession(HttpServletRequest request) {
		// System.out.println("Cookies:");
		// Cookie[] cookies = request.getCookies();
		// if (cookies != null)
		// for (Cookie cookie : cookies)
		// System.out.println(cookie.getName() + "=" + cookie.getValue());
		// System.out.println("Headers:");
		// Enumeration<String> names = request.getHeaderNames();
		// while (names.hasMoreElements()) {
		// String name = names.nextElement();
		// String value = "";
		// Enumeration<String> values = request.getHeaders(name);
		// while (values.hasMoreElements())
		// value += values.nextElement() + ",";
		// System.out.println(name + "=" + value);
		// }

		StringBuffer sb = new StringBuffer();
		sb.append("user-service里面的sessionid=" + request.getSession().getId());
		sb.append("，从ui传来的testname=" + request.getSession().getAttribute("testname"));
		request.getSession().setAttribute("testname", "user-service");
		String result = "" + System.currentTimeMillis();
		request.getSession().setAttribute("result", result);
		sb.append("，将testname设置为：user-service，result设置为：" + result + "。<br>");
		return sb.toString();
	}

}
