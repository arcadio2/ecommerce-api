package com.empresa.proyecto.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.empresa.proyecto.models.entity.Perfil;
import com.empresa.proyecto.models.entity.Usuario;
import com.empresa.proyecto.models.service.IUsuarioService;



@Component
public class InfoAdicionalToken implements TokenEnhancer{
	@Autowired
	private IUsuarioService usuarioService; 
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> info = new HashMap<String, Object>();
		Usuario usuario = usuarioService.findByUsername(authentication.getName());
		Perfil perfil = usuarioService.getProfileByUsername(usuario.getUsername()); 
		
		info.put("nombre", usuario.getNombre());
		info.put("apellido", usuario.getApellido());
		info.put("email", usuario.getEmail());
		info.put("active", usuario.isActive());
		//info.put("perfilId", perfil.getId()); 
		
		( (DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		
		return accessToken;
	}
}
