package com.java.jpa.app.auth.filter;

import java.io.IOException;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.jpa.app.models.entity.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

//	SecretKey secretKey = new SecretKeySpec("Alguna.Clave.Secreta.123456".getBytes(), SignatureAlgorithm.HS512.getJcaName());
//	Clave secreta creada automaticamente por secretKeyFor de 512Bits
	public static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
	private AuthenticationManager authManager;
	
	public JWTAuthenticationFilter(AuthenticationManager authManager) {
		this.authManager = authManager;
		setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login","POST"));
	}

//	Se encarga de realizar la autenticacion
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		if( username != null && password != null ) {
			logger.info("Username desde request parameters(form-data): ".concat(username));
			logger.info("password desde request parameters(form-data): ".concat(password));
		} else {
			Usuario user = null;
			try {
//				Casteo inverso, convertir de JSON a Object Usuario
				user = new ObjectMapper().readValue( request.getInputStream(), Usuario.class);
				username = user.getUsername();
				password = user.getPassword();
				logger.info("Username desde request InputStream(raw): ".concat(username));
				logger.info("password desde request InputStream(raw): ".concat(password));
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		username = username.trim();
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
		return authManager.authenticate(authToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		String username = ((User) authResult.getPrincipal()).getUsername();
		Collection<? extends GrantedAuthority> roles= authResult.getAuthorities();
		Claims claims = Jwts.claims();
		claims.put("authorities", new ObjectMapper().writeValueAsString(roles));//Automaticamente se transforma a String
//		Creando clave secreta de forma instanciada, con tamaño de 256bits
		SecretKey secretKey = new SecretKeySpec("Alguna.Clave.Secreta.123456Alguna.Clave.Secreta.123456".getBytes(), SignatureAlgorithm.HS256.getJcaName());
		
		String token = Jwts.builder()
				.setClaims(claims)
				.setSubject(username )
				.signWith(secretKey)
				.setIssuedAt(new Date())//Fecha creacion
				.setExpiration(new Date(System.currentTimeMillis() + 3600000*4))//fecha expiracion
				.compact();
//		Estandar enviar con Bearer
		response.addHeader("Authorization", "Bearer ".concat(token));
		Map<String, Object> body = new HashMap<>();
		body.put("token", token);
		body.put("user", (User) authResult.getPrincipal());
		body.put("mensaje", String.format("Hola %s, has iniciado sesión con éxito!", username));
//		Se debe transformar el map a un json, mediante la clase objectMapper
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setStatus(200);
		response.setContentType("application/json");
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		Map<String, Object> body = new HashMap<>();
		body.put("mensaje", "Error de autenticacion: username o password incorrecto");
		body.put("error", failed.getMessage());
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setStatus(401);
		response.setContentType("application/json");
	}

	
}
