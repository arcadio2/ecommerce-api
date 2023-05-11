package com.empresa.proyecto.auth;



import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;



@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig  extends AuthorizationServerConfigurerAdapter{
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private InfoAdicionalToken infoAdicionalToken;
	
	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authenticationManager;

	/*Permitimos a todos el acceso*/
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()")
		.checkTokenAccess("isAunthenticated()");
		
	}
	
	/**
	 * Aquí mandamos la configucarión del token y lo que va a recibir como llaves
	 *  como lo es, el nombre de nuestra aplicacion, y la contraseña, esto se mandará por
	 *  cabeceras http
	 *  Y podemos tanto leer como escribir
	 *  El token va a servir cada 3600 segundos
	 * */

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		/*Solo tenemos una app, pero puede haber mas*/
		clients.inMemory().withClient("angularApp")
		.secret(passwordEncoder.encode("12345"))
		.scopes("read","write")
		.authorizedGrantTypes("password", "refresh_token")
		.accessTokenValiditySeconds(7200)
		.refreshTokenValiditySeconds(7200); //lo renueva cada 3600 s
		
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoAdicionalToken,accesTokenConverter()));
		
		endpoints.authenticationManager(authenticationManager)
		//.tokenStore(tokenStore())
		.accessTokenConverter(accesTokenConverter())
		.tokenEnhancer(tokenEnhancerChain); //persistencia del token
		
	}
	
	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(accesTokenConverter());
	}

	
	/**
	 * aquí asignamos las llaves publica y privda, el token va a verificar la validez con estas
	 * */
	@Bean
	public JwtAccessTokenConverter accesTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		//jwtAccessTokenConverter.setSigningKey(JwtConfig.LLAVE_SECRETA);
		jwtAccessTokenConverter.setSigningKey(JwtConfig.RSA_PRIVADA);
		jwtAccessTokenConverter.setVerifierKey(JwtConfig.RSA_PUBLICA);
		return jwtAccessTokenConverter;
	}
	
}
