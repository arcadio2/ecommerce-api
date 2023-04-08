package com.empresa.proyecto.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.proyecto.models.entity.Comentarios;
import com.empresa.proyecto.models.entity.Perfil;
import com.empresa.proyecto.models.entity.Producto;
import com.empresa.proyecto.models.entity.Valoracion;
import com.empresa.proyecto.models.service.IComentariosService;
import com.empresa.proyecto.models.service.IProductoService;
import com.empresa.proyecto.models.service.IUsuarioService;
import com.empresa.proyecto.models.service.IValoracionService;
import com.empresa.proyecto.services.IValidationService;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = {"http://localhost:4200/","*"})
public class ProductosController {
	
	@Autowired 
	IProductoService productoService; 
	
	@Autowired
	IComentariosService comentarioService; 
	
	@Autowired
	IUsuarioService usuarioService; 
	
	@Autowired
	IValoracionService valoracionService; 
	
	
	@Autowired
	IValidationService validationService; 
	
	@GetMapping("")
	//@ResponseStatus(HttpStatus.OK)
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	public List<Producto> getProductos(){
		List<Producto> productos = productoService.findAll(); 
		
		return productos; 
		
	}
	
	@GetMapping("/{producto}")
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	public ResponseEntity<?> getProducto(@PathVariable String producto){
		Map<String, Object> response = new HashMap<>();
		
		Producto producto_response = null; 
		try {
			producto_response = productoService.getByNombre(producto); 
		}catch(Exception e) {
			response.put("error", "No se encontró el producto"); 
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND); 	
		}
		if(producto_response==null) {
			response.put("error", "No se encontró el producto"); 
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND); 	
		}
		response.put("mensaje", "Se ha encontrado el producto"); 
		response.put("producto", producto_response); 
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK); 	
	}
	
	@PostMapping("/create")
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@ResponseStatus(HttpStatus.CREATED)//201
	public ResponseEntity<?> createProducto(@RequestBody @Valid Producto producto, BindingResult result){
	
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {

			response = validationService.responseErrors(result);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}  
		
		try {
			Producto prodcuto_saved = productoService.save(producto); 
			response.put("mensaje", "El producto ha sido agregado con éxito");
			response.put("producto",prodcuto_saved); 
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
			
		}catch(Exception e) {
			response.put("Error", "Ha ocurrido un error al guardar \n"+e.getCause());
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 	
		}
	
	}
	
	@DeleteMapping("/delete/{producto}")
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	public ResponseEntity<?> deleteProducto(@PathVariable String producto){
		Map<String, Object> response = new HashMap<>();
		Producto producto_obtenido = null;
		
		try {
			productoService.getByNombre(producto); 
			
		}catch(Exception e) {
			response.put("mensaje", "No se ha encontrado el producto a eliminar");
			response.put("error", e.getMessage()); 
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		try {
			productoService.delete(producto_obtenido); 
			
		}catch(Exception e) {
			response.put("mensaje", "No se ha podido borrar el producto");
			response.put("error", e.getMessage()); 
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		response.put("mensaje","El producto se ha eliminado con exito");
		
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
		
	}
	
	
	@Secured({"ROLE_USER"})
	@PostMapping("/comentario/{username}/{id_producto}/{comentario_s}")
	public ResponseEntity<?> addComentario(@PathVariable String username,@PathVariable Long id_producto,@PathVariable String comentario_s){
			
		Map<String, Object> response = new HashMap<>();
		Comentarios comentario = new Comentarios(); 
		comentario.setComentario(comentario_s); 
		try {
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
		
		try {
			Comentarios comentario_saved = comentarioService.save(comentario); 
			response.put("mensaje", "El comentario ha sido agregado con éxito");
			response.put("comentario",comentario_saved); 
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
			
		}catch(Exception e) {
			response.put("Error", "Ha ocurrido un error al guardar \n"+e.getCause());
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 	
		}
	
			
	}
	
	@Secured({"ROLE_USER"})
	@PostMapping("/valoracion")
	public ResponseEntity<?> addValoracion(@RequestBody @Valid Valoracion valoracion, BindingResult result){
			
		
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {

			response = validationService.responseErrors(result);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}  
		
		try {
			Valoracion valoracion_saved = valoracionService.save(valoracion); 
			response.put("mensaje", "La valoración ha sido agregada con éxito");
			response.put("valoracion",valoracion_saved); 
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
			
		}catch(Exception e) {
			response.put("Error", "Ha ocurrido un error al guardar \n"+e.getCause());
			System.out.println(e.getMessage());
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 	
		}
	
			
	}
	
	@Secured({"ROLE_USER"})
	@PostMapping("/comentario")
	public ResponseEntity<?> addComentario2(@RequestBody @Valid Comentarios comentario, BindingResult result){
			
		
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {

			response = validationService.responseErrors(result);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}  
		
		try {
			Comentarios comentario_saved = comentarioService.save(comentario); 
			response.put("mensaje", "El comentario ha sido agregado con éxito");
			response.put("valoracion",comentario_saved); 
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
			
		}catch(Exception e) {
			response.put("Error", "Ha ocurrido un error al guardar \n"+e.getCause());
			System.out.println(e.getMessage());
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 	
		}
	
			
	}
	
	@GetMapping("/valoracion/{username}/{producto_id}")
	@Secured({"ROLE_ADMIN","ROLE_USER","ROLE_INSTRUCTOR"})
	public ResponseEntity<?> perfil(@PathVariable String username, @PathVariable Long producto_id){
		Map<String, Object> response = new HashMap<>();
		Valoracion valoracion = null;  
		try {
			valoracion = valoracionService.getByUsernameAndProducto(username,producto_id);
		}catch(Exception e) {
			response.put("error", "No se encontró la valoración"); 
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND); 	
		}
		if(valoracion == null) {
			response.put("error", "No se ha encontrado la valoracion"); 
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND); 	
		}
		response.put("mensaje", "Se ha encontrado la valoración"); 
		response.put("valoracion", valoracion); 
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK); 	
	}
	
	
	
	

}
