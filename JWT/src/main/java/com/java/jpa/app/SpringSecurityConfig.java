package com.java.jpa.app;

import java.util.Arrays;

//import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.java.jpa.app.auth.filter.JWTAuthenticationFilter;
import com.java.jpa.app.auth.filter.JWTAuthorizationFilter;
import com.java.jpa.app.auth.handler.LoginSuccessHandler;
import com.java.jpa.app.services.JpaUserDetailService;

@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

	private static final String[] rutasAll = { 
			"/", 
			"/css/**", 
			"/js/**", 
			"/listar**",
			"/locale"};
//	private static final String[] rutasUser = { 
//			"/ver/**", 
//			"uploads/**" };
//	private static final String[] rutasAdmin = { 
//			"/form/**", 
//			"/factura/**", 
//			"/delete/**" };
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
//	@Autowired
//	private DataSource dataSource;
	
	@Autowired
	private LoginSuccessHandler successHandler;
	
	@Autowired
	private JpaUserDetailService userService;
	
	 @Autowired
	 public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception{
//		 Autenticacion desde memoria, sin DB
//		 PasswordEncoder encoder = this.passwordEncoder;
//		 UserBuilder users = User.builder().passwordEncoder(encoder::encode);
//		 builder.inMemoryAuthentication()
//		 .withUser(users.username("admin").password("sa").roles("ADMIN", "USER"))
//		 .withUser(users.username("amat").password("sa").roles("USER"));
		 
//		 Autenticacion con JDBC 2-forma
//		 builder.jdbcAuthentication()
//		 .dataSource(dataSource)
//		 .passwordEncoder(passwordEncoder)
//		 .usersByUsernameQuery("select username, password, enabled from users where username=?")
//		 .authoritiesByUsernameQuery("select u.username, a.authority from authorities a inner join users u on (a.user_id=u.id) where u.username=?")
//		 ;
		 
//		 3era forma
		 builder.userDetailsService(userService)
		 .passwordEncoder(passwordEncoder);
		 
	 }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
		.antMatchers(rutasAll).permitAll()
//		.antMatchers(rutasUser).hasAnyRole("USER")
//		.antMatchers(rutasAdmin).hasAnyRole("ADMIN")
		.anyRequest().authenticated()
		/*.and()
			.formLogin()
				.successHandler(successHandler)
				.loginPage("/login")
				.permitAll()
		.and()
			.logout()
				.permitAll()
		.and()
			.exceptionHandling()
				.accessDeniedPage("/error_403")*/
		.and()
		.addFilter(new JWTAuthenticationFilter(authenticationManager()))
		.addFilter(new JWTAuthorizationFilter(authenticationManager()))
		.csrf().disable()
		.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().cors().configurationSource(corsConfigurationSource())
		;
	}
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOriginPatterns(Arrays.asList("*"));
		config.setAllowedMethods(Arrays.asList("GET", "POST"));
		config.setAllowCredentials(true);
		config.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
	
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter(){
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}
	 
}
