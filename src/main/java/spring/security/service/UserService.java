package spring.security.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import spring.security.dto.UserDTO;
import spring.security.model.User;

public interface UserService extends UserDetailsService{
	User save(UserDTO registrationDto);
	
}
