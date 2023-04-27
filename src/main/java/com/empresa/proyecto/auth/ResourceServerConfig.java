package com.empresa.proyecto.auth;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
@Configuration
@EnableResourceServer
public class ResourceServerConfig  extends ResourceServerConfigurerAdapter{
	
	/**
	 * Asignamos las rutas publicas para todos los usuarios, aun si no están autenticados
	 * */
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/hola", 
				"/api/usuarios/uploads/img/**","/api/productos/**","/api/productos",
				"/api/productos/producto/**",
				"/api/productos/get/**","/api/imagenes/**",
				"/api/clientes/uploads/img/**", "/images/**").permitAll()
		.antMatchers(HttpMethod.POST,"/api/user/create").permitAll()
		
		//.antMatchers("/api/clientes/{id}").permitAll()
		//.antMatchers("/api/facturas/**").permitAll()
		/*.antMatchers(HttpMethod.GET,"/api/clientes/{id}").hasAnyRole("USER","ADMIN")
		.antMatchers(HttpMethod.POST,"/api/clientes").hasAnyRole("ADMIN")
		.antMatchers("/api/clientes/**").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET,"/api/clientes/upload").hasAnyRole("USER","ADMIN")*/
		.anyRequest().authenticated()
		.and().cors().configurationSource(corsConfigurationSource());
	} 
	
	//Esto para que no se puedan meter peticiones desde otros sitios web no definidos
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		config.setAllowedOriginPatterns(Arrays.asList("*"));
		config.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
		config.setAllowCredentials(true);
		config.setAllowedHeaders(Arrays.asList("Content-Type","Authorization"));
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
	
	//GENERAR TOKEN Y VALIDAR, filtro de cors
	@Bean
	public FilterRegistrationBean<CorsFilter> crossFilter(){
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource() ));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}
}
