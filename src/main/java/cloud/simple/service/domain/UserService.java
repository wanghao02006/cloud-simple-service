package cloud.simple.service.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import cloud.simple.service.dao.UserDao;
import cloud.simple.service.model.User;

@Service
@Transactional
public class UserService {
	
	@Autowired
	private UserDao userMapper;
	
	@HystrixCommand(fallbackMethod = "fallbackSearchAll")
	public List<User> searchAll(){
		List<User> list = userMapper.findAll();
		return list;
	}
	
	@SuppressWarnings("unused")
	private List<User> fallbackSearchAll() {
		List<User> ls = new ArrayList<User>();
		User user = new User();
		user.setUsername("TestHystrixCommand from userservice");
		ls.add(user);
		return ls;
	}
	
}
