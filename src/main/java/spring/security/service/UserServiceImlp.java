package spring.security.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import spring.security.dto.UserDTO;
import spring.security.model.Role;
import spring.security.model.User;
import spring.security.repository.UserRepository;

@Service
public class UserServiceImlp implements UserService {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepo;

	// ham dang nhap
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}
	

	// dang yeu cau 1 @bean trong file config
	@Override
	public User save(UserDTO registrationDto) {
		User user = new User(registrationDto.getEmail(), passwordEncoder.encode(registrationDto.getPassword()), registrationDto.getName(),
				registrationDto.getAddress(), registrationDto.getDate(), Arrays.asList(new Role("ROLE_USER")));
		return userRepo.save(user);
	}

}
