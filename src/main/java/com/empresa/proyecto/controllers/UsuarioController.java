package com.empresa.proyecto.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.security.PermitAll;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.empresa.proyecto.models.entity.Usuario;
import com.empresa.proyecto.models.entity.docs.UserPassword;
import com.empresa.proyecto.models.entity.Perfil;
import com.empresa.proyecto.models.entity.Producto;
import com.empresa.proyecto.models.entity.Role;

import com.empresa.proyecto.models.service.IUsuarioService;
import com.empresa.proyecto.services.IEmailService;
import com.empresa.proyecto.services.IFileService;
import com.empresa.proyecto.services.IValidationService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200/","*"})
public class UsuarioController {

	@Autowired
	IUsuarioService usuarioService; 

	
	@Autowired 
	IFileService fileService; 
	
	@Autowired
	IValidationService validationService; 
	
	@Autowired
	private TokenStore tokenStore;
	
	@Autowired
	IEmailService emailService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@PostMapping("/user/create")
	@ResponseStatus(HttpStatus.CREATED)//201
	//@Secured({})
	public ResponseEntity<?> createUser(@RequestBody @Valid Usuario usuario, BindingResult result){
		
		String ip="localhost";
		Map<String, Object> response = new HashMap<>();
		
			
			if(result.hasErrors()) {

				response = validationService.responseErrors(result);
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
			}  
			if(usuarioService.existeUsuarioByEmail(usuario.getEmail()) ) {
				response.put("mensaje", "El email ya está registrado");
				response.put("error","Email inválido");
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			}else if(usuarioService.existeUsuarioByUsername(usuario.getUsername())) {
				response.put("mensaje", "El usuario ya está en uso");
				response.put("error","Username inváldo");
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			}  
			
			try {
   
				List<Role> roles =  Arrays.asList(usuarioService.getRoleByName("ROLE_USER")); 
		 
				usuario.setRoles(roles); 
				
				usuario.setEnabled(true);
				String token = UUID.randomUUID().toString();
				usuario.setToken(token);
				usuario.setPassword( passwordEncoder.encode( usuario.getPassword() )); 
				Usuario nuevoUsuario = usuarioService.save(usuario); 
				
				
				Map<String, Object> datos = new HashMap<>();
				datos.put("usuario", usuario.getNombre());
				datos.put("url", ""+ip+":4200/confirm?uiid="+token); 
		
				emailService.sendWithAttach2("shinesadecv170@gmail.com", nuevoUsuario.getEmail(), "Shine: Confirmar correo", "Por favor confirma tu cuenta",datos);
				
				response.put("mensaje", "El usuario ha sido creado con éxito");
				response.put("usuario",nuevoUsuario); 
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
			}catch(Exception e) {
				response.put("Error", "Ha ocurrido un error al guardar \n"+e.getCause()); 
				System.out.println(e.getMessage());
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 	
			}   

	}
	
	//@PermitAll
	//@Secured("permitAll()")
	@PostMapping("/user/confirm")
	public ResponseEntity<?> confirmarUsuario(@RequestBody String token) {
		Map<String, Object> response = new HashMap<>();
		//String username = auth.getName();
		Usuario usuario = null;  
		try {
			usuario = usuarioService.getBytoken(token);
			usuario.setActive(true); 
			usuario.setToken(""); 
			usuario = usuarioService.save(usuario); 
		}catch(Exception e) {
			response.put("error", "Ha ocurrido un error"); 
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 	
		}
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
		
	}
	
	@GetMapping("/user/profile/{username}")
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	public ResponseEntity<?> perfil(@PathVariable String username){
		Map<String, Object> response = new HashMap<>();
		Perfil perfil = null;  
		try {
			perfil = usuarioService.getProfileByUsername(username);
		}catch(Exception e) {
			response.put("error", "Ha ocurrido un error"); 
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 	
		}
		if(perfil == null) {
			response.put("error", "No se ha encontrado el perfil"); 
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND); 	
		}
		response.put("mensaje", "Se ha encontrado el perfil"); 
		response.put("perfil", perfil); 
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK); 	
	}
	
	@PostMapping("/user/profile")
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> createPerfil(@RequestBody @Valid Perfil perfil,
			BindingResult result,Authentication authentication){
		
		Map<String, Object> response = new HashMap<>();
		String username = authentication.getName(); 	
		
		/*
		Usuario usuario = null; 
		try {
			usuario =  usuarioService.findByUsername(username); 
		}catch(Exception e) {
			response.put("error", "No estás autenticado");
			response.put("mensaje", e.getMessage()); 
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.FORBIDDEN);
			
		}*/
	
		
		System.out.println("USUAERIO OBTENIDO: " +perfil.getSexo().getId());
		if(result.hasErrors()) {
			response = validationService.responseErrors(result);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		//BindingResult result_user; 
		//result_user.addError(perfil.getUsuario()); 
		
		
		System.out.println("ENTRA");
		Perfil perfilCreado = null; 
	   
		/*Obtenemos el usuario*/
		Usuario usuario = usuarioService.findByUsername(perfil.getUsuario().getUsername()); 
		usuario.setNombre(perfil.getUsuario().getNombre()); 
		usuario.setApellido(perfil.getUsuario().getApellido()); 
		perfil.setUsuario(usuario);
		perfil.setSexo(usuarioService.getSexoById(perfil.getSexo().getId())); 
		//perfil.setFoto("no_user.png");
		//Usuario usuario_editado =  null; 
		try {
			//ususario_editado = usuarioService.save(usuario_editado); 
			perfilCreado = usuarioService.saveProfile(perfil); 
			 
		}catch(Exception e) {
			response.put("error", "No se ha podido crear el perfil");
			
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST); 	
		}
		
		
		response.put("mensaje", "El perfil ha sido actualizado con éxito"); 
		response.put("perfil", perfilCreado); 
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK); 	
	} 
	
	
	@PutMapping("/user/profile")
	@Secured({"ROLE_ADMIN","ROLE_USER","ROLE_INSTRUCTOR"})
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> editPerfil(@RequestBody Perfil perfil){
		Map<String, Object> response = new HashMap<>();

		
		Perfil perfilActual = null; 
		try {
			String username = perfil.getUsuario().getUsername();
			perfilActual = usuarioService.getProfileByUsername(username);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}

		if(perfilActual == null) {
			response.put("error", "No existe el perfil a editar");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND); 
		}
		
		Perfil perfilUpdated = null; 
		try {
			perfilActual.setAltura(perfil.getAltura());
			perfilActual.setEdad(perfil.getEdad());
			  
			//perfilActual.setFoto(perfil.getFoto());
			//perfilActual.setPeso(perfil.getPeso());
	

			
			perfilUpdated = usuarioService.saveProfile(perfilActual);
		}catch(Exception e) {
			response.put("mensaje", "Ocurrió un error al guardar ");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El perfil ha sido editado con éxito");
		response.put("perfil",perfilUpdated);
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
	
	@GetMapping("/hola")
	public Map<String,Object> index() {   
		System.out.println("ENTRA");
		List<String> milista =new  ArrayList<String>(); 
		milista.add("hola"); 
		milista.add("VIctor"); 
		milista.add("cristobal");
		milista.add("Zury");
		Map<String, Object> response = new HashMap<>();
		response.put("objeto", milista); 
		 
		
		return response; 
	}
	
	
	
	/*
	@PostMapping("/usuarios/upload")
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	public ResponseEntity<?> upload(@RequestParam(name = "file") MultipartFile archivo, @RequestParam("username") String username){
		
		Map<String, Object> response = new HashMap<>();
		Perfil perfil = usuarioService.getProfileByUsername(username); 
		if(!archivo.isEmpty()) {
			String nombreArchivo = null;
			try {
				nombreArchivo = fileService.uploadImage(archivo);
			} catch (IOException e) {
				response.put("mensaje", "Error al subir la imagen");
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR); 
			
			}
			fileService.deleteImage(perfil.getId());
				 
			perfil.setFoto(nombreArchivo); 
			usuarioService.saveProfile(perfil);
			response.put("perfil", perfil);
			response.put("mensaje", "Se ha subidio correctamente la imagen "+nombreArchivo);
			
		}else {
			response.put("error", "Debe seleccionar una imagen");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST); 
		}
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK); 
	}
	*/
	
	/*IMAGEN DE PERFIL*/
	@GetMapping("/usuarios/uploads/img/{nombreFoto:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto){
		Path rutaArchivo = Paths.get("uploads").resolve(nombreFoto).toAbsolutePath();
		Resource recurso = null; 
		try {
			recurso = fileService.cargar(nombreFoto); 
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} 
 
		/*Para que se pueda descargar el recurso*/
		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\" "+recurso.getFilename()+"\" ");
		
		return new ResponseEntity<Resource>(recurso,cabecera,HttpStatus.OK);
	}
  
	@PutMapping("/user/makeAdmin")
	@Secured({"ROLE_ADMIN"})
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<?> makeAdmin(@RequestBody Usuario usuario){
		Map<String, Object> response = new HashMap<>();
		
		Usuario usuarioObtenido = null; 
		try {
			usuarioObtenido = usuarioService.findByUsername(usuario.getUsername()); 
		}catch(Exception e) {
			response.put("mensaje", "No se ha encontrado el usuario a editar");
			response.put("error", e.getMessage()); 
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		List<Role> roles = usuarioObtenido.getRoles(); 
		boolean hacer = true; 
		
		Role newRole = usuarioService.getRoleByName("ROLE_ADMIN");
		for (Role role : roles) {
			if(role.equals(newRole)) {
				hacer = false; 
			}
		}
		if(hacer) {
			roles.add(newRole);
		}else {
			roles.remove(newRole);
		}
		for (Role role : roles) {
			System.out.println(role.getNombre());
		}
		usuarioObtenido.setRoles(roles);  
		 
		Usuario usuarioUpdated = null; 
		try {
			usuarioUpdated = usuarioService.save(usuarioObtenido); 
		}catch(Exception e) {
			response.put("mensaje", "No se ha podido guardar el usuario");
			System.out.println(e.getMessage());
			response.put("error", e.getMessage()); 
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		response.put("mensaje","Se ha actualizado el usuario con éxito");
		response.put("usuario", usuarioUpdated); 
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
		
	}
	
	@PutMapping("/user/contrasenia")
	@Secured({"ROLE_USER"})
	public ResponseEntity<?> cambiarContraseña(@RequestBody @Valid UserPassword usuario,
			BindingResult result, Authentication auth){
		Map<String, Object> response = new HashMap<>();
		

		String username = auth.getName(); 
		System.out.println("EL USUARIO ES "+username);
		Usuario usuarioObtenido = null; 
		try {
			usuarioObtenido = usuarioService.findByUsername(username); 
		}catch(Exception e) {
			response.put("mensaje", "No se ha encontrado el usuario a editar");
			response.put("error", e.getMessage()); 
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(result.hasErrors()) {
			
			response = validationService.responseErrors(result);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}  
		//String viejaEncode = passwordEncoder.encode(usuario.getVieja());
		boolean contrasenasCoinciden = passwordEncoder.matches(usuario.getVieja(), usuarioObtenido.getPassword());
		if(contrasenasCoinciden!=true) {
			response.put("mensaje", "La contraseña que proporcionaste es incorrecta");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		
		String pssSaved = passwordEncoder.encode(usuario.getContrasenia()); 
		usuarioObtenido.setPassword(pssSaved); 
		Usuario usuarioUpdated = null; 
		try {
			usuarioUpdated = usuarioService.save(usuarioObtenido); 
		}catch(Exception e) {
			response.put("mensaje", "No se ha podido guardar el usuario");
			System.out.println(e.getMessage());
			response.put("error", e.getMessage()); 
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje","Se ha actualizado la contraseña");
		response.put("usuario", usuarioUpdated); 
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
		
		
		
		
	}
	
	
	@PutMapping("/user/makeInstructor")
	@Secured({"ROLE_ADMIN"})
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<?> makeInstructor(@RequestBody Usuario usuario){
		Map<String, Object> response = new HashMap<>();
		
		Usuario usuarioObtenido = null; 
		try {
			usuarioObtenido = usuarioService.findByUsername(usuario.getUsername()); 
		}catch(Exception e) {
			response.put("mensaje", "No se ha encontrado el usuario a editar");
			response.put("error", e.getMessage()); 
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		List<Role> roles = usuarioObtenido.getRoles(); 
		boolean hacer = true; 
		
		Role newRole = usuarioService.getRoleByName("ROLE_INSTRUCTOR");
		for (Role role : roles) {
			if(role.equals(newRole)) {
				hacer = false; 
			}
		}
		if(hacer) {
			roles.add(newRole);
		}else {
			roles.remove(newRole);
		}
		for (Role role : roles) {
			System.out.println(role.getNombre());
		}
		usuarioObtenido.setRoles(roles);  
		 
		Usuario usuarioUpdated = null; 
		try {
			
			usuarioUpdated = usuarioService.save(usuarioObtenido); 
		}catch(Exception e) {
			response.put("mensaje", "No se ha podido guardar el usuario");
			System.out.println(e.getMessage());
			response.put("error", e.getMessage()); 
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		response.put("mensaje","Se ha actualizado el usuario con éxito");
		response.put("usuario", usuarioUpdated); 
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
		
	}

	
	@DeleteMapping("/user/{username}")
	@Secured({"ROLE_ADMIN"})
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<?> deleteUser(@PathVariable String username){
		Map<String, Object> response = new HashMap<>();
		Usuario usuarioObtenido = null; 
		
		try {
			usuarioObtenido = usuarioService.findByUsername(username);   
		}catch(Exception e) {
			response.put("mensaje", "No se ha encontrado el usuario a editar");
			response.put("error", e.getMessage()); 
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		} 
		try {   
			usuarioObtenido.setRoles(null); 
			Perfil perfil = usuarioService.getProfileByUsername(usuarioObtenido.getNombre());
			 
			Usuario nuevoUsuario = usuarioService.save(usuarioObtenido); 
			if(perfil != null) {
				usuarioService.deleteProfile(perfil); 
			}
			 
			usuarioService.deleteUser(nuevoUsuario);   
		}catch(Exception e) {
			response.put("mensaje", "No se ha podido borrar el usuario");
			response.put("error", e.getMessage()); 
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		response.put("mensaje","El usuario se ha eliminado con exito");
		//response.put("usuario", usuarioUpdated); 
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	}
	
	@GetMapping("/users")
	@Secured({"ROLE_ADMIN"})
	public List<Usuario> getAllusers(){
		return usuarioService.getAllUsers();
	}
	
	@GetMapping("/users/usuarios/{nombre}")
	@Secured({"ROLE_ADMIN","ROLE_INSTRUCTOR","ROLE_USER"})
	public List<Usuario> getUsersByRole(@PathVariable String nombre){
		return usuarioService.getUsuariosByRole(nombre);
	} 
	
	@GetMapping("/users/perfiles")
	@Secured({"ROLE_ADMIN","ROLE_INSTRUCTOR","ROLE_USER"})
	public List<Perfil> getAllProfiles(){
		return usuarioService.getAllProfiles();
	} 
	
	@GetMapping("/users/perfiles/{nombre}")
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	public List<Perfil> getPerfilesByRole(@PathVariable String nombre){
		return usuarioService.getPerfilByRole(nombre);
	}
	
	@GetMapping("/get/user")
	@Secured({"ROLE_USER"})
	public ResponseEntity<?> getUsuarioByUsernme(Authentication auth){
		Map<String, Object> response = new HashMap<>();
		String username = auth.getName();
		System.out.println("USuario "+username);
		
		Usuario usuario_response = null; 
		try {
			usuario_response = usuarioService.findByUsername(username); 
		}catch(Exception e) {
			response.put("error", "No se encontró el usuario"); 
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND); 	
		}
		if(usuario_response==null) {
			response.put("error", "No se encontró el usuario"); 
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND); 	
		}
	
		response.put("mensaje", "Se ha encontrado el usuario"); 
		response.put("usuario", usuario_response); 
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK); 
	}
	
	
	@PostMapping("/logout")
	public void logout(HttpServletRequest request) throws ServletException {
	    String authHeader = request.getHeader("Authorization");
	    System.out.println(authHeader);
	    if (authHeader != null) {
	        String tokenValue = authHeader.replace("Bearer", "").trim();
	        OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
	        tokenStore.removeAccessToken(accessToken);
	    }
	}
	
 
	
	
	
}
