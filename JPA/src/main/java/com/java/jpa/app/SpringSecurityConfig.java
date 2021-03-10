package com.java.jpa.app;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.java.jpa.app.auth.handler.LoginSuccessHandler;

@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

	private static final String[] rutasAll = { 
			"/", 
			"/css/**", 
			"/js/**", 
			"/listar" };
//	private static final String[] rutasUser = { 
//			"/ver/**", 
//			"uploads/**" };
//	private static final String[] rutasAdmin = { 
//			"/form/**", 
//			"/factura/**", 
//			"/delete/**" };
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private LoginSuccessHandler successHandler;
	
	 @Autowired
	 public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception{
//		 Autenticacion desde memoria, sin DB
//		 PasswordEncoder encoder = this.passwordEncoder;
//		 UserBuilder users = User.builder().passwordEncoder(encoder::encode);
//		 builder.inMemoryAuthentication()
//		 .withUser(users.username("admin").password("sa").roles("ADMIN", "USER"))
//		 .withUser(users.username("amat").password("sa").roles("USER"));
//		 Autenticacion con JDBC
		 builder.jdbcAuthentication()
		 .dataSource(dataSource)
		 .passwordEncoder(passwordEncoder)
		 .usersByUsernameQuery("select username, password, enabled from users where username=?")
		 .authoritiesByUsernameQuery("select u.username, a.authority from authorities a inner join users u on (a.user_id=u.id) where u.username=?")
		 ;
	 }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
		.antMatchers(rutasAll).permitAll()
//		.antMatchers(rutasUser).hasAnyRole("USER")
//		.antMatchers(rutasAdmin).hasAnyRole("ADMIN")
		.anyRequest().authenticated()
		.and()
			.formLogin()
				.successHandler(successHandler)
				.loginPage("/login")
				.permitAll()
		.and()
			.logout()
				.permitAll()
		.and()
			.exceptionHandling()
				.accessDeniedPage("/error_403")
		;
	}
	 
	 
}
