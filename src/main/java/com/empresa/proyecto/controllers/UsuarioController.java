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
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

import com.empresa.proyecto.models.entity.Perfil;
import com.empresa.proyecto.models.entity.Role;

import com.empresa.proyecto.models.service.IUsuarioService;
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
	private BCryptPasswordEncoder passwordEncoder;
	
	@PostMapping("/user/create")
	@ResponseStatus(HttpStatus.CREATED)//201
	//@Secured({})
	public ResponseEntity<?> createUser(@RequestBody @Valid Usuario usuario, BindingResult result){
		
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
				   
				//roles.add(role);				 
				usuario.setRoles(roles); 
				usuario.setEnabled(true);
				usuario.setPassword( passwordEncoder.encode( usuario.getPassword() )); 
				Usuario nuevoUsuario = usuarioService.save(usuario); 
				
				response.put("mensaje", "El usuario ha sido creado con éxito");
				response.put("usuario",nuevoUsuario); 
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
			}catch(Exception e) {
				response.put("Error", "Ha ocurrido un error al guardar \n"+e.getCause()); 
				System.out.println(e.getMessage());
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 	
			}   

	}
	@GetMapping("/user/profile/{username}")
	@Secured({"ROLE_ADMIN","ROLE_USER","ROLE_INSTRUCTOR"})
	public ResponseEntity<?> perfil(@PathVariable String username){
		Map<String, Object> response = new HashMap<>();
		Perfil perfil = null;  
		try {
			perfil = usuarioService.getProfileByUsername(username);
		}catch(Exception e) {
			response.put("error", "No se encontró el usuario"); 
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 	
		}
		if(perfil == null) {
			response.put("error", "No se ha encontrado el perfil"); 
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST); 	
		}
		response.put("mensaje", "Se ha encontrado el perfil"); 
		response.put("perfil", perfil); 
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK); 	
	}
	
	@PostMapping("/user/profile")
	@Secured({"ROLE_ADMIN","ROLE_USER","ROLE_INSTRUCTOR"})
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> createPerfil(@RequestBody @Valid Perfil perfil,  BindingResult result){
		Map<String, Object> response = new HashMap<>();

		if(result.hasErrors()) {
			response = validationService.responseErrors(result);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		System.out.println("ENTRA");
		Perfil perfilCreado = null; 
	   
		/*Obtenemos el usuario*/
		Usuario usuario = usuarioService.findByUsername(perfil.getUsuario().getUsername()); 
		perfil.setUsuario(usuario);
		perfil.setSexo(usuarioService.getSexoById(perfil.getSexo().getId())); 
		//perfil.setFoto("no_user.png");
		
		try {
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
			perfilActual.setPeso(perfil.getPeso());
	

			
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
	public List<String> index() {   
		System.out.println("ENTRA");
		List<String> milista =new  ArrayList<String>(); 
		milista.add("GOLA"); 
		milista.add("Richie"); 
		 
		
		return milista; 
	}
	
	
	
	
	@PostMapping("/usuarios/upload")
	@Secured({"ROLE_ADMIN","ROLE_USER","ROLE_INSTRUCTOR"})
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
	@Secured({"ROLE_ADMIN","ROLE_INSTRUCTOR","ROLE_USER"})
	public List<Perfil> getPerfilesByRole(@PathVariable String nombre){
		return usuarioService.getPerfilByRole(nombre);
	}
	
	
	@GetMapping("/users/alumnos/{instructor}")
	@Secured({"ROLE_ADMIN","ROLE_INSTRUCTOR"})
	public List<Perfil> getAlumnos(@PathVariable String instructor){
		return usuarioService.getPerfilesByInstructor(instructor); 
	}
	
	
	
}
