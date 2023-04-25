package com.empresa.proyecto.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.proyecto.models.entity.Bolsa;
import com.empresa.proyecto.models.entity.Perfil;
import com.empresa.proyecto.models.entity.Usuario;
import com.empresa.proyecto.models.service.IBolsaService;
import com.empresa.proyecto.models.service.IProductoService;



@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = {"http://localhost:4200/","*"})
public class CarritoController {
	

	@Autowired 
	IProductoService productoService; 
	
	
	@Autowired
	IBolsaService bolsaService; 

	
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
	 
	@DeleteMapping("/bolsa/{id}")  
	@Secured({"ROLE_USER"})
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<?> deleteUser(@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		Bolsa bolsaObtenida = null; 
		try {
			bolsaObtenida = bolsaService.getById(id);   
		}catch(Exception e) {
			response.put("mensaje", "No se ha encontrado el elemento a eliminar");
			response.put("error", e.getMessage()); 
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		} 
		if(bolsaObtenida==null) {
			response.put("mensaje", "No se ha encontrado el elemento a eliminar");
			response.put("error", "Error al eliminar"); 
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		try {   
			
		
			 
			bolsaService.delete(bolsaObtenida);   
		}catch(Exception e) {
			response.put("mensaje", "No se ha podido eliminar el elemento del carrito");
			response.put("error", e.getMessage()); 
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		response.put("mensaje","Se ha eliminado el elemento con éxito");
		//response.put("usuario", usuarioUpdated); 
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	}
	
	
	
}
