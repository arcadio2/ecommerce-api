package com.empresa.proyecto.controllers;

import java.util.HashMap;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.proyecto.models.entity.DetalleProducto;
import com.empresa.proyecto.models.entity.DetalleProductoDto;
import com.empresa.proyecto.models.entity.Producto;
import com.empresa.proyecto.models.service.IDetalleProductoService;
import com.empresa.proyecto.models.service.IProductoService;
import com.empresa.proyecto.services.IValidationService;

@RestController
@RequestMapping("/api/detalle")
@CrossOrigin(origins = {"http://localhost:4200/","*"})
public class DetalleProductoController {

	
	@Autowired 
	IProductoService productoService; 
	
	@Autowired 
	IDetalleProductoService detalleService; 
	
	
	@Autowired
	IValidationService validationService; 
	
	@GetMapping("/{id}/{color}/{talla}")
	public ResponseEntity<?> getDetalleByColorAndTalla(@PathVariable Long id,
			@PathVariable String color,@PathVariable String talla){
		Map<String, Object> response = new HashMap<>();
		
		System.out.println(id+" "+talla+ " "+color );

		DetalleProducto detalle = null; 
		try {
			detalle = detalleService.getByIdProductoAndTallaAndColor(id, talla, color);
			
		
		}catch(Exception e) {
			response.put("error", "No se encontró el producto"); 
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND); 	
		}
		if(detalle==null) {
			response.put("error", "No se encontró el producto"); 
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND); 	
		}
		response.put("mensaje", "Se ha encontrado el producto"); 
		response.put("detalle", convertir(detalle)); 
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK); 	
	}
	
	@GetMapping("/detalle/{id}")
	public ResponseEntity<?> getDetalleByColorAndTalla(@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		DetalleProducto detalle = null; 
		try {
			detalle = detalleService.getById(id);
			
		
		}catch(Exception e) {
			response.put("error", "No se encontró el producto"); 
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND); 	
		}
		if(detalle==null) {
			response.put("error", "No se encontró el producto"); 
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND); 	
		}
		response.put("mensaje", "Se ha encontrado el producto"); 
		response.put("producto", convertir(detalle)); 
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK); 
		
	}
	
	
	/*Método para aumentar el stock*/
	@PutMapping("/edit")
	@Secured({"ROLE_ADMIN"})
	@ResponseStatus(HttpStatus.CREATED)//201
	public ResponseEntity<?> editProducto(@RequestBody @Valid DetalleProducto producto, BindingResult result){
	 
		Map<String, Object> response = new HashMap<>();
		if(result.hasErrors()) {

			response = validationService.responseErrors(result);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}  
		
		
		
		DetalleProducto producto_encontrado = null; 
		try {
			producto_encontrado = detalleService.getById(producto.getId()); 
			producto_encontrado.setStock(producto.getStock()); 
			System.out.println("PRODUCTOXD "+producto_encontrado.getProducto().getNombre());
			/*
			producto_encontrado.setPrecio(producto.getPrecio()); 
			producto_encontrado.setDescripcion(producto.getDescripcion()); 
			producto_encontrado.setNombre(producto.getNombre()); */
		}catch (Exception e){ 
			response.put("Error", "Ha ocurrido un error al editar \n"+e.getCause());
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 	
		}
			
		
		try {
			DetalleProducto prodcuto_saved = detalleService.save(producto_encontrado); 
			response.put("mensaje", "El producto ha sido agregado con éxito");
			response.put("subproducto",prodcuto_saved); 
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
			
		}catch(Exception e) {
			response.put("Error", "Ha ocurrido un error al guardar \n"+e.getCause());
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 	
		}
	
	}
	
	
	@DeleteMapping("/delete/{id}")
	@Secured({"ROLE_ADMIN"})
	public ResponseEntity<?> deleteDetalle(@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		DetalleProducto detalle = null; 
		try {
			detalle = detalleService.getById(id);
			detalleService.delete(detalle);
			
		
		}catch(Exception e) {
			response.put("error", "No se encontró el producto"); 
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND); 	
		}
	
		response.put("mensaje", "Se ha eliminado el producto"); 
		response.put("producto", convertir(detalle)); 
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK); 
		
	}
	
	
	
	public DetalleProductoDto convertir(DetalleProducto detalle) {
		DetalleProductoDto detalle_final = new DetalleProductoDto();
		detalle_final.setColor(detalle.getColor());
		detalle_final.setId(detalle.getId());
		detalle_final.setProducto(detalle.getProducto());
		detalle_final.setStock(detalle.getStock());
		detalle_final.setTalla(detalle.getTalla());
		
		return detalle_final; 
		
	}
	
}
