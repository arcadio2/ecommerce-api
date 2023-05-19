package com.empresa.proyecto.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.proyecto.models.entity.Compra;
import com.empresa.proyecto.models.entity.Direccion;
import com.empresa.proyecto.models.entity.Usuario;
import com.empresa.proyecto.models.service.ICompraService;
import com.empresa.proyecto.models.service.IDireccionesService;
import com.empresa.proyecto.models.service.IUsuarioService;

@RestController
@RequestMapping("/api/compras")
@CrossOrigin(origins = {"http://localhost:4200/","*"})
public class ComprasController {
	
	@Autowired 
	private ICompraService compraService; 
	
	@Autowired 
	private IDireccionesService direccionesService;  
	
	@Autowired
	IUsuarioService usuarioService; 
	
	
	@GetMapping("/get")
	@Secured({"ROLE_USER"})
	public ResponseEntity<?> getByUser(Authentication auth){
		Map<String, Object> response = new HashMap<>(); 
		String username = auth.getName(); 
		List<Compra>  compras = null; 
		try {
			compras = compraService.getByUser(username); 
		}catch(Exception e) {
			response.put("mensaje", "Ha ocurrido un error"); 
			response.put("error", e.getMessage()); 
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		response.put("mensaje", "Se han pobtenido las compras"); 
		response.put("compras", compras); 
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK); 
	}
	

	
	@PostMapping("/save")
	@Secured({"ROLE_USER"})
	public ResponseEntity<?> saveByUser(@RequestBody List<Compra> compras, Authentication auth){
		Map<String, Object> response = new HashMap<>(); 
		String username = auth.getName();
		
		
		Usuario  usuario = null; 
		Direccion direccion = null; 
		try {
			usuario = usuarioService.findByUsername(username); 
			direccion = direccionesService.save(compras.get(0).getDireccion()); 
		}catch(Exception e) {
			response.put("mensaje", "Ha ocurrido un error"); 
			response.put("error", e.getMessage()); 
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		if(usuario==null) {
			response.put("mensaje", "Ha ocurrido un error"); 
			response.put("error", "No se ha encontrado al usuario"); 
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.UNAUTHORIZED); 
		}
		for(Compra c: compras ) {
			c.setDireccion(direccion); 
			c.setUsuario(usuario); 
		}
		List<Compra> compras_guardadas = null; 
		try {
			compras_guardadas = compraService.saveAll(compras); 
		}catch(Exception e) {
			response.put("mensaje", "Ha ocurrido un error"); 
			response.put("error", e.getMessage()); 
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		response.put("mensaje", "Se han guardado las compras"); 
		response.put("compras", compras_guardadas); 
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED); 
	}
	
}
