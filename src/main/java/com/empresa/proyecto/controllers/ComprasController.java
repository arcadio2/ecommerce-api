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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.proyecto.models.entity.Compra;
import com.empresa.proyecto.models.entity.DetalleProducto;
import com.empresa.proyecto.models.entity.Direccion;
import com.empresa.proyecto.models.entity.Usuario;
import com.empresa.proyecto.models.service.IBolsaService;
import com.empresa.proyecto.models.service.ICompraService;
import com.empresa.proyecto.models.service.IDetalleProductoService;
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
	
	@Autowired
	private IDetalleProductoService detalleService; 
	
	@Autowired
	private IBolsaService bolsaService; 
	
	
	@GetMapping("/get")
	@Secured({"ROLE_USER"})
	public ResponseEntity<?> getByUser(Authentication auth){
		Map<String, Object> response = new HashMap<>(); 
		String username = auth.getName(); 
		System.out.println("USUARIOOOO   "+username);
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
	@GetMapping("/get/{id_producto}")
	@Secured({"ROLE_USER"})
	public ResponseEntity<?> getByUserAndProduct(Authentication auth, @PathVariable Long id_producto){
		Map<String, Object> response = new HashMap<>(); 
		String username = auth.getName(); 

		boolean existe = false; 
		try {
			existe = compraService.getByProductoAndUsuarioExist(username,id_producto); 
		}catch(Exception e) {
			response.put("mensaje", "Ha ocurrido un error"); 
			response.put("error", e.getMessage()); 
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		response.put("mensaje", "Se han pobtenido las compras"); 
		response.put("compras", existe); 
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK); 
	}
	
	@GetMapping("/get/all")
	@Secured({"ROLE_ADMIN"})
	public ResponseEntity<?> getAll(){
		Map<String, Object> response = new HashMap<>(); 

		List<Compra>  compras = null; 
		try {
			compras = compraService.getAll(); 
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
			c.setDetalle_producto(detalleService.getById(c.getDetalle_producto().getId()));
			c.setUsuario(usuario); 
		}
		
		
		List<Compra> compras_guardadas = null; 
		try {
			bolsaService.vaciarBolsaUsuario(username); 
			compras_guardadas = compraService.saveAll(compras);
			
			
		}catch(Exception e) {
			response.put("mensaje", "Ha ocurrido un error"); 
			response.put("error", e.getMessage()); 
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		for(Compra c: compras_guardadas ) {
			try {
				c.getDetalle_producto().setStock(c.getDetalle_producto().getStock()-c.getCantidad()); 
				detalleService.save(c.getDetalle_producto()); 
			}catch(Exception e) {
				
			}
			
		}
		
		response.put("mensaje", "Se han guardado las compras"); 
		response.put("compras", compras_guardadas); 
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED); 
	}
	@PutMapping("/estatus")
	@Secured({"ROLE_ADMIN"})
	public ResponseEntity<?> changeDetalle(@RequestBody Compra compra){
		Map<String, Object> response = new HashMap<>();
		
		Compra compra_response = null; 
		try {
			compra_response = compraService.getById(compra.getId()); 
			
		}catch(Exception e) {
			response.put("error", "Ha ocurrido un error");  
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 	
		}
		compra_response.setActive(false);
		try {
			compra_response = compraService.save(compra_response); 
		}catch(Exception e) {
			response.put("error", "Ha ocurrido un error");  
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 	
		}  
		
		response.put("mensaje", "Se ha cambiado el status a entregado"); 
		response.put("compra", compra_response); 
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK); 	
	}
	
}
