package com.empresa.proyecto.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
