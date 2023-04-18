package com.empresa.proyecto.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.proyecto.models.dao.IDetalleProductoDao;
import com.empresa.proyecto.models.entity.Bolsa;
import com.empresa.proyecto.models.entity.CategoriaProducto;
import com.empresa.proyecto.models.entity.Comentarios;
import com.empresa.proyecto.models.entity.ComentariosDto;
import com.empresa.proyecto.models.entity.DetalleProducto;
import com.empresa.proyecto.models.entity.DetalleProductoDto;
import com.empresa.proyecto.models.entity.Perfil;
import com.empresa.proyecto.models.entity.Producto;
import com.empresa.proyecto.models.service.IBolsaService;
import com.empresa.proyecto.models.service.ICategoriaProductoService;
import com.empresa.proyecto.models.service.IComentariosService;
import com.empresa.proyecto.models.service.IDetalleProductoService;
import com.empresa.proyecto.models.service.IProductoService;
import com.empresa.proyecto.models.service.IUsuarioService;

import com.empresa.proyecto.services.IValidationService;
import com.empresa.proyecto.services.PlnService;

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
	IDetalleProductoService detalleService; 
	
	@Autowired
	IBolsaService bolsaService; 

	
	@Autowired
	IValidationService validationService; 
	
	@Autowired 
	ICategoriaProductoService categoriaService; 
	
	
	@GetMapping("")
	//@ResponseStatus(HttpStatus.OK)
	public List<Producto> getProductos(){
		List<Producto> productos = productoService.findAll(); 
		
		return productos; 

	}

	@GetMapping("/get/{nombre}")
	//@ResponseStatus(HttpStatus.OK)
	public List<Producto> getProductosNombre(@PathVariable String nombre){
		List<Producto> productos = productoService.findByNombre(nombre); 
		

		return productos; 
	}  
	
	@GetMapping("/listado/{nombre}")
	//@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> getSimilares(@PathVariable String nombre){
		Map<String, Object> response = new HashMap<>();
		
		Producto encontrado = null; 
		try {
			encontrado = productoService.getByNombre(nombre); 
			
		}catch(Exception e) {
			response.put("error", "No se encontró el producto"); 
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND); 	
		}
		String categoria; 
		List<String> talla,color = new ArrayList<>();
		talla = encontrado.getDetalle().stream().map(d->d.getTalla().getTalla()).collect(Collectors.toList());
		color = encontrado.getDetalle().stream().map(d->d.getColor().getColor()).collect(Collectors.toList());
		
		System.out.println("XCD"); 
		System.out.println(talla);
		System.out.println(color);
		categoria = encontrado.getCategoria().getTipo(); 
		System.out.println(categoria);
		//talla = encontrado.getDetalle().
		
		List<Producto> productos = productoService.getByColorOrTallaOrCategoria(talla,color,categoria); 
		
		response.put("success", "Se han encontrado los productos"); 
		response.put("productos", productos); 
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK); 
	}
	
	
	
	@GetMapping("/producto/{producto}")
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
	

	
	@GetMapping("/comentarios/{username}/{producto_id}")
	@Secured({"ROLE_ADMIN","ROLE_USER","ROLE_INSTRUCTOR"})
	public ResponseEntity<?> getComentariosByU(@PathVariable String username, @PathVariable Long producto_id){
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
		response.put("valoracion", comentario); 
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK); 	
	}
	
	
	
	
	/*BOLSA*/
	@PutMapping("/detalle")
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	public ResponseEntity<?> changeDetalle(@RequestBody DetalleProducto detalle){
		Map<String, Object> response = new HashMap<>();
		
		DetalleProducto detalle_response = null; 
		try {
			detalle_response = detalleService.save(detalle); 
			
		}catch(Exception e) {
			response.put("error", "No se encontró el producto"); 
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND); 	
		}
		if(detalle_response==null) {
			response.put("error", "No se encontró el producto"); 
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND); 	
		}
		response.put("mensaje", "Se ha encontrado el producto"); 
		response.put("detalle", detalle_response); 
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK); 	
	}
	
	
	@PutMapping("/bolsa")
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	public ResponseEntity<?> changeBolsa(@RequestBody Bolsa bolsa){
		Map<String, Object> response = new HashMap<>();
		
		Bolsa bolsa_response = null; 
		Bolsa bolsa_guardada = null; 
		
		try {
			bolsa_guardada = bolsaService.getById(bolsa.getId()); 
			bolsa.setUsuario(bolsa_guardada.getUsuario());
		}catch(Exception e) {
			
		}
		
		try {
			bolsa_response = bolsaService.save(bolsa); 
			
		}catch(Exception e) {
			response.put("error", "No se encontró el producto de la bolsa"); 
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND); 	
		}
		if(bolsa_response==null) {
			response.put("error", "No se encontró el producto"); 
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND); 	
		}
		response.put("mensaje", "Se ha editado el producto en la bolsa"); 
		response.put("detalle", bolsa_response); 
		 
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK); 	
	}
	
	
	/*
	 * CATEGORIA
	 * */
	@GetMapping("/categoria/{sexo}")
	public List<CategoriaProducto> obetenerCategoriasBySexo(@PathVariable String sexo){
		System.out.println("SEX "+sexo);
		return categoriaService.finBySexo(sexo);  
	}

}
