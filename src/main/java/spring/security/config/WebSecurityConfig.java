package spring.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import spring.security.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserService userService;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userService);
		auth.setPasswordEncoder(passwordEncoder());
		return auth;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		
		// nhung trang
		http
		.authorizeRequests()
		.antMatchers("/","/shop/**","/forgotpassword","/register").permitAll()
		.antMatchers("/admin/**").hasRole("ADMIN")
		.anyRequest()
		.authenticated()
		.and()
		.formLogin() //  nhận form từ UI
		.loginPage("/login") // nhan  form login (post)
		.permitAll()
		.failureUrl("/login?error=true")
		.defaultSuccessUrl("/")
		.usernameParameter("email") // check email , password
		.passwordParameter("password")
		.and()
		.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/login")
		.invalidateHttpSession(true)
		.deleteCookies("JSESSIONID")
		.and()
		.exceptionHandling()
		.and()
		.csrf()
		.disable();
http.headers().frameOptions().disable();
		
	}



}
