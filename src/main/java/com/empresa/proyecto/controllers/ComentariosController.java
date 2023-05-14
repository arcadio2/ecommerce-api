package com.empresa.proyecto.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.proyecto.models.entity.ComentarioValoracion;
import com.empresa.proyecto.models.entity.Comentarios;
import com.empresa.proyecto.models.entity.ComentariosDto;
import com.empresa.proyecto.models.entity.Producto;
import com.empresa.proyecto.models.service.IComentariosService;
import com.empresa.proyecto.models.service.IProductoService;
import com.empresa.proyecto.models.service.IUsuarioService;
import com.empresa.proyecto.services.IValidationService;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = {"http://localhost:4200/","*"})
public class ComentariosController {
	
	@Autowired 
	IProductoService productoService; 
	
	@Autowired
	IComentariosService comentarioService; 
	

	@Autowired
	IUsuarioService usuarioService; 
	
	@Autowired
	IValidationService validationService; 

	//AGREGAR COMENTARIO
		@Secured({"ROLE_USER"})
		@PostMapping("/comentario/{id_producto}")
		public ResponseEntity<?> addComentario(@PathVariable Long id_producto,
				@RequestBody  ComentarioValoracion comentario_s,
				Authentication authentication){
				
			String username = authentication.getName();
			Map<String, Object> response = new HashMap<>();
			Comentarios comentario = new Comentarios(); 
			comentario.setComentario(comentario_s.getComentario());
			comentario.setValoracion(comentario_s.getValoracion());
			comentario.setTitulo(comentario_s.getTitulo()); 
			
			Producto producto = null; 
			
			try {
				producto = productoService.findById(id_producto); 
				comentario.setProducto(productoService.findById(id_producto)); 
				comentario.setUsuario(usuarioService.findByUsername(username)); 
			}catch(Exception e) {
				response.put("Error", "Ha ocurrido un error al guardar \n"+e.getCause());
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
			}
			
			/*if(result.hasErrors()) {
		
				response = validationService.responseErrors(result);
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
			}  */
			Comentarios comentario_saved = null; 
			try {
				comentario_saved = comentarioService.save(comentario); 
				
				
			}catch(Exception e) {
				response.put("Error", "Ha ocurrido un error al guardar \n"+e.getCause());
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 	
			}
		
			List<Comentarios> comentarios = comentarioService.getByIdProducto(id_producto); 
			Double promedio =this.obtenerPromedioValoracion(comentarios); 
			producto.setValoracion_total(promedio); 
			productoService.save(producto); 	
			response.put("mensaje", "El comentario ha sido agregado con éxito");
			response.put("comentario",comentario_saved); 
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
				
		}
		
		@Secured({"ROLE_USER"})
		@PutMapping("/comentario/{id_producto}")
		public ResponseEntity<?> editCpmentario(@PathVariable Long id_producto,
				@RequestBody  Comentarios comentario_s,
				Authentication authentication){
				
			String username = authentication.getName();
			Map<String, Object> response = new HashMap<>();
			/*Comentarios comentario = new Comentarios(); 
			comentario.setComentario(comentario_s.getComentario());
			comentario.setValoracion(comentario_s.getValoracion());
			comentario.setTitulo(comentario_s.getTitulo()); */
			Producto producto = null; 
			
			try {
				producto = productoService.findById(id_producto); 
				comentario_s.setProducto(productoService.findById(id_producto)); 
				comentario_s.setUsuario(usuarioService.findByUsername(username)); 
			}catch(Exception e) {
				response.put("Error", "Ha ocurrido un error al guardar \n"+e.getCause());
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
			}
			
			
			/*if(result.hasErrors()) {
		
				response = validationService.responseErrors(result);
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
			}  */
			Comentarios comentario_saved = null;
			try {
				comentario_saved = comentarioService.save(comentario_s); 
				
			}catch(Exception e) {
				response.put("Error", "Ha ocurrido un error al guardar \n"+e.getCause());
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 	
			}
			List<Comentarios> comentarios = comentarioService.getByIdProducto(id_producto); 
			Double promedio =this.obtenerPromedioValoracion(comentarios); 
			producto.setValoracion_total(promedio); 
			productoService.save(producto); 
			response.put("mensaje", "El comentario ha sido editado con éxito");
			response.put("comentario",comentario_saved); 
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
		}
		

		@Secured({"ROLE_USER"})
		@PostMapping("/comentario")
		public ResponseEntity<?> addComentario2(@RequestBody @Valid ComentariosDto comentarioDto, BindingResult result){
				
			
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {

				response = validationService.responseErrors(result);
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
			}  
			
			
			Comentarios comentario = new Comentarios(); 
			comentario.setComentario(comentarioDto.getComentario()); 
			comentario.setProducto(comentarioDto.getProducto()); 
			comentario.setUsuario(comentarioDto.getUsuario()); 
			comentario.setValoracion(comentarioDto.getValoracion());
			comentario.setTitulo(comentarioDto.getTitulo());
			Comentarios existente = null; 
			try {
				
				existente = comentarioService.getByUsernameAndPerfil(
						comentario.getUsuario().getUsername(), 
						comentario.getProducto().getId()); 
				
			}catch(Exception e) {
				
			}
			System.out.println("XD"+existente);
			if(existente != null) {
				
				comentario.setId(existente.getId()); 
			}
			
			try {
				
				Comentarios comentario_saved = comentarioService.save(comentario); 
				if(existente!=null) {
					response.put("mensaje", "El comentario ha sido editado con éxito");
				}else {
					response.put("mensaje", "El comentario ha sido agregado con éxito");
				}
				
				response.put("comentario",comentario_saved); 
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
				
			}catch(Exception e) {
				response.put("Error", "Ha ocurrido un error al guardar \n"+e.getCause());
				System.out.println(e.getMessage());
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 	
			}
		
				
		}
		

		//OBTENER COMENTARIO POR USUARIO Y PRODUCTO
		@GetMapping("/comentario/usuario/{producto_id}")
		@Secured({"ROLE_ADMIN","ROLE_USER"})
		public ResponseEntity<?> getComentariosByU(@PathVariable Long producto_id,
				Authentication authentication){
			String username = authentication.getName(); 
			Map<String, Object> response = new HashMap<>();
			Comentarios comentario = null;  
			try { 
				comentario = comentarioService.getByUsernameAndPerfil(username,producto_id);
			}catch(Exception e) {
				response.put("error", "No se encontró el comentario"); 
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND); 	
			}
			if(comentario == null) {
				response.put("error", "No se ha encontrado el comentario"); 
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND); 	
			}
			response.put("mensaje", "Se ha encontrado el comentario"); 
			response.put("comentario", comentario); 
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK); 	
		}
		//OBTENER COMENTARIOS POR PRODUCTO
		
		@GetMapping("/comentario/{producto_id}")
	
		public ResponseEntity<?> getComentariosByP(@PathVariable Long producto_id){
			Map<String, Object> response = new HashMap<>();
			List<Comentarios> comentarios = null;  
			try {
				comentarios = comentarioService.getByIdProducto(producto_id);
			}catch(Exception e) {
				response.put("error", "No se encontró el comentario"); 
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND); 	
			}
			if(comentarios == null || comentarios.size()==0) {
				response.put("error", "No se han encontrado los comentarios"); 
				response.put("mensaje", "Aún no hay comentarios para este producto");
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND); 	
			}
			response.put("mensaje", "Se han encontrado los comentario"); 
			response.put("comentarios", comentarios); 
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK); 	
		}
		
		public double obtenerPromedioValoracion(List<Comentarios> comentarios) {
		    if (comentarios.isEmpty()) {
		        return 0.0;
		    }
		    
		    double sumaValoraciones = 0.0;
		    for (Comentarios comentario : comentarios) {
		        sumaValoraciones += comentario.getValoracion();
		    }
		    
		    return sumaValoraciones / comentarios.size();
		}


	
}
