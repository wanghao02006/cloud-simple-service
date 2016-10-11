package cloud.simple.service.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cloud.simple.service.conf.DataSourceProperties;
import cloud.simple.service.domain.UserService;
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

	@RequestMapping("/conf")
	public String home() {
		return "name=" + name + " ,age=" + age + " ,pass=" + dataSourceProperties.getPassword() + ",url=" + dataSourceProperties.getUrl();
	}

	@RequestMapping(value = "/{username}", method = RequestMethod.GET)
	public List<User> readUserInfo(@PathVariable("username") String userName) {
		List<User> ls = userService.searchAll();
		User u = new User();
		u.setId(userName);
		u.setUsername(userName);
		ls.add(u);
		return ls;
	}

	@RequestMapping("/session")
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
